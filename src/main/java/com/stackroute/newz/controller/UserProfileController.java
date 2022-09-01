package com.stackroute.newz.controller;

import java.util.List;

import com.stackroute.newz.model.Reminder;
import com.stackroute.newz.util.exception.ReminderNotExistsException;
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

import com.stackroute.newz.model.UserProfile;
import com.stackroute.newz.service.UserProfileService;
import com.stackroute.newz.util.exception.UserProfileAlreadyExistsException;
import com.stackroute.newz.util.exception.UserProfileNotExistsException;

@RestController
public class UserProfileController {
	@Autowired
	UserProfileService userProfileService;

	@PostMapping("api/v1/user")
	public ResponseEntity<?> registerUser(@RequestBody UserProfile userProfile){
		try {
			return new ResponseEntity<UserProfile>(userProfileService.registerUser(userProfile),HttpStatus.CREATED);
		}catch (UserProfileAlreadyExistsException e){
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/api/v1/user")
	public ResponseEntity<List<UserProfile>> viewUsers()
	{
		List<UserProfile> userProfiles=userProfileService.getAllUserProfiles();
		return new ResponseEntity<List<UserProfile>>(userProfiles,HttpStatus.OK);
	}

	@PutMapping("/api/v1/user/{userId}")
	public ResponseEntity<?> updateUser(@RequestBody UserProfile userProfile,@PathVariable String userId){
		try {
			return new ResponseEntity<UserProfile>(userProfileService.updateUserProfile(userProfile,userId),HttpStatus.OK);
		}catch (UserProfileNotExistsException e){
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("api/v1/user/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable String userId){
		try{
			return new ResponseEntity<UserProfile>(userProfileService.getUserProfile(userId),HttpStatus.OK);
		}catch (UserProfileNotExistsException e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping("/api/v1/user/{userId}")
	public ResponseEntity<?> deleteUserById(@PathVariable String userId){
		try{
			userProfileService.deleteUserProfile(userId);
			return new ResponseEntity<String>("Deleted successfully", HttpStatus.OK);
		}catch (UserProfileNotExistsException e){
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	
}
