package com.stackroute.newz.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.newz.model.UserProfile;
import com.stackroute.newz.repository.UserProfileRepository;
import com.stackroute.newz.util.exception.UserProfileAlreadyExistsException;
import com.stackroute.newz.util.exception.UserProfileNotExistsException;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	UserProfileRepository userProfileRepository;

	public UserProfile registerUser(UserProfile user) throws UserProfileAlreadyExistsException {
		Optional<UserProfile> existUserProfile=userProfileRepository.findById(user.getUserId());
		if (existUserProfile.isPresent()){
			throw new UserProfileAlreadyExistsException();
		}
		else {
			return userProfileRepository.save(user);
		}
	}

	public UserProfile updateUserProfile(UserProfile user, String userId) throws UserProfileNotExistsException{
			UserProfile existUser=userProfileRepository.getOne(userId);
			if (existUser==null){
				throw new UserProfileNotExistsException();
			}
			else {
				return userProfileRepository.saveAndFlush(user);
			}
	}

	public void deleteUserProfile(String userId) throws UserProfileNotExistsException {
		UserProfile existUser=userProfileRepository.getOne(userId);
		if (existUser==null){
			throw new UserProfileNotExistsException();
		}
		else {
			userProfileRepository.deleteById(userId);
		}
		
	}

	public UserProfile getUserProfile(String userId) throws UserProfileNotExistsException {
		Optional<UserProfile> existUser=userProfileRepository.findById(userId);
		if (existUser.isEmpty()){
			throw new UserProfileNotExistsException();
		}
		else {
			return existUser.get();
		}
	}

	public List<UserProfile> getAllUserProfiles() {
		return userProfileRepository.findAll();
	}

}
