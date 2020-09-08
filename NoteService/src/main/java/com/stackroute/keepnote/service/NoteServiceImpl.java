package com.stackroute.keepnote.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;
import com.stackroute.keepnote.repository.NoteRepository;
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
public class NoteServiceImpl implements NoteService{
private NoteRepository noteRepository;


	/*
	 * Autowiring should be implemented for the NoteRepository and MongoOperation.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	
	/*
	 * This method should be used to save a new note.
	 */
	@Autowired
	public NoteServiceImpl(NoteRepository noteRepository)
	{
		this.noteRepository = noteRepository;
	}
	public boolean createNote(Note note) {
		List<Note> list = new ArrayList<>();
		String id = note.getNoteCreatedBy();
		list.add(note);
		NoteUser noteUser = new NoteUser(id,list);

		NoteUser newNoteUser = noteRepository.insert(noteUser);
		if(newNoteUser==null)
		{
			return false;
		}
		return true;
	}
	
	/* This method should be used to delete an existing note. */

	
	public boolean deleteNote(String userId, int noteId) {
		NoteUser noteUser = noteRepository.findById(userId).get();
		List<Note> list = noteUser.getNotes();
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i).getNoteId()==noteId)
			{
				list.remove(i);
				break;
			}
		}
		noteUser.setNotes(list);
		noteRepository.save(noteUser);
		return true;
	}
	
	/* This method should be used to delete all notes with specific userId. */

	
	public boolean deleteAllNotes(String userId) {
		NoteUser noteUser = noteRepository.findById(userId).get();
		List<Note> list = null;
		noteUser.setNotes(list);
		noteRepository.save(noteUser);
		return true;
	}

	/*
	 * This method should be used to update a existing note.
	 */
	public Note updateNote(Note note, int id, String userId) throws NoteNotFoundExeption {
		try{
			NoteUser noteUser = noteRepository.findById(userId).get();
			boolean flag = false;
			List<Note> list = noteUser.getNotes();
			for(int i=0;i<list.size();i++)
			{
				if(list.get(i).getNoteId()==id)
				{
					list.remove(i);
					list.add(i,note);
					flag = true;
					break;
				}
			}
			if(flag!=false)
			{
				noteUser.setNotes(list);
				noteRepository.save(noteUser);
				return note;
			}

		}
		catch (NoSuchElementException e)
		{
			throw new NoteNotFoundExeption("Note not found");
		}
			return null;

	}

	/*
	 * This method should be used to get a note by noteId created by specific user
	 */
	public Note getNoteByNoteId(String userId, int noteId) throws NoteNotFoundExeption {
		try{
			NoteUser noteUser = noteRepository.findById(userId).get();
			boolean flag = false;
			Note note = null;
			List<Note> list = noteUser.getNotes();
			for(int i=0;i<list.size();i++)
			{
				if(list.get(i).getNoteId()==noteId)
				{
					note = list.get(i);
					flag = true;
					break;
				}
			}
			if(flag!=false)
			{
				return note;
			}

		}
		catch (NoSuchElementException e)
		{
			throw new NoteNotFoundExeption("Note not found");
		}
		return null;
	}

	/*
	 * This method should be used to get all notes with specific userId.
	 */
	public List<Note> getAllNoteByUserId(String userId) {
		NoteUser noteUser = noteRepository.findById(userId).get();
		List<Note> list = noteUser.getNotes();
		return list;
	}

}
