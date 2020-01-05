package java_files;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import java.io.Serializable;

@Entity
@Inheritance
public class User implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    private String login;
    private String password;
    private String name;
    private String lastname;

    public User(){
    }

    public User(String login, String password, String name, String lastname) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String haslo) {
        this.password = haslo;
    }

    public void setName(String imie) {
        this.name = imie;
    }

    public void setLastname(String nazwisko) {
        this.lastname = nazwisko;
    }

    @Override
    public String toString() {
        return "User {" +
                " id = " + id +
                ", login = " + login +
                ", password = " + password +
                ", name = " + name +
                ", lastname = " + lastname +
                '}';
    }
}