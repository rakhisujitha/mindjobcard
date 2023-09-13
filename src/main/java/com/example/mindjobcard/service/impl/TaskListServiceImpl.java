package com.example.mindjobcard.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mindjobcard.model.TaskList;
import com.example.mindjobcard.repository.TaskListRepository;
import com.example.mindjobcard.service.TaskListService;

@Service
public class TaskListServiceImpl implements TaskListService{

	@Autowired
	private TaskListRepository taskListRepository;
	
	@Override
	public TaskList saveTaskList(TaskList taskList) {
		return taskListRepository.save(taskList);
	}

	@Override
	public List<TaskList> getAllTaskList() {
		return taskListRepository.findAll();
	}

	@Override
	public Optional<TaskList> findById(Long id) {
		return taskListRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		taskListRepository.deleteById(id);
	}

	@Override
	public List<TaskList> findByBatchOrderId(Long id) {
		return taskListRepository.findByBatchOrderId(id);
	}

	@Override
	public List<TaskList> findByPerson(String name) {
		return taskListRepository.findByPerson(name);
	}

	@Override
	public List<TaskList> findByPersonAndStatusIsNot(String name, String status) {
		return taskListRepository.findByPersonAndStatusIsNot(name, status);
	}

	@Override
	public List<TaskList> findByPersonAndStatusNot(String name, String status) {
		return taskListRepository.findByPersonAndStatusNot(name, status);
	}

	@Override
	public List<TaskList> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date startdate, Date enddate) {
		return taskListRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(startdate, enddate);
	}

}
