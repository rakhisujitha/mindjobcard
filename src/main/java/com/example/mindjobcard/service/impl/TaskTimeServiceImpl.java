package com.example.mindjobcard.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mindjobcard.model.TaskTime;
import com.example.mindjobcard.repository.TaskTimeRepository;
import com.example.mindjobcard.service.TaskTimeService;

@Service
public class TaskTimeServiceImpl implements TaskTimeService{

	@Autowired
	private TaskTimeRepository taskTimeRepository;
	
	@Override
	public TaskTime saveTaskTime(TaskTime taskTime) {
		return taskTimeRepository.save(taskTime);
	}

	@Override
	public List<TaskTime> getAllTaskTime() {
		return taskTimeRepository.findAll();
	}

	@Override
	public Optional<TaskTime> findById(Long id) {
		return taskTimeRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		taskTimeRepository.deleteById(id);
	}

}
