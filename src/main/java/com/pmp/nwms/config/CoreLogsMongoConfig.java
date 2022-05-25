package com.pmp.nwms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.pmp.nwms.logging", mongoTemplateRef = "coreLogsMongoTemplate")
public class CoreLogsMongoConfig {
}
