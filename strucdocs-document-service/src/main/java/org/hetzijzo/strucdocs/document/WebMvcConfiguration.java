package org.hetzijzo.strucdocs.document;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.time.Instant;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new InstantStringConverter());
    }

    class InstantStringConverter implements Converter<String, Instant> {
        @Override
        public Instant convert(String value) {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            return Instant.ofEpochMilli(Long.valueOf(value));
        }
    }
}
