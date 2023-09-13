package com.example.mindjobcard.service;

import java.util.List;
import java.util.Optional;

import com.example.mindjobcard.model.TaskTime;

public interface TaskTimeService {
	
	TaskTime saveTaskTime(TaskTime taskTime);
	
	List<TaskTime> getAllTaskTime();
	
	Optional<TaskTime> findById(Long id);
	
	void deleteById(Long id);

}
