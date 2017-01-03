package org.strucdocs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.strucdocs.JsonRootRelProvider;
import org.strucdocs.config.databind.YamlObjectMapper;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebMvc
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    private static final MediaType MEDIA_TYPE_YAML = MediaType.valueOf("text/yaml");
    private static final MediaType MEDIA_TYPE_YML = MediaType.valueOf("text/yml");

    @Autowired
    private YamlObjectMapper yamlObjectMapper;

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true)
            .favorParameter(false)
            .ignoreAcceptHeader(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType(MediaType.APPLICATION_XML.getSubtype(), MediaType.APPLICATION_XML)
            .mediaType(MediaType.APPLICATION_JSON.getSubtype(), MediaType.APPLICATION_JSON)
            .mediaType(MediaType.APPLICATION_PDF.getSubtype(), MediaType.APPLICATION_PDF)
            .mediaType(MEDIA_TYPE_YML.getSubtype(), MEDIA_TYPE_YML)
            .mediaType(MEDIA_TYPE_YAML.getSubtype(), MEDIA_TYPE_YAML);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter yamlConverter = new MappingJackson2HttpMessageConverter(yamlObjectMapper);
        yamlConverter.setSupportedMediaTypes(Arrays.asList(MEDIA_TYPE_YAML, MEDIA_TYPE_YML));
        converters.add(yamlConverter);
    }

    @Bean
    public RelProvider relProvider() {
        return new JsonRootRelProvider();
    }
}
