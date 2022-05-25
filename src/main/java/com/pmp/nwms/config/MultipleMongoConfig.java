package com.pmp.nwms.config;

import com.mongodb.*;
import com.pmp.nwms.logging.util.JSONObjectWriterConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(MultipleMongoProperties.class)
public class MultipleMongoConfig {
    private final MultipleMongoProperties mongoProperties;

    public MultipleMongoConfig(MultipleMongoProperties mongoProperties) {
        this.mongoProperties = mongoProperties;
    }

    @Primary
    @Bean(name = "coreLogsMongoTemplate")
    public MongoTemplate coreLogsMongoTemplate(MongoConverter converter) throws Exception {
        ((MappingMongoConverter) converter).setTypeMapper(new DefaultMongoTypeMapper(null));
        MongoTemplate mongoTemplate = new MongoTemplate(coreLogsFactory(), converter);
        mongoTemplate.setWriteConcern(WriteConcern.MAJORITY);
        mongoTemplate.setWriteResultChecking(WriteResultChecking.EXCEPTION);
        return mongoTemplate;
    }

    @Bean(name = "rubruLogsMongoTemplate")
    public MongoTemplate rubruLogsMongoTemplate(MongoConverter converter) throws Exception {
        ((MappingMongoConverter) converter).setTypeMapper(new DefaultMongoTypeMapper(null));
        MongoTemplate mongoTemplate = new MongoTemplate(rubruLogsFactory(), converter);
        mongoTemplate.setWriteConcern(WriteConcern.MAJORITY);
        mongoTemplate.setWriteResultChecking(WriteResultChecking.EXCEPTION);
        return mongoTemplate;
    }

    @Bean
    @Primary
    public MongoDbFactory coreLogsFactory() throws Exception {
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        MongoClientOptions options = builder.build();
        MongoProperties mongo = this.mongoProperties.getCoreLogs();
        MongoClient mongoClient = new MongoClient(new ServerAddress(mongo.getHost(), mongo.getPort()), MongoCredential.createCredential(mongo.getUsername(), mongo.getDatabase(), mongo.getPassword()), options);
        return new SimpleMongoDbFactory(mongoClient, mongo.getDatabase());
    }

    @Bean
    public MongoDbFactory rubruLogsFactory() throws Exception {
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        MongoClientOptions options = builder.build();
        MongoProperties mongo = this.mongoProperties.getRubruLogs();
        MongoClient mongoClient = new MongoClient(new ServerAddress(mongo.getHost(), mongo.getPort()), MongoCredential.createCredential(mongo.getUsername(), mongo.getDatabase(), mongo.getPassword()), options);
        return new SimpleMongoDbFactory(mongoClient, mongo.getDatabase());
    }


    @Bean
    @ConditionalOnMissingBean
    public MongoCustomConversions mongoCustomConversions() {
        List<Object> converters = new ArrayList<>();
        converters.add(JSONObjectWriterConverter.INSTANCE);
        return new MongoCustomConversions(converters);
    }


    @Bean
    @ConditionalOnMissingBean({MongoConverter.class})
    public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context, MongoCustomConversions conversions) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        mappingConverter.setCustomConversions(conversions);
        return mappingConverter;
    }


}
