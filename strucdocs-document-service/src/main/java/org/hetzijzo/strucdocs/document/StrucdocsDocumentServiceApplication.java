package org.hetzijzo.strucdocs.document;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories
public class StrucdocsDocumentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrucdocsDocumentServiceApplication.class, args);
    }

    @Bean
    @Conditional(WindowsCondition.class)
    public CommandLineConfiguration windowsCommandLineConfiguration() {
        return CommandLineConfiguration.builder()
            .exifCommand("C:\\Data\\Programs\\exiftool.exe")
            .extractTextCommand("C:\\Data\\Programs\\xpdfbin-win-3.04\\bin64\\pdftotext.exe")
            .build();
    }

    @Bean
    @Conditional(LinuxCondition.class)
    public CommandLineConfiguration linuxCommandLineConfiguration() {
        return CommandLineConfiguration.builder()
            .exifCommand("exiftool")
            .extractTextCommand("pdftotext")
            .build();
    }
}
