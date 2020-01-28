package be.catsandcoding.humidity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import java.sql.Timestamp;

@Data
@Entity
public class SensorData {
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long entryId;
	@Column(nullable = false)
	private String sensorId;
	@Column(nullable = false)
	private Double humidity;
	@Column(nullable = false)
	private Double temperature;
	@Column(nullable = false)
	private Timestamp enteredOn;

	SensorData() {}

	SensorData(String sensorId, Double humidity, Double temperature, Timestamp enteredOn){
		this.sensorId = sensorId;
		this.humidity = humidity;
		this.temperature = temperature;
		this.enteredOn = enteredOn;
	}
}
