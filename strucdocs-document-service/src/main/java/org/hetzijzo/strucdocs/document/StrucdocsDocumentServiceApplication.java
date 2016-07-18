package org.hetzijzo.strucdocs.document;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories
public class StrucdocsDocumentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrucdocsDocumentServiceApplication.class, args);
    }
}
