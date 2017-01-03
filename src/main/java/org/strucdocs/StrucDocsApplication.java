package org.strucdocs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.EndpointMBeanExportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {EndpointMBeanExportAutoConfiguration.class})
@EnableJpaRepositories
public class StrucDocsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrucDocsApplication.class, args);
    }
}
