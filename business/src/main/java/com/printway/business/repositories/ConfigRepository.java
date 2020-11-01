package com.printway.business.repositories;

import com.printway.business.models.Config;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Integer> {
    Config findFirstByStatus(Integer status);
    Page<Config> findAllByStatus(Integer status, Pageable pageable);
    List<Config> findAllByStatus(Integer status);
}
