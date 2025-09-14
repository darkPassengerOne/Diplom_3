package site.nomoreparties.stellarburgers.utils;

public class ApiUser {
    public String email;
    public String password;
    public String name;

    public ApiUser(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}