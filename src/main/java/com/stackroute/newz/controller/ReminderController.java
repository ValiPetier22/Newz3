package com.stackroute.newz.controller;

import java.util.List;

import com.stackroute.newz.model.News;
import com.stackroute.newz.util.exception.NewsAlreadyExistsException;
import com.stackroute.newz.util.exception.NewsNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.newz.model.Reminder;
import com.stackroute.newz.service.ReminderService;
import com.stackroute.newz.util.exception.ReminderNotExistsException;

@RestController
public class ReminderController {

	@Autowired
	ReminderService reminderService;

	@GetMapping("/api/v1/reminder")
	public ResponseEntity<List<Reminder>> viewReminders()
	{
		List<Reminder> reminders=reminderService.getAllReminders();
		return new ResponseEntity<List<Reminder>>(reminders,HttpStatus.OK);
	}

	@GetMapping("api/v1/reminder/{reminderId}")
	public ResponseEntity<?> getReminderById(@PathVariable int reminderId){
		try{
			return new ResponseEntity<Reminder>(reminderService.getReminder(reminderId),HttpStatus.OK);
		}catch (ReminderNotExistsException r){
			return new ResponseEntity<String>(r.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("api/v1/reminder")
	public ResponseEntity<?> createReminder(@RequestBody Reminder reminder){
		try {
			return new ResponseEntity<Reminder>(reminderService.addReminder(reminder),HttpStatus.CREATED);
		}catch (Exception e){
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@PutMapping("/api/v1/reminder/{reminderId}")
	public ResponseEntity<?> updateReminder(@RequestBody Reminder reminder){
		try {
			return new ResponseEntity<Reminder>(reminderService.updateReminder(reminder),HttpStatus.OK);
		}catch (ReminderNotExistsException e){
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/api/v1/reminder/{reminderId}")
	public ResponseEntity<?> deleteReminderById(@PathVariable int reminderId){
		try{
			reminderService.deleteReminder(reminderId);
			return new ResponseEntity<String>("Deleted successfully", HttpStatus.OK);
		}catch (ReminderNotExistsException e){
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	

}
