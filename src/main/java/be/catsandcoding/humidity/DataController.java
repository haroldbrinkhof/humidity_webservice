package be.catsandcoding.humidity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;
import java.util.Collection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class DataController {

	private final SensorDataRepository repository;

	DataController(SensorDataRepository repository){
		this.repository = repository;
	}

	// sensors add new humidity data
	@PostMapping("/data/add")
	public String index(@RequestBody SensorData newData) {
		try {
		repository.save(newData);
		}
		catch(org.springframework.dao.DataIntegrityViolationException e){
			return "{\"status\":\"failure\"}";
		}	
	 	return "{\"status\":\"success\"}";
	 }

	// get the last datapoint by a specific sensor
	@GetMapping("/data/get/{sensorId}")
	public String getById(@PathVariable String sensorId){
		SensorData data = repository.findBySensorId(sensorId);
		try{
			return new ObjectMapper().writeValueAsString(data);
		} catch(JsonProcessingException e) {
			return "{\"status\":\"failure\"}";
		}

	 }

	// get the last datapoint by all currently active sensors
	@GetMapping("/data/get/all")
	public String getAll(){
		Collection<SensorData> latest = repository.latestEntryForAllActive();
		try{
			return new ObjectMapper().writeValueAsString(latest);
		} catch (JsonProcessingException e) {
			return "{\"status\":\"failure\"}";
		}
	 }


}

