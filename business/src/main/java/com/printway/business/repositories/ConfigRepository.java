package com.printway.business.repositories;

import com.printway.business.models.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Integer> {
    Config findFirstByStatus(Integer status);
}
