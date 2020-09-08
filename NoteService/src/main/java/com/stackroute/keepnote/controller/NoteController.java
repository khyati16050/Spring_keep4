package com.stackroute.keepnote.controller;

import com.stackroute.keepnote.exception.NoteAlreadyExistsException;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.service.NoteService;
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
@RequestMapping("api/v1")
public class NoteController {

	/*
	 * Autowiring should be implemented for the NoteService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	private NoteService noteService;
@Autowired
public NoteController(NoteService noteService)
{
	this.noteService = noteService;
}


	/*
	 * Define a handler method which will create a specific note by reading the
	 * Serialized object from request body and save the note details in the
	 * database.This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 201(CREATED) - If the note created successfully. 
	 * 2. 409(CONFLICT) - If the noteId conflicts with any existing user.
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP POST method
	 */
	private ResponseEntity responseEntity;

	@PostMapping("/note")
	public ResponseEntity<?> createNote(@RequestBody Note note)  {

		try {

			Boolean flag = noteService.createNote(note);
			if(flag==true)
			responseEntity = new ResponseEntity(flag, HttpStatus.CREATED);
			if(flag==false)
				responseEntity = new ResponseEntity(flag, HttpStatus.CONFLICT);

		}catch (Exception e) {
			responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
		}
//		catch (Exception e)
//		{
//			System.out.println(e);
//			responseEntity = new ResponseEntity("Some Internal Error Try after sometime" , HttpStatus.INTERNAL_SERVER_ERROR);
//		}

		return responseEntity;
	}
	/*
	 * Define a handler method which will delete a note from a database.
	 * This handler method should return any one of the status messages basis 
	 * on different situations: 
	 * 1. 200(OK) - If the note deleted successfully from database. 
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP Delete
	 * method" where "id" should be replaced by a valid noteId without {}
	 */
@DeleteMapping("/note/{userId}/{noteId}")
public ResponseEntity<?> deleteNote(@PathVariable("userId") String userId,@PathVariable("noteId") int noteId)  {

	try {
		Boolean flag = noteService.deleteNote(userId,noteId);
		if(flag==true)
			responseEntity = new ResponseEntity(flag, HttpStatus.OK);
		if(flag==false)
			responseEntity = new ResponseEntity(flag, HttpStatus.NOT_FOUND);

	}catch (Exception e) {
		responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	return responseEntity;
}
	/*
	 * Define a handler method which will update a specific note by reading the
	 * Serialized object from request body and save the updated note details in a
	 * database. 
	 * This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 200(OK) - If the note updated successfully.
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP PUT method.
	 */
	@DeleteMapping("/note/{userId}")
	public ResponseEntity<?> deleteAllNotes(@PathVariable("userId") String userId)  {

		try {
			Boolean flag = noteService.deleteAllNotes(userId);
			if(flag==true)
				responseEntity = new ResponseEntity(flag, HttpStatus.OK);
			if(flag==false)
				responseEntity = new ResponseEntity(flag, HttpStatus.NOT_FOUND);

		}catch (Exception e) {
			responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}
	/*
	 * Define a handler method which will get us the all notes by a userId.
	 * This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the note found successfully. 
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP GET method
	 */
	@PutMapping("/note/{userId}/{noteId}")
	public ResponseEntity<?> UpdateNote(@PathVariable("userId") String userId,@PathVariable("noteId") int noteId)  {

		try {
			Note note = noteService.getNoteByNoteId(userId, noteId);
			Note newNote = noteService.updateNote(note,noteId,userId);
			if(newNote==null)
			{
				responseEntity = new ResponseEntity(newNote, HttpStatus.NOT_FOUND);
			}
			else
			{
				responseEntity = new ResponseEntity(newNote, HttpStatus.OK);
			}
		}catch (Exception e) {
			responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}
	@GetMapping("/note/{userId}")
	public ResponseEntity<?> getAllNotes(@PathVariable("userId") String userId)  {

		try {
			List<Note> list = noteService.getAllNoteByUserId(userId);
				responseEntity = new ResponseEntity(list, HttpStatus.OK);

		}catch (Exception e) {
			responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}
	/*
	 * Define a handler method which will show details of a specific note created by specific 
	 * user. This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the note found successfully. 
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * This handler method should map to the URL "/api/v1/note/{userId}/{noteId}" using HTTP GET method
	 * where "id" should be replaced by a valid reminderId without {}
	 * 
	 */
	@GetMapping("/note/{userId}/{noteId}")
	public ResponseEntity<?> getNote(@PathVariable("userId") String userId,@PathVariable("noteId") int noteId)  {

		try {
			Note note = noteService.getNoteByNoteId(userId, noteId);
			if(note==null)
			{
				responseEntity = new ResponseEntity(note, HttpStatus.NOT_FOUND);
			}
			else
			{
				responseEntity = new ResponseEntity(note, HttpStatus.OK);
			}
		}catch (Exception e) {
			responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}
}
