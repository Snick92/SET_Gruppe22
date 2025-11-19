package core.dto;

//for user to login
public class LogInRequest {
    private String email;
    private String password;

    public LogInRequest(){

    }

    //Getter & setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
