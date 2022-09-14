package com.fadgiras.exos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fadgiras.exos.model.File;

@Repository
public interface FilesRepository extends JpaRepository<File, Long> {
    
}
