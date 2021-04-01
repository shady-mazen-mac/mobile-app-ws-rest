package com.apps.developer.mobile.ws.service.Implementation;

import java.util.List;

import com.apps.developer.mobile.ws.dao.DAO;
import com.apps.developer.mobile.ws.dao.implementation.MySQLDAO;
import com.apps.developer.mobile.ws.dto.UserDto;
import com.apps.developer.mobile.ws.exception.CouldNotCreateRecordException;
import com.apps.developer.mobile.ws.exception.CouldNotUpdateRecordException;
import com.apps.developer.mobile.ws.exception.NoRecordFoundException;
import com.apps.developer.mobile.ws.model.response.ErrorMessages;
import com.apps.developer.mobile.ws.service.UserService;
import com.apps.developer.mobile.ws.utils.UserProfileUtils;

public class UsersServiceImpl implements UserService {

	DAO database;

	public UsersServiceImpl()

	{

		this.database = new MySQLDAO();

	}

	UserProfileUtils userProfileUtlis = new UserProfileUtils();

	public UserDto createUser(UserDto user) {
		UserDto returnValue = new UserDto();

		userProfileUtlis.validateRequiredFields(user);

		// validate the Required Fields

		// check if user already exists

		UserDto existingUser = this.getUserByUserName(user.getEmail());
		if (existingUser != null) {
			throw new CouldNotCreateRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.name());

		}

		// Create an Entity Object

		// Generate secure public user id
		String userId = userProfileUtlis.generateUserId(30);
		user.setUserId(userId);

		// Genereate Salt

		String salt = userProfileUtlis.getSalt(30);

		// Generate a secure ppasword

		String encryptedPassword = userProfileUtlis.generateSecurePassword(user.getPassword(), salt);
		user.setSalt(salt);
		user.setEncryptedPassword(encryptedPassword);

		// record data in the database

		returnValue = this.saveUser(user);

		// rerturn back the user profile

		return returnValue;
	}

	public UserDto getUser(String id) {
		UserDto returnValue = null;

		try {

			this.database.openConnection();
			;
			returnValue = this.database.getUser(id);

		} catch (Exception ex) {

			ex.printStackTrace();
			throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		} finally {

			this.database.closeConnection();
		}

		return returnValue;

	}

	@Override
	public UserDto getUserByUserName(String userName) {

		UserDto userDto = null;

		if (userName == null || userName.isEmpty()) {
			return userDto;

		}

		// connect to Database

		try {
			this.database.openConnection();
			userDto = this.database.getUserByUserName(userName);

		} finally {

			this.database.closeConnection();

		}

		return userDto;

	}

	private UserDto saveUser(UserDto user) {

		UserDto returnValue = null;
		try {
			this.database.openConnection();
			returnValue = this.database.saveUser(user);

		} finally {

			this.database.closeConnection();

		}

		return returnValue;

	}

	@Override
	public List<UserDto> getUsers(int start, int limit) {
		List<UserDto> users = null;
		try {
			this.database.openConnection();
			users = this.database.getUsers(start, limit);

		} finally {

			this.database.closeConnection();

		}

		return users;
	}

	public void updateUserDetails(UserDto userDetails) {
		try {
			// Connect to database
			this.database.openConnection();
			// Update User Details
			this.database.updateUser(userDetails);

		} catch (Exception ex) {
			throw new CouldNotUpdateRecordException(ex.getMessage());
		} finally {
			this.database.closeConnection();
		}
	}

}
