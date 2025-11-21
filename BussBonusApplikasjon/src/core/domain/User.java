package core.domain;

import java.time.LocalDateTime;

public abstract class User {

    protected int userId;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String userType;
    protected LocalDateTime createdAt;

    //Constructor
    public User (int userId, String firstName, String lastName, String email, String userType, LocalDateTime createdAt){
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email=email;
        this.userType = userType;
        this.createdAt = createdAt;
    }

    //getter
    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Each subclass returns its user type
    public abstract String getUserType();

    /*--- Metoder ---

    logIn();
    logOut();
    createUser();
    deleteUser();
     */


}



