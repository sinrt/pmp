package com.pmp.nwms.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "mongodb")
public class MultipleMongoProperties {
    private MongoProperties coreLogs;
    private MongoProperties rubruLogs;

    public MongoProperties getCoreLogs() {
        return coreLogs;
    }

    public void setCoreLogs(MongoProperties coreLogs) {
        this.coreLogs = coreLogs;
    }

    public MongoProperties getRubruLogs() {
        return rubruLogs;
    }

    public void setRubruLogs(MongoProperties rubruLogs) {
        this.rubruLogs = rubruLogs;
    }

    @Override
    public String toString() {
        return "MultipleMongoProperties{" +
            "coreLogs=" + coreLogs +
            ", rubruLogs=" + rubruLogs +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MultipleMongoProperties that = (MultipleMongoProperties) o;

        if (coreLogs != null ? !coreLogs.equals(that.coreLogs) : that.coreLogs != null) return false;
        return rubruLogs != null ? rubruLogs.equals(that.rubruLogs) : that.rubruLogs == null;
    }

    @Override
    public int hashCode() {
        int result = coreLogs != null ? coreLogs.hashCode() : 0;
        result = 31 * result + (rubruLogs != null ? rubruLogs.hashCode() : 0);
        return result;
    }
}
