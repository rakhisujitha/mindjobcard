package com.example.mindjobcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mindjobcard.model.TaskTime;

@Repository
public interface TaskTimeRepository extends JpaRepository<TaskTime, Long>{

}
