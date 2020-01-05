package java_files;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Visit_description implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;
    private String visit_summary;
    private String prescribed_meds;
    private long visit_id;

    public Visit_description(){
    }

    public Visit_description(String visit_summary, String prescribed_meds, long visit_id){
        this.visit_summary = visit_summary;
        this.prescribed_meds = prescribed_meds;
        this.visit_id = visit_id;
    }

    public String getVisit_summary() {
        return visit_summary;
    }

    public void setVisit_summary(String visit_summary) {
        this.visit_summary = visit_summary;
    }

    public String getPrescribed_meds() {
        return prescribed_meds;
    }

    public void setPrescribed_meds(String prescribed_meds) {
        this.prescribed_meds = prescribed_meds;
    }

    public long getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(long visit_id) {
        this.visit_id = visit_id;
    }

    @Override
    public String toString() {
        return "Visit description{" +
                "id = " + id +
                ", visit_id = " + visit_id +
                ", visit_summary = " + visit_summary +
                ", prescribed_meds: " + prescribed_meds +
                '}';
    }
}
