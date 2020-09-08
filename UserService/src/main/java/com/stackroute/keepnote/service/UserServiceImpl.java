package com.stackroute.keepnote.service;

import com.stackroute.keepnote.exceptions.UserAlreadyExistsException;
import com.stackroute.keepnote.exceptions.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

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
public class UserServiceImpl implements UserService {

	/*
	 * Autowiring should be implemented for the UserRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */

	/*
	 * This method should be used to save a new user.Call the corresponding method
	 * of Respository interface.
	 */
	private UserRepository userRepository;
	@Autowired
	public UserServiceImpl(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}
	@Override
	public User registerUser(User user) throws UserAlreadyExistsException {
		Optional<User> optional =  userRepository.findById(user.getUserId());
		User newUser = userRepository.insert(user);
		if(newUser==null)
		{
			throw new UserAlreadyExistsException("User Already Exists");
		}
		else
		{
			return newUser;
		}

	}

	/*
	 * This method should be used to update a existing user.Call the corresponding
	 * method of Respository interface.
	 */

	public User updateUser(String userId,User user) throws UserNotFoundException {
		Optional<User> optional =  userRepository.findById(user.getUserId());
		if(optional.isPresent())
		{
			String id = user.getUserId();
			String name = user.getUserName();
			String password = user.getUserPassword();
			String mobile = user.getUserMobile();
			Date date = user.getUserAddDate();
			User newUser = new User();
			newUser.setUserId(userId);
			newUser.setUserName(name);
			newUser.setUserPassword(password);
			newUser.setUserMobile(mobile);
			newUser.setUserAddedDate(date);

			userRepository.save(newUser);

			return user;
		}
		else
		{
			throw new UserNotFoundException("User not found");
		}


	}

	/*
	 * This method should be used to delete an existing user. Call the corresponding
	 * method of Respository interface.
	 */

	public boolean deleteUser(String userId) throws UserNotFoundException {
		boolean flag = true;
//
		Optional<User> optional = userRepository.findById(userId);
		if(optional.isPresent())
		{
			userRepository.deleteById(userId);
			flag = true;
		}
		else
		{
			throw new UserNotFoundException("User not found");
		}
		return flag;
	}

	/*
	 * This method should be used to get a user by userId.Call the corresponding
	 * method of Respository interface.
	 */

	public User getUserById(String userId) throws UserNotFoundException {
		Optional<User> optional = userRepository.findById(userId);
		if(optional.isPresent())
		{
			User user = optional.get();
			return user;
		}
		else
		{
			throw new UserNotFoundException("User not found");
		}

	}

}
