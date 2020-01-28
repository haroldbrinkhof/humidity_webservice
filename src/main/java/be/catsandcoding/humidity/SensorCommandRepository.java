package be.catsandcoding.humidity;    
                   
import org.springframework.data.jpa.repository.JpaRepository;     
import org.springframework.data.jpa.repository.Query;         
import java.util.Collection;                                   
                                                               
interface SensorCommandRepository extends JpaRepository<SensorCommand, Long> {
	                                                              
@Query(value = "SELECT c FROM SensorCommand c WHERE c.sensorId = ?#{[0]} AND c.acknowledged = FALSE ORDER BY c.commandId ASC")                  
Collection<SensorCommand> allCommandsBySensor(String sensorId);                    
}
