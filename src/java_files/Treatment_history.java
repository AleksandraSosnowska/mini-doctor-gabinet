package java_files;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Treatment_history implements Serializable{

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;
    private String disease;
    private String recommendations;
    private String treatment_efficacy;

    public Treatment_history(){
    }

    public Treatment_history(String disease,
                             String recommendations,
                             String treatment_efficacy){
        this.disease = disease;
        this.recommendations = recommendations;
        this.treatment_efficacy = treatment_efficacy;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getTreatment_efficacy() {
        return treatment_efficacy;
    }

    public void setTreatment_efficacy(String treatment_efficacy) {
        this.treatment_efficacy = treatment_efficacy;
    }

    @Override
    public String toString() {
        return "Treatment History{" +
                "id = " + id +
                ", disease = " + disease +
                ", recommendations = '" + recommendations + '\'' +
                ", treatment_efficacy = '" + treatment_efficacy + '\'' +
                '}';
    }
}
