package com.pmp.nwms.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlet.InstrumentedFilter;
import com.codahale.metrics.servlets.MetricsServlet;
import com.pmp.nwms.util.slidemaker.FileToSlideConverter;
import com.pmp.nwms.util.slidemaker.SlideableTypes;
import io.github.jhipster.config.JHipsterProperties;
import io.undertow.UndertowOptions;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory> {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    private final Environment env;

    private final JHipsterProperties jHipsterProperties;

    private MetricRegistry metricRegistry;

    public WebConfigurer(Environment env, JHipsterProperties jHipsterProperties) {

        this.env = env;
        this.jHipsterProperties = jHipsterProperties;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (env.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }
        EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);
        initMetrics(servletContext, disps);
        log.info("Web application fully configured");
    }

    /**
     * Customize the Servlet engine: Mime types, the document root, the cache.
     */
    @Override
    public void customize(WebServerFactory server) {
        setMimeMappings(server);

        /*
         * Enable HTTP/2 for Undertow - https://twitter.com/ankinson/status/829256167700492288
         * HTTP/2 requires HTTPS, so HTTP requests will fallback to HTTP/1.1.
         * See the JHipsterProperties class and your application-*.yml configuration files
         * for more information.
         */
        if (jHipsterProperties.getHttp().getVersion().equals(JHipsterProperties.Http.Version.V_2_0) &&
            server instanceof UndertowServletWebServerFactory) {

            ((UndertowServletWebServerFactory) server)
                .addBuilderCustomizers(builder ->
                    builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true));
        }
    }

    private void setMimeMappings(WebServerFactory server) {
        if (server instanceof ConfigurableServletWebServerFactory) {
            MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
            // IE issue, see https://github.com/jhipster/generator-jhipster/pull/711
            mappings.add("html", MediaType.TEXT_HTML_VALUE + ";charset=" + StandardCharsets.UTF_8.name().toLowerCase());
            // CloudFoundry issue, see https://github.com/cloudfoundry/gorouter/issues/64
            mappings.add("json", MediaType.TEXT_HTML_VALUE + ";charset=" + StandardCharsets.UTF_8.name().toLowerCase());
            ConfigurableServletWebServerFactory servletWebServer = (ConfigurableServletWebServerFactory) server;
            servletWebServer.setMimeMappings(mappings);
        }
    }

    /**
     * Initializes Metrics.
     */
    private void initMetrics(ServletContext servletContext, EnumSet<DispatcherType> disps) {
        log.debug("Initializing Metrics registries");
        servletContext.setAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE,
            metricRegistry);
        servletContext.setAttribute(MetricsServlet.METRICS_REGISTRY,
            metricRegistry);

        log.debug("Registering Metrics Filter");
        FilterRegistration.Dynamic metricsFilter = servletContext.addFilter("webappMetricsFilter",
            new InstrumentedFilter());

        metricsFilter.addMappingForUrlPatterns(disps, true, "/*");
        metricsFilter.setAsyncSupported(true);

        log.debug("Registering Metrics Servlet");
        ServletRegistration.Dynamic metricsAdminServlet =
            servletContext.addServlet("metricsServlet", new MetricsServlet());

        metricsAdminServlet.addMapping("/management/metrics/*");
        metricsAdminServlet.setAsyncSupported(true);
        metricsAdminServlet.setLoadOnStartup(2);
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        HttpSessionSecurityContextRepository repository = new HttpSessionSecurityContextRepository();
        repository.setAllowSessionCreation(false);
        return repository;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = jHipsterProperties.getCors();
        if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
            log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", config);
            source.registerCorsConfiguration("/management/**", config);
            source.registerCorsConfiguration("/v2/api-docs", config);
        }
        return new CorsFilter(source);
    }

    @Autowired(required = false)
    public void setMetricRegistry(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @Autowired FileToSlideConverter pdfFileToSlideConverter;
    /*@Autowired FileToSlideConverter wordFileToSlideConverter;
    @Autowired FileToSlideConverter powerPointFileToSlideConverter;*/

    @Bean
    public Map<SlideableTypes, FileToSlideConverter> slideConverters(){
        HashMap<SlideableTypes, FileToSlideConverter> result = new HashMap<>();
        result.put(SlideableTypes.pdf, pdfFileToSlideConverter);
        /*result.put(SlideableTypes.docx, wordFileToSlideConverter);
        result.put(SlideableTypes.pptx, powerPointFileToSlideConverter);*/
        return result;
    }

    private SSLContext makeSslContext() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                //No need to implement.
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                //No need to implement.
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            return sc;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        RequestConfig config = RequestConfig.custom()
            .setConnectTimeout(timeout)
            .setConnectionRequestTimeout(timeout)
            .setSocketTimeout(timeout)
            .build();
        HttpClientBuilder httpClientBuilder = HttpClientBuilder
            .create()
            .setDefaultRequestConfig(config);
        if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
            httpClientBuilder.setSSLContext(makeSslContext())
                .setSSLHostnameVerifier((s, sslSession) -> true);
        }

        CloseableHttpClient client = httpClientBuilder
            .build();
        return new HttpComponentsClientHttpRequestFactory(client);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(getClientHttpRequestFactory());
    }

}
