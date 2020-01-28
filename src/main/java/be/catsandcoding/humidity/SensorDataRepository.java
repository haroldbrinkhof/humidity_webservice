package be.catsandcoding.humidity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Collection;

interface SensorDataRepository extends JpaRepository<SensorData, Long>{ 

@Query(value = "select * FROM (SELECT ROW_NUMBER() OVER (PARTITION BY sensor_id ORDER BY entered_on DESC) AS n, s.* FROM sensor_data s) x WHERE x.n = 1 AND sensor_id IN (SELECT sensor_id FROM sensor WHERE active = TRUE)", nativeQuery = true)
Collection<SensorData> latestEntryForAllActive();

@Query(value = "SELECT * FROM sensor_data WHERE sensor_id = ?1 ORDER BY entered_on DESC LIMIT 1", nativeQuery = true)
SensorData findBySensorId(String sensorId);
}



