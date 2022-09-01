package com.stackroute.newz.service;

import java.util.List;
import java.util.Optional;

import com.stackroute.newz.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.newz.model.Reminder;
import com.stackroute.newz.repository.ReminderRepository;
import com.stackroute.newz.service.ReminderService;
import com.stackroute.newz.util.exception.ReminderNotExistsException;

@Service
public class ReminderServiceImpl implements ReminderService {

	@Autowired
	ReminderRepository reminderRepository;

	public Reminder addReminder(Reminder reminder) {
		return reminderRepository.save(reminder);
	}

	public Reminder updateReminder(Reminder reminder) throws ReminderNotExistsException {
		Reminder existReminder=reminderRepository.getOne(reminder.getReminderId());
		if (existReminder==null){
			throw new ReminderNotExistsException();
		}
		else {
			return reminderRepository.saveAndFlush(reminder);
		}
	}

	public void deleteReminder(int reminderId) throws ReminderNotExistsException {
		Reminder existReminder=reminderRepository.getOne(reminderId);
		if (existReminder==null){
			throw new ReminderNotExistsException();
		}
		else {
			reminderRepository.deleteById(reminderId);
		}
	
	}

	public Reminder getReminder(int reminderId) throws ReminderNotExistsException {
		Optional<Reminder> existReminder=reminderRepository.findById(reminderId);
		if (existReminder.isEmpty()){
			throw new ReminderNotExistsException();
		}
		else {
			return existReminder.get();
		}

	}

	public List<Reminder> getAllReminders() {
		return reminderRepository.findAll();
	}

}
