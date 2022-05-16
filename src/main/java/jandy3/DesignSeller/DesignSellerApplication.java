package jandy3.DesignSeller;

import jandy3.DesignSeller.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class DesignSellerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesignSellerApplication.class, args);
	}

}
