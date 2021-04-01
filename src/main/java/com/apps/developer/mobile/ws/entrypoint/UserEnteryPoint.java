package com.apps.developer.mobile.ws.entrypoint;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.BeanUtils;

import com.apps.developer.mobile.ws.annotations.Secured;
import com.apps.developer.mobile.ws.dto.UserDto;
import com.apps.developer.mobile.ws.model.request.CreateUserRequestModel;
import com.apps.developer.mobile.ws.model.response.UserProfileRest;
import com.apps.developer.mobile.ws.service.UserService;
import com.apps.developer.mobile.ws.service.Implementation.UsersServiceImpl;

@Path("/users")
public class UserEnteryPoint {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserProfileRest createUser(CreateUserRequestModel requestObject) {
		UserProfileRest returnValue = new UserProfileRest();

		// Prepare UserDTO
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(requestObject, userDto);

		// Create new user
		UserService userService = new UsersServiceImpl();
		UserDto createdUserProfile = userService.createUser(userDto);

		// Prepare response
		BeanUtils.copyProperties(createdUserProfile, returnValue);

		return returnValue;
	}

	@Secured
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserProfileRest getUserProfile(@PathParam("id") String id) {
		UserProfileRest returnValue = null;

		UserService userService = new UsersServiceImpl();
		UserDto userProfile = userService.getUser(id);

		returnValue = new UserProfileRest();
		BeanUtils.copyProperties(userProfile, returnValue);

		return returnValue;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<UserProfileRest> getUsers(@DefaultValue("0") @QueryParam("start") int start,
			@DefaultValue("50") @QueryParam("limit") int limit) {

		UserService userService = new UsersServiceImpl();
		List<UserDto> users = userService.getUsers(start, limit);

		List<UserProfileRest> returnValue = new ArrayList<UserProfileRest>();
		for (UserDto userDto : users) {
			UserProfileRest userModel = new UserProfileRest();

			BeanUtils.copyProperties(userDto, userModel);
			userModel.setHref("/users/" + userDto.getUserId());
			returnValue.add(userModel);
		}

		return returnValue;

	}
	
	
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserProfileRest updateUserDetails(@PathParam("id") String id,
            UpdateUserRequestModel userDetails) {
        
        UserService userService = new UsersServiceImpl();
        UserDto storedUserDetails = userService.getUser(id);
        
         // Set only those fields you would like to be updated with this request
        if(userDetails.getFirstName() !=null && !userDetails.getFirstName().isEmpty())
        {
            storedUserDetails.setFirstName(userDetails.getFirstName());  
        }
        storedUserDetails.setLastName(userDetails.getLastName());
        
        // Update User Details
        userService.updateUserDetails(storedUserDetails);
        
        // Prepare return value 
        UserProfileRest returnValue = new UserProfileRest();
        BeanUtils.copyProperties(storedUserDetails, returnValue);


        return returnValue;
	
	
	
	
	
	
	
	
	
	
    }
	

}
