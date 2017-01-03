package org.strucdocs.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.strucdocs.config.databind.YamlObjectMapper;

import java.lang.reflect.Constructor;

@Configuration
public class ObjectMapperConfiguration {

    @Bean
    @Primary
    public ObjectMapper objectMapper() throws ReflectiveOperationException {
        return constructWithDefaults(ObjectMapper.class);
    }

    @Bean
    public YamlObjectMapper yamlMapper() throws ReflectiveOperationException {
        return constructWithDefaults(YamlObjectMapper.class);
    }

    private <T extends ObjectMapper> T constructWithDefaults(Class<T> objectMapperClass)
        throws ReflectiveOperationException {
        Constructor<T> constructor = objectMapperClass.getConstructor();
        T objectMapper = constructor.newInstance();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }
}
