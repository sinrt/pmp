package com.pmp.nwms;

import com.pmp.nwms.config.ApplicationProperties;
import com.pmp.nwms.config.DefaultProfileUtil;

import com.pmp.nwms.domain.User;
import com.pmp.nwms.repository.UserRepository;
import com.pmp.nwms.util.UserUtil;
import io.github.jhipster.config.JHipsterConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
@EnableDiscoveryClient
public class NwmsApp {

    private static final Logger log = LoggerFactory.getLogger(NwmsApp.class);

    private final Environment env;
    private final UserRepository userRepository;

    public NwmsApp(Environment env, UserRepository userRepository) {
        this.env = env;
        this.userRepository = userRepository;
    }

    /**
     * Initializes nwms.
     * <p>
     * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not " +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
        List<User> users = userRepository.findAllByRecordHashCodeIsNull();
        if(users != null && !users.isEmpty()){
            for (User user : users) {
                user.setRecordHashCode(UserUtil.calculateUserRecordHashCode(user));
                user = userRepository.save(user);
            }
        }


        /*for (PropertySource<?> propertySource : ((AbstractEnvironment) env).getPropertySources()) {
            if(propertySource instanceof MapPropertySource){
                //todo for these configs, make the current dataList
                //then get the saved dataList
                //for each config in the current list do the following
                    //if the config not exists in the saved list, add it to the new configs list and save it in the db
                    //if the config exists in the saved list, check if it is changed, add it to the updated configs list
                //if the new configs list is not empty, log it
                //if the updated configs list is not empty, log it
                System.out.println("\n\n");
                System.out.println("propertySource.getClass().getName() = " + propertySource.getClass().getName());
                System.out.println(propertySource.getName());
                System.out.println(((MapPropertySource)propertySource).getSource());
                System.out.println("\n\n");
            }
        }*/


    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line argumentshttps
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(NwmsApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}\n\t" +
                "External: \t{}://{}:{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            env.getProperty("server.port"),
            protocol,
            hostAddress,
            env.getProperty("server.port"),
            env.getActiveProfiles());
    }
}
