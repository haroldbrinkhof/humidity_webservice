package be.catsandcoding.humidity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.prefs.Preferences;
import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import java.io.File;
import java.io.IOException;


@SpringBootApplication
public class HumidityApplication {
	private static String password;
	private static String dbPassword;
	private static String dbUsername;
	private static String dbDriver;
	private static String dbUrl;

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = null;

		Ini ini = null;	
		try{
			// load our secret variables from the configuration file
			ini = new Ini(new File("/usr/local/etc/humidityApplication/config.ini"));
			java.util.prefs.Preferences prefs = new IniPreferences(ini);
			HumidityApplication.password = prefs.node("general").get("password", null);
			HumidityApplication.dbPassword = prefs.node("database").get("password", null);
			HumidityApplication.dbUsername = prefs.node("database").get("username", null);
			HumidityApplication.dbDriver = prefs.node("database").get("driver", null);
			HumidityApplication.dbUrl = prefs.node("database").get("url", null);

			ctx = SpringApplication.run(HumidityApplication.class, args);
		}
		catch(IOException e){
			System.out.println("configuration file couldn't be opened!\nExiting.");
			SpringApplication.exit(ctx, () -> 1);
		}
	}

	public static boolean checkPassword(String password){
		return password.equals(HumidityApplication.password);
	}

	public static String getDbPassword(){
		return dbPassword;
	}

	public static String getDbUsername(){
		return dbUsername;
	}

	public static String getDbDriver(){
		return dbDriver;
	}

	public static String getDbUrl(){
		return dbUrl;
	}

}
