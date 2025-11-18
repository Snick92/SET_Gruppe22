package core.domain;

public class Admin extends User{


    //Constructor
    public Admin(int userId, String firstName, String lastName, String email) {
        super(userId, firstName, lastName, email);  //calls the superclass user
    }

    /*--- Metoder --- moves to usecases??

    createRoute();
    deleteRoute();
    updateRoute();


     */

}
