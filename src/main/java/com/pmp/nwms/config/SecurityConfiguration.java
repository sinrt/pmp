package com.pmp.nwms.config;

import com.pmp.nwms.security.AuthoritiesConstants;
import com.pmp.nwms.security.jwt.JWTConfigurer;
import com.pmp.nwms.security.jwt.TokenProvider;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserDetailsService userDetailsService;

    private final TokenProvider tokenProvider;

    private final CorsFilter corsFilter;

    private final SecurityProblemSupport problemSupport;

    private final SecurityContextRepository securityContextRepository;

    public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder, UserDetailsService userDetailsService, TokenProvider tokenProvider, CorsFilter corsFilter, SecurityProblemSupport problemSupport, SecurityContextRepository securityContextRepository) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.problemSupport = problemSupport;
        this.securityContextRepository = securityContextRepository;
    }

    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .securityContext().securityContextRepository(securityContextRepository).and()
            .csrf()
            .disable()
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport)
            .and()
            .headers()
            .frameOptions()
            .disable()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/api/sendingSMS").permitAll()
            .antMatchers("/api/service-types").permitAll()
            .antMatchers("/api/classrooms").permitAll()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/validcode").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/getCaptcha").permitAll()
            .antMatchers("/api/suggestions-boxes").permitAll()
            .antMatchers("/api/suggestions-boxes/file").permitAll()
            .antMatchers("/api/server-configs/byParamName/{paramName}").permitAll()
            .antMatchers("/api/classrooms/byuuid/{sessionId}").permitAll()
            .antMatchers("/api/purchase-statuses/currentplan/{name}").permitAll()
            .antMatchers("/api/getLoginTry").permitAll()
            .antMatchers("/api/getsystemip").permitAll()
            .antMatchers("/api/account/image/{creatorLogin}").permitAll()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/account/reset-password/init").permitAll()
            .antMatchers("/api/account/reset-password/finish").permitAll()
            .antMatchers("/api/rubru/webhook").permitAll()
            .antMatchers("/api/classroom/file/get/**").permitAll()
            .antMatchers("/api/classroom/file/stream/**").permitAll()
            .antMatchers("/api/classroom/file/list").permitAll()
            .antMatchers("/api/classroom/file/slide/get/**").permitAll()
            .antMatchers("/api/classroom/survey/published/list/**").permitAll()
            .antMatchers("/api/classroom/survey/answer/save").permitAll()
            .antMatchers("/api/classrooms/check-status/**").permitAll()
            .antMatchers("/api/classrooms/dynamic-check-status/**").permitAll()
            .antMatchers("/api/classroom/recording/get/**").permitAll()
            .antMatchers("/api/classroom/file/**").authenticated()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/audits").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/kurento/**").permitAll()
            .antMatchers("/kurento/*").permitAll()
            .antMatchers("/kurento/").permitAll()
            .and()
            .apply(securityConfigurerAdapter());

    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
