package james.LineBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class LineBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(LineBotApplication.class, args);
	}

}
