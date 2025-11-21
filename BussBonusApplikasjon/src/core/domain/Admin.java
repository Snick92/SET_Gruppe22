package core.domain;

import java.time.LocalDateTime;

public class Admin extends User{


    //Constructor
    public Admin(int userId, String firstName, String lastName, String email, String userType, LocalDateTime createdAt) {
        super(userId, firstName, lastName, email, userType, createdAt);  //calls the superclass user
    }

    @Override
    public String getUserType(){
        return "Admin";
    }

    /*--- Metoder --- moves to usecases??

    createRoute();
    deleteRoute();
    updateRoute();


     */

}
