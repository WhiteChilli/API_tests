package dto;

public class Courier {

    int id;
    String login;
    String password;
    String name;

    public Courier(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }


    public Courier() {
    }

}
