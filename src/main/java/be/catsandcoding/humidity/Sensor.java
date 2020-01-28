package be.catsandcoding.humidity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.sql.Timestamp;

@Data
@Entity
public class Sensor {
        private @Id String sensorId;
        @Column(nullable = false)
	private String friendlyName = "Please specify an identifier";
	@Column(nullable = false, columnDefinition = "boolean default TRUE")
        private boolean active = true;
	@Column(nullable = false)
	private Timestamp activatedOn = new Timestamp(System.currentTimeMillis());
        private Timestamp deactivatedOn;

        Sensor() {}

        Sensor(String sensorId, String friendlyName, boolean active, Timestamp activatedOn, Timestamp deactivatedOn){
                this.sensorId = sensorId;
                this.friendlyName = friendlyName;
                this.active = active;
                this.activatedOn = activatedOn;
		this.deactivatedOn = deactivatedOn;
        }
	Sensor(String sensorId){
		this.sensorId = sensorId;
	}

}

