package com.fadgiras.exos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fadgiras.exos.model.DBFile;

@Repository
public interface FilesRepository extends JpaRepository<DBFile, Long> {
    @Query(
    value = "SELECT * FROM files f WHERE f.uuid = :uuid", nativeQuery = true)
    Optional<DBFile> findFileByUUID(@Param("uuid") String uuid);
}
