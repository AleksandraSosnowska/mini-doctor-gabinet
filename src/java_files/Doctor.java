package java_files;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Doctor extends User implements Serializable{

    static final long serialVersionUID = 1L;

    private String specialization;
    private String job_position;

    public Doctor(){}

    public Doctor(String login, String password,
                  String name, String lastname,
                  String specialization, String job_position){
        super(login, password, name, lastname);
        this.specialization = specialization;
        this.job_position = job_position;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specjalizacja) {
        this.specialization = specjalizacja;
    }

    public String getJob_position() {
        return job_position;
    }

    public void setJob_position(String job_position) {
        this.job_position = job_position;
    }

    @Override
    public String toString() {
        return super.toString() + " Doctor {" +
                " specialization = " + specialization +
                ", job_position = " + job_position +
                "} ";
    }
}
