package be.catsandcoding.humidity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Collection;
                                                                    
interface SensorsRepository extends JpaRepository<Sensor, String>{

@Query(value = "SELECT s FROM Sensor s WHERE s.active = FALSE")
Collection<Sensor> findInactiveSensors();
@Query(value = "SELECT s FROM Sensor s WHERE s.active = TRUE")
Collection<Sensor> findActiveSensors();
}     
