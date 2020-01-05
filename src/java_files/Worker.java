package java_files;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Worker extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int clinic_number;
    private String clinic_address;

    public Worker(){
    }

    public Worker(String login, String haslo,
                  String name, String lastname,
                  int clinic_number, String clinic_address){
        super(login, haslo, name, lastname);
        this.clinic_number = clinic_number;
        this.clinic_address = clinic_address;
    }

    public int getClinic_number() {
        return clinic_number;
    }

    public void setClinic_number(int clinic_number) {
        this.clinic_number = clinic_number;
    }

    public String getClinic_address() {
        return clinic_address;
    }

    public void setClinic_address(String clinic_address) {
        this.clinic_address = clinic_address;
    }

    @Override
    public String toString() {
        return super.toString() + " Worker {" +
                " clinic_number = " + clinic_number +
                ", clinic_address = " + clinic_address +
                "} ";
    }
}
