package be.catsandcoding.humidity;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
	     
	@Bean
	public DataSource getDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(HumidityApplication.getDbDriver());
		dataSourceBuilder.url(HumidityApplication.getDbUrl());
		dataSourceBuilder.username(HumidityApplication.getDbUsername());
		dataSourceBuilder.password(HumidityApplication.getDbPassword());
		return dataSourceBuilder.build();
	}
}
