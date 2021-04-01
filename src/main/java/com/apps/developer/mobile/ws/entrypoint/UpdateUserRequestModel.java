package com.apps.developer.mobile.ws.entrypoint;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class UpdateUserRequestModel {
    private String firstName;
    private String lastName;

 
    public String getFirstName() {
        return firstName;
    }

   
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    public String getLastName() {
        return lastName;
    }

    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
}