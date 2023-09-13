package com.example.mindjobcard.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mindjobcard.exception.ResourceNotFoundException;
import com.example.mindjobcard.model.TaskTime;
import com.example.mindjobcard.service.TaskListService;
import com.example.mindjobcard.service.TaskTimeService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class TaskTimeController {
	
	@Autowired
	private TaskTimeService taskTimeService;
	
	@Autowired
	private TaskListService taskListService;
	
	@PostMapping("/tasklist/{id}/tasktime")
	public ResponseEntity<TaskTime> saveTaskTime(@PathVariable Long id,@RequestBody TaskTime taskTimeRequest) {
		
		TaskTime taskTime = taskListService.findById(id).map(tasklist -> {
			taskTimeRequest.setTaskList(tasklist);
			return taskTimeService.saveTaskTime(taskTimeRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("TaskList Not Found"));
		
		return new ResponseEntity<TaskTime>(taskTime,HttpStatus.CREATED);
	}
	
	@GetMapping("/tasktimes")
	public ResponseEntity<List<TaskTime>> getAllTaskTime() {
		
		List<TaskTime> taskTimes = new ArrayList<TaskTime>();
		
		taskTimeService.getAllTaskTime().forEach(taskTimes::add);
		
		if(taskTimes.isEmpty()) {
			return new ResponseEntity<List<TaskTime>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<TaskTime>>(taskTimes,HttpStatus.OK);
	}

}
