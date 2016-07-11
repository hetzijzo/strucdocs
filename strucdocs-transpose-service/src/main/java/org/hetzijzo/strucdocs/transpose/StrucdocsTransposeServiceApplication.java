package org.hetzijzo.strucdocs.transpose;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class StrucdocsTransposeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrucdocsTransposeServiceApplication.class, args);
    }
}
