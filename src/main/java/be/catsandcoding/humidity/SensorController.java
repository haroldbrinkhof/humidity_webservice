package be.catsandcoding.humidity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.Random;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class SensorController {

        private final SensorsRepository repository;
	private final SensorCommandRepository cmdRepository;

        SensorController(SensorsRepository repository, SensorCommandRepository cmd){
                this.repository = repository;
		this.cmdRepository = cmd;
        }

	// returns commands queued for a specific sensor; sensor specific functionality
	@GetMapping("/sensor/get/commands/{sensorId}")
	public String sensorGetCommands(@PathVariable String sensorId){
		try{
		return new ObjectMapper()
			.writeValueAsString(cmdRepository.allCommandsBySensor(sensorId));
		} catch (JsonProcessingException e) {
			return "\"status\":\"failure\"";
		}
			
		
	}

	// queue a command for a specific sensor
	@PostMapping("/sensor/add/command")
	public String sensorAddCommand(@RequestBody PerformSensorCommand cmd){
		if(HumidityApplication.checkPassword(cmd.getPassword())){
			SensorCommand passed = new SensorCommand();
			passed.setCommand(cmd.getCommand());
			passed.setSensorId(cmd.getSensorId());
			passed.setSafety(new Random().nextInt());
			passed.setAcknowledged(false); 
			cmdRepository.save(passed);
		}
		else { return "{\"status\":\"denied\"}"; }
		
		return "{\"status\":\"success\"}";
	}

	/* sensor confirms it has received the command, so we delete it from the queue
		by setting acknowledged to true. The database will then delete the record
		using an associated trigger
	*/
	@PostMapping("/sensor/acknowledge/command")
	public String sensorAcknowledgeCommand(@RequestBody PerformSensorCommand cmd){
		if(HumidityApplication.checkPassword(cmd.getPassword())){
			SensorCommand v = null;
			Optional<SensorCommand> verify = cmdRepository.findById(cmd.getCommandId());
			if(verify.isPresent()) v = verify.get();
			if(v.getSensorId().equals(cmd.getSensorId()) &&
				v.getCommandId() == cmd.getCommandId() &&
				v.getCommand() == cmd.getCommand() &&
				v.getSafety() == cmd.getSafety()){

				SensorCommand passed = new SensorCommand();
				passed.setCommand(cmd.getCommand());
				passed.setSensorId(cmd.getSensorId());
				passed.setSafety(new Random().nextInt());
				passed.setAcknowledged(true); 
				cmdRepository.save(passed);
			}
		}
		else { return "{\"status\":\"denied\"}"; }
		
		return "{\"status\":\"success\"}";
	}

	// sensors identify themselves, new sensors get registered in the database
        @PostMapping("/sensor/hello")
	public String sensorHello(@RequestBody SensorHello hello) {
		if(HumidityApplication.checkPassword(hello.getPassword())){
			if(!repository.existsById(hello.getSensorId())){
				repository.save(new Sensor(hello.getSensorId()));
			}
		}
		else { return "{\"status\":\"denied\"}"; }
                
		return "{\"status\":\"success\"}";
        }

	// alter the 'friendly name' assigned to a sensor
        @PostMapping("/sensor/set/friendlyname")
        public String sensorSetFriendlyName(@RequestBody SensorFriendlyName post){
		if(HumidityApplication.checkPassword(post.getPassword())){
			Optional<Sensor> sensor = repository.findById(post.getSensorId());
			if(sensor.isPresent()){
				sensor.get().setFriendlyName(post.getFriendlyName());
				repository.save(sensor.get());
			}
		}
		else { return "\"status\":\"denied\""; }

                return "{\"status\":\"success\"}";
        }

	// get a list of all currently active sensors
        @GetMapping("/sensor/get/activesensors")
        public String sensorGetActiveSensors(){
		Iterable<Sensor> sensors = repository.findActiveSensors();

		try{
			return new ObjectMapper().writeValueAsString(sensors);
		} catch(JsonProcessingException e) {
			return "{\"status\":\"failure\"}";
		}
        }

	// get a list of all currently inactive sensors
	@GetMapping("/sensor/get/inactivesensors")
	public String sensorGetInactiveSensors(){
		Iterable<Sensor> sensors = repository.findInactiveSensors();

		try{
			return new ObjectMapper().writeValueAsString(sensors);
		} catch(JsonProcessingException e) {
			return "{\"status\":\"failure\"}";
		}
	}

	// get a list of all sensors regardless of active status
	@GetMapping("/sensor/get/allsensors")
	public String sensorGetAllSensors(){
		Iterable<Sensor> sensors = repository.findAll();

		try{
			return new ObjectMapper().writeValueAsString(sensors);
		} catch(JsonProcessingException e) {
			return "{\"status\":\"failure\"}";
		}
	}

	// activate a specific sensor
	@PostMapping("/sensor/activate")
	public String sensorActivate(@RequestBody SensorActivation post){
		if(HumidityApplication.checkPassword(post.getPassword())){
			Optional<Sensor> sensor = repository.findById(post.getSensorId());
			if(sensor.isPresent()){
				sensor.get().setActive(post.isActive());
				repository.save(sensor.get());
			}
		}
		else { return "\"status\":\"denied\""; }

                return "{\"status\":\"success\"}";
	}

	// deactivate a specific sensor
	@PostMapping("/sensor/deactivate")
	public String sensorDeactivate(@RequestBody SensorActivation post){
		if(HumidityApplication.checkPassword(post.getPassword())){
			Optional<Sensor> sensor = repository.findById(post.getSensorId());
			if(sensor.isPresent()){
				sensor.get().setActive(post.isActive());
				repository.save(sensor.get());
			}
		}
		else { return "\"status\":\"denied\""; }

                return "{\"status\":\"success\"}";
	}


}

