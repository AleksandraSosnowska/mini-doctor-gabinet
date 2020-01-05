package java_files;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Patient extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int age;
    private Date birth_date;
    private String pesel;
    private boolean insurance_status;
    private String home_address;

    public Patient(){
    }

    public Patient(String login, String password,
                   String name, String lastname,
                   int age, Date birth_date,
                   String pesel, boolean insurance_status,
                   String home_address) {
        super(login, password, name, lastname);
        this.age = age;
        this.birth_date = birth_date;
        this.pesel = pesel;
        this.insurance_status = insurance_status;
        this.home_address = home_address;
    }

    public int getAge() {
        return age;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public String getPesel(){
        return pesel;
    }

    public boolean getInsurance_status(){
        return insurance_status;
    }
    public String getHome_address(){
        return home_address;
    }

    public void setAge(int wiek) {
        this.age = wiek;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void setInsurance_status(boolean insurance_status) {
        this.insurance_status = insurance_status;
    }

    public void setHome_address(String home_address) {
        this.home_address = home_address;
    }

    @Override
    public String toString() {
        return super.toString() +
                " Patient {" +
                "age = " + age +
                ", birth_date = " + birth_date +
                ", pesel = " + pesel +
                ", insurance_status = " + insurance_status +
                ", home_address = " + home_address +
                "} ";
    }
}


