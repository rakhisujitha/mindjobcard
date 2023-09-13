package com.example.mindjobcard.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mindjobcard.exception.ResourceNotFoundException;
import com.example.mindjobcard.model.TaskList;
import com.example.mindjobcard.service.BatchOrderService;
import com.example.mindjobcard.service.TaskListService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class TaskListController {
	
	@Autowired
	private TaskListService taskListService;
	
	@Autowired
	private BatchOrderService batchOrderService;
	
	@PostMapping("/batchorder/{id}/tasklist")
	public ResponseEntity<TaskList> saveTaskList(@PathVariable Long id, @RequestBody TaskList taskListRequest) {
		
		TaskList taskList = batchOrderService.findById(id).map(batchorder -> {
			taskListRequest.setBatchOrder(batchorder);
			return taskListService.saveTaskList(taskListRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("BATCH ORDER NOT FOUND"));
		
		return new ResponseEntity<TaskList>(taskList,HttpStatus.CREATED);
	}
	
	@GetMapping("/batchorder/{id}/tasklists")
	public ResponseEntity<List<TaskList>> getTaskListByBatchOrder(@PathVariable Long id) {

		if(!batchOrderService.existsById(id)) {
			throw new ResourceNotFoundException("BATCH ORDER NOT FOUND");
		}
		List<TaskList> taskLists = taskListService.findByBatchOrderId(id);
		return new ResponseEntity<List<TaskList>>(taskLists,HttpStatus.OK);
	}
	
	@GetMapping("/tasklist/{id}")
	public ResponseEntity<List<TaskList>> getTaskListByPerson(@PathVariable String id) {
		
		List<TaskList> taskLists = new ArrayList<TaskList>();
		
		taskListService.findByPerson(id).forEach(taskLists::add);
		
		if(taskLists.isEmpty()) {
			return new ResponseEntity<List<TaskList>>(HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<List<TaskList>>(taskLists,HttpStatus.OK);
		}
	}
	
	@GetMapping("/runningmachine")
	public ResponseEntity<List<TaskList>> getTodayRunningMachine() {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String searchDate = today.format(dateTimeFormatter);
		
		try {
			Date todaydate = dateFormat.parse(searchDate);
			System.out.println(today);
			
			List<TaskList> taskLists = new ArrayList<TaskList>();
			
			taskListService.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(todaydate, todaydate).forEach(taskLists::add);
			
			if(!taskLists.isEmpty()) {
				return new ResponseEntity<List<TaskList>>(taskLists, HttpStatus.OK);
			}
		} catch (ParseException e) {
			
			return new ResponseEntity<List<TaskList>>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<TaskList>>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/tasklist/status/{id}")
	public ResponseEntity<List<TaskList>> getTaskListByPersonAndStatus(@PathVariable String id) {
		
		List<TaskList> taskLists = new ArrayList<TaskList>();
		
		taskListService.findByPersonAndStatusIsNot(id,"Finished").forEach(taskLists::add);
		
		if(taskLists.isEmpty()) {
			return new ResponseEntity<List<TaskList>>(HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<List<TaskList>>(taskLists,HttpStatus.OK);
		}
	}
	
	@GetMapping("/tasklist/status/fin/{id}")
	public ResponseEntity<List<TaskList>> getTaskListByPersonAndStatusfin(@PathVariable String id) {
		
		List<TaskList> taskLists = new ArrayList<TaskList>();
		
		taskListService.findByPersonAndStatusNot(id,"Finished").forEach(taskLists::add);
		
		if(taskLists.isEmpty()) {
			return new ResponseEntity<List<TaskList>>(HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<List<TaskList>>(taskLists,HttpStatus.OK);
		}
	}
	
	@GetMapping("tasklist/id/{id}")
	public ResponseEntity<TaskList> getById(@PathVariable Long id) {
		
		Optional<TaskList> tasklist = taskListService.findById(id);
		
		if(tasklist.isPresent()) {
			return new ResponseEntity<TaskList>(tasklist.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<TaskList>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/tasklist/{id}")
	public ResponseEntity<TaskList> updateTaskList(@PathVariable Long id, @RequestBody TaskList taskListUpdate) {
		
		TaskList taskList = taskListService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("TASK LIST NOT FOUND"));
		
		taskList.setDescription(taskListUpdate.getDescription());
		taskList.setSpecification(taskListUpdate.getSpecification());
		taskList.setStandard(taskListUpdate.getStandard());
		taskList.setProcess(taskListUpdate.getProcess());
		taskList.setStartDate(taskListUpdate.getStartDate());
		taskList.setEndDate(taskListUpdate.getEndDate());
		taskList.setQuantity(taskListUpdate.getQuantity());
		taskList.setPerson(taskListUpdate.getPerson());
		taskList.setStatus(taskListUpdate.getStatus());
		taskList.setMachine(taskListUpdate.getMachine());
		
		return new ResponseEntity<TaskList>(taskListService.saveTaskList(taskList), HttpStatus.OK);
	}
	
	@PutMapping("/tasklist/user/{id}")
	public ResponseEntity<TaskList> updateTaskListByUser(@PathVariable Long id, @RequestBody TaskList taskListUpdate) {
		
		TaskList taskList = taskListService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("TASK LIST NOT FOUND"));

		taskList.setFinishedQty(taskListUpdate.getFinishedQty());
		taskList.setStatus(taskListUpdate.getStatus());
		taskList.setRemark(taskListUpdate.getRemark());
		
		return new ResponseEntity<TaskList>(taskListService.saveTaskList(taskList), HttpStatus.OK);
	}
	
	
	@DeleteMapping("/tasklist/{id}")
	public ResponseEntity<TaskList> deleteById(@PathVariable Long id) {
		
		taskListService.deleteById(id);
		return new ResponseEntity<TaskList>(HttpStatus.NO_CONTENT);
	}
	
}
