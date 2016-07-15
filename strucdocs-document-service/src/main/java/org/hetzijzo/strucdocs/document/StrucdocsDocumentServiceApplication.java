package org.hetzijzo.strucdocs.document;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCouchbaseRepositories
public class StrucdocsDocumentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrucdocsDocumentServiceApplication.class, args);
    }
}
