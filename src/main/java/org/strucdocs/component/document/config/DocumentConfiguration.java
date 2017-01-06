package org.strucdocs.component.document.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.strucdocs.config.condition.LinuxCondition;
import org.strucdocs.config.condition.WindowsCondition;

import java.io.File;

@Configuration
public class DocumentConfiguration {

    @Bean
    public File fileStoreRoot(@Value("${fileStore.root:upload-dir}") String root) {
        return new File(root);
    }

    @Bean
    @Conditional(WindowsCondition.class)
    public CommandLineProperties windowsCommandLineProperties() {
        return CommandLineProperties.builder()
            .exifCommand("C:\\Data\\Programs\\exiftool.exe")
            .extractTextCommand("C:\\Data\\Programs\\xpdfbin-win-3.04\\bin64\\pdftotext.exe")
            .build();
    }

    @Bean
    @Conditional(LinuxCondition.class)
    public CommandLineProperties linuxCommandLineProperties() {
        return CommandLineProperties.builder()
            .exifCommand("exiftool")
            .extractTextCommand("pdftotext")
            .build();
    }
}
