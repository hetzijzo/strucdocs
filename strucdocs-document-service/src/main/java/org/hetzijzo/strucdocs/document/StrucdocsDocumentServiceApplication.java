package org.hetzijzo.strucdocs.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
@EnableDiscoveryClient
public class StrucdocsDocumentServiceApplication {

    @Autowired
    private BinaryStoreConfiguration configuration;

    public static void main(String[] args) {
        SpringApplication.run(StrucdocsDocumentServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner initDirs() {
        return (String[] args) -> new File(configuration.getBinaryStoreRoot()).mkdirs();
    }
}
