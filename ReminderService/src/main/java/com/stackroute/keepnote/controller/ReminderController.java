package com.stackroute.keepnote.controller;

import com.stackroute.keepnote.exception.ReminderNotCreatedException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.service.ReminderService;
//import org.graalvm.compiler.lir.sparc.*;
//import org.graalvm.compiler.lir.sparc.SPARCArithmetic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@RequestMapping("/api/v1")
public class ReminderController {
private ReminderService reminderService;
private ResponseEntity responseEntity;

	/*
	 * From the problem statement, we can understand that the application requires
	 * us to implement five functionalities regarding reminder. They are as
	 * following:
	 * 
	 * 1. Create a reminder 
	 * 2. Delete a reminder 
	 * 3. Update a reminder 
	 * 4. Get all reminders by userId 
	 * 5. Get a specific reminder by id.
	 * 
	 */

	/*
	 * Autowiring should be implemented for the ReminderService. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword
	 */
@Autowired
	public ReminderController(ReminderService reminderService) {
	this.reminderService = reminderService;
	}
	@PostMapping("/reminder")
	public ResponseEntity<?> saveUser(@RequestBody Reminder reminder)  {

		try {
			Reminder newReminder = reminderService.createReminder(reminder);
			responseEntity = new ResponseEntity(newReminder, HttpStatus.CREATED);

		}catch (ReminderNotCreatedException e) {
			responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
		}
		catch (Exception e)
		{
			System.out.println(e);
			responseEntity = new ResponseEntity("Some Internal Error Try after sometime" , HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	/*
	 * Define a handler method which will create a reminder by reading the
	 * Serialized reminder object from request body and save the reminder in
	 * database. Please note that the reminderId has to be unique. This handler
	 * method should return any one of the status messages basis on different
	 * situations: 
	 * 1. 201(CREATED - In case of successful creation of the reminder
	 * 2. 409(CONFLICT) - In case of duplicate reminder ID
	 *
	 * This handler method should map to the URL "/api/v1/reminder" using HTTP POST
	 * method".
	 */

	/*
	 * Define a handler method which will delete a reminder from a database.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the reminder deleted successfully from database. 
	 * 2. 404(NOT FOUND) - If the reminder with specified reminderId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/reminder/{id}" using HTTP Delete
	 * method" where "id" should be replaced by a valid reminderId without {}
	 */
	@DeleteMapping("/reminder/{reminderId}")
	public ResponseEntity<?> deleteReminder(@PathVariable("reminderId") String reminderId)
	{
		try {
			boolean flag = reminderService.deleteReminder(reminderId);
			responseEntity = new ResponseEntity(flag,HttpStatus.OK);
		}
		catch (ReminderNotFoundException e) {
			responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		catch (Exception e)
		{
			System.out.println(e);
			responseEntity = new ResponseEntity("Some Internal Error Try after sometime" , HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}
	/*
	 * Define a handler method which will update a specific reminder by reading the
	 * Serialized object from request body and save the updated reminder details in
	 * a database. This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 200(OK) - If the reminder updated successfully. 
	 * 2. 404(NOT FOUND) - If the reminder with specified reminderId is not found. 
	 * 
	 * This handler method should map to the URL "/api/v1/reminder/{id}" using HTTP PUT
	 * method.
	 */
	@PutMapping("/reminder/{reminderId}")
	public ResponseEntity<?> updateReminder(@PathVariable("reminderId") String reminderId, @RequestBody Reminder reminder)
	{
		try {
			Reminder newReminder = reminderService.updateReminder(reminder,reminderId);
			responseEntity = new ResponseEntity(newReminder,HttpStatus.OK);
		}
		catch (ReminderNotFoundException e) {
			responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		catch (Exception e)
		{
			System.out.println(e);
			responseEntity = new ResponseEntity("Some Internal Error Try after sometime" , HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}
	/*
	 * Define a handler method which will show details of a specific reminder. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the reminder found successfully. 
	 * 2. 404(NOT FOUND) - If the reminder with specified reminderId is not found. 
	 * 
	 * This handler method should map to the URL "/api/v1/reminder/{id}" using HTTP GET method
	 * where "id" should be replaced by a valid reminderId without {}
	 */
	@GetMapping("/reminder/{reminderId}")
	public ResponseEntity<?> getReminderById(@PathVariable("reminderId") String reminderId)
	{
		try {
			Reminder newReminder = reminderService.getReminderById(reminderId);
			responseEntity = new ResponseEntity(newReminder, HttpStatus.OK);

		}catch (ReminderNotFoundException e) {
			responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}

	/*
	 * Define a handler method which will get us the all reminders.
	 * This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the reminder found successfully. 
	 * 2. 404(NOT FOUND) - If the reminder with specified reminderId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/reminder" using HTTP GET method
	 */
	@GetMapping("/reminder")
	public ResponseEntity<?> getAllReminder()
	{
		try {
			List<Reminder> list = reminderService.getAllReminders();

			if(list==null)
			{
				responseEntity = new ResponseEntity(list, HttpStatus.NOT_FOUND);
			}
			else
			{
				responseEntity = new ResponseEntity(list, HttpStatus.OK);
			}

		}
		catch (Exception e)
		{
			System.out.println(e);
			responseEntity = new ResponseEntity("Some Internal Error Try after sometime" , HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

}
