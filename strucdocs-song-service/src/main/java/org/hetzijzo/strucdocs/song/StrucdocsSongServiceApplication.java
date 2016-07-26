package org.hetzijzo.strucdocs.song;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableJpaRepositories
public class StrucdocsSongServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrucdocsSongServiceApplication.class, args);
    }
}
