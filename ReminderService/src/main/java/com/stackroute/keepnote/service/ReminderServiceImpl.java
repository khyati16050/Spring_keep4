package com.stackroute.keepnote.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.stackroute.keepnote.exception.ReminderNotCreatedException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class ReminderServiceImpl implements ReminderService {

	/*
	 * Autowiring should be implemented for the ReminderRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	private ReminderRepository reminderRepository;
@Autowired
public ReminderServiceImpl(ReminderRepository reminderRepository)
{
	this.reminderRepository = reminderRepository;
}
	/*
	 * This method should be used to save a new reminder.Call the corresponding
	 * method of Respository interface.
	 */
	public Reminder createReminder(Reminder reminder) throws ReminderNotCreatedException {
		Optional<Reminder> optional =  reminderRepository.findById(reminder.getReminderId());
		Reminder newReminder = reminderRepository.insert(reminder);
		if(newReminder==null)
		{
			throw new ReminderNotCreatedException("Not created");
		}
		else
		{
			return newReminder;
		}

	}

	/*
	 * This method should be used to delete an existing reminder.Call the
	 * corresponding method of Respository interface.
	 */
	public boolean deleteReminder(String reminderId) throws ReminderNotFoundException {
		boolean flag = true;
//
		Optional<Reminder> optional = reminderRepository.findById(reminderId);
		if(optional.isPresent())
		{
			reminderRepository.deleteById(reminderId);
			flag = true;
		}
		else
		{
			throw new ReminderNotFoundException("Reminder not found");
		}
		return flag;
	}

	/*
	 * This method should be used to update a existing reminder.Call the
	 * corresponding method of Respository interface.
	 */
	public Reminder updateReminder(Reminder reminder, String reminderId) throws ReminderNotFoundException {
		Optional<Reminder> optional =  reminderRepository.findById(reminderId);
		if(optional.isPresent())
		{
			String id = reminder.getReminderId();
			String name = reminder.getReminderName();
			String des = reminder.getReminderDescription();
			String type = reminder.getReminderType();
			String created = reminder.getReminderCreatedBy();
			Date date = reminder.getReminderCreationDate();
			Reminder newReminder = new Reminder(id,name,des,type,created,date);

			reminderRepository.save(newReminder);

			return reminder;
		}
		else
		{
			throw new ReminderNotFoundException("not found");
		}
	}

	/*
	 * This method should be used to get a reminder by reminderId.Call the
	 * corresponding method of Respository interface.
	 */
	public Reminder getReminderById(String reminderId) throws ReminderNotFoundException {
		Optional<Reminder> optional = reminderRepository.findById(reminderId);
		if(optional.isPresent())
		{
			Reminder reminder = optional.get();
			return reminder;
		}
		else
		{
			throw new ReminderNotFoundException("not found");
		}
	}

	/*
	 * This method should be used to get all reminders. Call the corresponding
	 * method of Respository interface.
	 */

	public List<Reminder> getAllReminders() {
	return reminderRepository.findAll();

	}

}
