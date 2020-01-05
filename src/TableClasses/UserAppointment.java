package TableClasses;

public class UserAppointment {

    private long visit_id;
    private String appointment_type;
    private String doctor_spec;
    private String doctor_name;
    private String patient_name;
    private String date;
    private String status;

    public UserAppointment(long visit_id, String appointment_type, String doctor_spec,
                           String doctor_name, String date, String status, String patient_name){
        this.visit_id = visit_id;
        this.appointment_type = appointment_type;
        this.doctor_spec = doctor_spec;
        this.doctor_name = doctor_name;
        this.date = date;
        this.status = status;
        this.patient_name = patient_name;
    }

    public long getVisit_Id() {
        return visit_id;
    }

    public void setVisit_Id(long visit_id) {
        this.visit_id = visit_id;
    }

    public String getAppointment_type() {
        return appointment_type;
    }

    public void setAppointment_type(String appointment_type) {
        this.appointment_type = appointment_type;
    }

    public String getDoctor_spec() {
        return doctor_spec;
    }

    public void setDoctor_spec(String doctor_spec) {
        this.doctor_spec = doctor_spec;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPatient_name() { return patient_name; }

    public void setPatient_name(String patient_name) { this.patient_name = patient_name; }

    public long getVisit_id() { return visit_id; }

    public void setVisit_id(long visit_id) { this.visit_id = visit_id; }

    @Override
    public String toString() {
        return "UserAppointment {" +
                " visit_id = " + visit_id +
                ", doctor_spec = " + doctor_spec +
                ", doctor_name = " + doctor_name +
                ", patient_name: " + patient_name +
                ", date = " + date +
                ", status = " + status +
                '}';
    }
}
