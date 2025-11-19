package core.domain;

import java.time.LocalDateTime;

public class EndUser extends User{

    //Constructor
    public EndUser(int userId, String fistName, String lastName, String email, LocalDateTime createdAt){
        super(userId, fistName, lastName, email, createdAt); //calls the superclass user
    }

/*--- Metoder ---  moves to usecases??

showUserProfile();
searchRoute();

 */
}
