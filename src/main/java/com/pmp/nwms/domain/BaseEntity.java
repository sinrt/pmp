package com.pmp.nwms.domain;

public interface BaseEntity<PK> {
    PK fetchPrimaryKey();
}
