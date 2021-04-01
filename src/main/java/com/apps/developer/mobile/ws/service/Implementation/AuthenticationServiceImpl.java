package com.apps.developer.mobile.ws.service.Implementation;

import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.apps.developer.mobile.ws.dao.DAO;
import com.apps.developer.mobile.ws.dao.implementation.MySQLDAO;
import com.apps.developer.mobile.ws.dto.UserDto;
import com.apps.developer.mobile.ws.exception.AuthenticationException;
import com.apps.developer.mobile.ws.model.response.ErrorMessages;
import com.apps.developer.mobile.ws.service.AuthenticationService;
import com.apps.developer.mobile.ws.service.UserService;
import com.apps.developer.mobile.ws.utils.UserProfileUtils;
import java.util.Base64;

public class AuthenticationServiceImpl implements AuthenticationService {
	DAO database;

	public UserDto authenticate(String userName, String password) throws AuthenticationException {
		UserService usersService = new UsersServiceImpl();
		UserDto storedUser = usersService.getUserByUserName(userName);

		if (storedUser == null) {

			throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());

		}

		String encryptedPassword = null;

		encryptedPassword = new UserProfileUtils().generateSecurePassword(password, storedUser.getSalt());

		boolean authenticated = false;
		if (encryptedPassword != null && encryptedPassword.equalsIgnoreCase(storedUser.getEncryptedPassword()))

			if (userName != null && userName.equalsIgnoreCase(storedUser.getEmail())) {

				authenticated = true;

			}

		if (!authenticated) {
			throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());

		}
		return storedUser;

	}

	public String issueAcessToken(UserDto userProfile) throws AuthenticationException {
		String returnValue = null;
		String newSaltAsPostfix = userProfile.getSalt();
		String accessTokenMaterial = userProfile.getUserId() + newSaltAsPostfix;

		byte[] encryptedAccessToken = null;

		try {
			encryptedAccessToken = new UserProfileUtils().encrypt(userProfile.getEncryptedPassword(),
					accessTokenMaterial);

		} catch (InvalidKeySpecException ex) {
			Logger.getLogger(AuthenticationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AuthenticationException("Failed To issue secure access Token");
		}

		String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);

		// Split token into equal parts
		int tokenLength = encryptedAccessTokenBase64Encoded.length();

		String tokenToSaveToDatabase = encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);
		returnValue = encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);

		userProfile.setToken(tokenToSaveToDatabase);
		updateUserProfile(userProfile);
		return returnValue;

	}

	private void updateUserProfile(UserDto userProfile) {
		this.database = new MySQLDAO();
		try {
			database.openConnection();
			database.updateUser(userProfile);
		} finally {
			database.closeConnection();
		}
	}

	public void restSecurityCridentials(String password, UserDto userProfile) {
		// generate new Value Salt
		UserProfileUtils userUtils = new UserProfileUtils();
		String salt = userUtils.getSalt(30);

		String securePassword = userUtils.generateSecurePassword(password, salt);
		userProfile.setSalt(salt);
		userProfile.setEncryptedPassword(securePassword);

		updateUserProfile(userProfile);
	}

}
