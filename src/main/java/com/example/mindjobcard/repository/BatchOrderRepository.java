package com.example.mindjobcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mindjobcard.model.BatchOrder;

@Repository
public interface BatchOrderRepository extends JpaRepository<BatchOrder, Long>{

}
