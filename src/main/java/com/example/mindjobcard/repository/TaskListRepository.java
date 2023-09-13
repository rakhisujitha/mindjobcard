package com.example.mindjobcard.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mindjobcard.model.TaskList;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList , Long>{

	List<TaskList> findByBatchOrderId(Long id);
	
	List<TaskList> findByPerson(String name);
	
	List<TaskList> findByPersonAndStatusIsNot(String name, String status);
	
	List<TaskList> findByPersonAndStatusNot(String name, String status);
	
	List<TaskList> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date startdate, Date enddate);
	
}
