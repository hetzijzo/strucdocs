package org.hetzijzo.strucdocs.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class StrucdocsEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrucdocsEurekaApplication.class, args);
	}
}
