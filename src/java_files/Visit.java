package java_files;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Visit implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;
    private VisitType visit_type;
    private long patient_id;
    private long doctor_id;
    private Date visit_date;
    private VisitStatus status;

    public Visit(){
    }

    public Visit(String visit_type, Date visit_date, long patient_id, long doctor_id){
        this.visit_type = VisitType.getEnumType(visit_type);
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.visit_date = visit_date;
        this.status = VisitStatus.WAITING;
    }

    public enum VisitType {
        PRIVATE ("private"), HOME ("home"), NFZ ("nfz");
        String type;
        VisitType(String type){
            this.type = type;
        }
        public String getType(){
            return type;
        }

        static VisitType getEnumType(String type) {
            switch(type.toLowerCase().trim()) {
                case "private":
                    return VisitType.PRIVATE;
                case "home":
                    return VisitType.HOME;
                case "nfz":
                    return VisitType.NFZ;
                default:
                    System.out.println("błedne dane");
            }
            return VisitType.PRIVATE;
        }
    }

    public String getVisitType(){
        switch(visit_type) {
            case PRIVATE:
                return "private";
            case HOME:
                return "home";
            case NFZ:
                return "nfz";
            default:
                return "";
        }
    }

    public enum VisitStatus {
        WAITING ("waiting"), CONFIRMED ("confirmed"), PAST ("past"), DECLINED ("declined");
        String type;
        VisitStatus(String status){
            this.type = type;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public VisitStatus getStatus() {
        return status;
    }

    public String getStatusString(){
        switch(status){
            case WAITING:
                return "waiting";
            case CONFIRMED:
                return  "confirmed";
            case PAST:
                return "past";
            case DECLINED:
                return "declined";
            default:
                System.out.println("Źle ustawiony status wizyty");
        }
        return "";
    }

    public void setStatus(String status) {
        switch(status){
            case "waiting":
                this.status = VisitStatus.WAITING;
                break;
            case "confirmed":
                this.status = VisitStatus.CONFIRMED;
                break;
            case "past":
                this.status = VisitStatus.PAST;
                break;
            case "declined":
                this.status = VisitStatus.DECLINED;
                break;
            default:
                System.out.println("Nie udało się ustawić statusu wizyty");
        }
    }

    public long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(long patient_id) {
        this.patient_id = patient_id;
    }

    public long getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(long doctor_id) {
        this.doctor_id = doctor_id;
    }

    public Date getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(Date visit_date) {
        this.visit_date = visit_date;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id = " + id +
                ", patient_id = " + patient_id +
                ", doctor_id = " + doctor_id +
                ", visit_type: " + visit_type +
                ", visit_date = " + visit_date + '\'' +
                '}';
    }
}