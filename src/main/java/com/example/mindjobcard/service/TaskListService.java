package com.example.mindjobcard.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.mindjobcard.model.TaskList;

public interface TaskListService {

	TaskList saveTaskList(TaskList taskList);
	
	List<TaskList> getAllTaskList();
	
	Optional<TaskList> findById(Long id);
	
	void deleteById(Long id);
	
	List<TaskList> findByBatchOrderId(Long id);
	
	List<TaskList>findByPerson(String name);
	
	List<TaskList> findByPersonAndStatusIsNot(String name, String status);
	
	List<TaskList> findByPersonAndStatusNot(String name, String status);
	
	List<TaskList> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date startdate, Date enddate);
}
