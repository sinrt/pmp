package com.pmp.nwms.logging.data.repository;

import com.pmp.nwms.logging.data.entity.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface LogRepository extends MongoRepository<Log, String>, QuerydslPredicateExecutor<Log> {
    Long deleteByAppWebUrlAndCallDateTimeBefore(String appWebUrl, Date checkDate);
}
