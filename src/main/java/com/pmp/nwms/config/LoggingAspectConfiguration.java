package com.pmp.nwms.config;

import com.pmp.nwms.aop.logging.LoggingAspect;

import com.pmp.nwms.logging.aspect.NwmsLogger;
import io.github.jhipster.config.JHipsterConstants;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }

    @Bean
    @ConditionalOnProperty(
        name = "nwms.aop.logging.enabled",
        matchIfMissing = true)
    public NwmsLogger nwmsLogger() {
        return new NwmsLogger();
    }

    @Bean
    public ParameterNameDiscoverer parameterNameDiscoverer(){
        return new LocalVariableTableParameterNameDiscoverer();
    }
}
