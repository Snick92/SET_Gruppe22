package core.domain;

import java.time.LocalDateTime;

public abstract class User {

    protected int userId;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected LocalDateTime createdAt;

    //Constructor
    public User (int userId, String firstName, String lastName, String email, LocalDateTime createdAt){
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email=email;
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

    /*--- Metoder ---

    logIn();
    logOut();
    createUser();
    deleteUser();
     */


}



