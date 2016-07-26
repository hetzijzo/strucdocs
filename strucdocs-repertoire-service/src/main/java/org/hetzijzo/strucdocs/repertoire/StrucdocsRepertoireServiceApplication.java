package org.hetzijzo.strucdocs.repertoire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.hypermedia.DiscoveredResource;
import org.springframework.cloud.client.hypermedia.DynamicServiceInstanceProvider;
import org.springframework.cloud.client.hypermedia.ServiceInstanceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories
public class StrucdocsRepertoireServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrucdocsRepertoireServiceApplication.class, args);
    }

    @Bean
    public DiscoveredResource storesByLocationResource(ServiceInstanceProvider provider) {
        return new DiscoveredResource(provider, traverson -> traverson.follow("songs"));
    }

    @Bean
    public DynamicServiceInstanceProvider dynamicServiceProvider(DiscoveryClient client) {
        return new DynamicServiceInstanceProvider(client, "song-service");
    }
}
