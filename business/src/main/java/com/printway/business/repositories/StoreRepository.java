package com.printway.business.repositories;

import com.printway.business.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    Store findByName(String name);
    List<Store> findAllByStatus(int status);
}
