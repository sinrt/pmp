package com.pmp.nwms.rubru.data.repository;

import com.pmp.nwms.rubru.data.document.RubruSignalServerLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RubruSignalServerLogRepository extends MongoRepository<RubruSignalServerLog, String> {

    Long deleteByAppUrlAndLogDateBefore(String appUrl, Date checkDate);
}
