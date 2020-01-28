package be.catsandcoding.humidity;
import lombok.Data;

@Data
class PerformSensorCommand extends SensorCommand {
	private String password;
}
