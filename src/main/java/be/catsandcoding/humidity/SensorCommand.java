package be.catsandcoding.humidity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;                   
import javax.persistence.GenerationType;                   
import javax.persistence.Column;                   
import javax.persistence.Id;   

@Data
@Entity
class SensorCommand{
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long commandId;
	@Column(nullable = false)
	private String sensorId;
	@Column(nullable = false)
	private int command;
	@Column(nullable = false)
	private int safety;
	@Column(nullable = false)
	private boolean acknowledged;
} 
