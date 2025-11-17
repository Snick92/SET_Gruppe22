package core.domain;

public abstract class User {

    protected int userId;
    protected String firstName;
    protected String lastName;
    protected String email;

    //Constructor
    public User ( int userId, String firstName, String lastName, String email){
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email=email;
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

    /*--- Metoder ---

    logIn();
    logOut();
    createUser();
    deleteUser();
     */


}



