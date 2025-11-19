package core.domain;

import java.time.LocalDateTime;

public class Admin extends User{


    //Constructor
    public Admin(int userId, String firstName, String lastName, String email, LocalDateTime createdAt) {
        super(userId, firstName, lastName, email, createdAt);  //calls the superclass user
    }

    /*--- Metoder --- moves to usecases??

    createRoute();
    deleteRoute();
    updateRoute();


     */

}
