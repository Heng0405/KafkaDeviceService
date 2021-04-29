package com.example.deviceservice.domain.repository;

import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.example.deviceservice.domain.model.Motherboard;
import org.springframework.data.cassandra.repository.Consistency;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MotherboardRepository extends MapIdCassandraRepository<Motherboard> {

    @Consistency(DefaultConsistencyLevel.LOCAL_QUORUM)
    @Query("UPDATE " + Motherboard.TABLE + " SET " + Motherboard.COLUMN_STATUS + " = ?2 WHERE " + Motherboard.COLUMN_KUID + " = ?0 IF " + Motherboard.COLUMN_STATUS + " = ?1")
    boolean updateStatus(String kuid, String lastStatus, String newStatus);
}
