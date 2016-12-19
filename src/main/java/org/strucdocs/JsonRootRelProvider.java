package org.strucdocs;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.RequiredArgsConstructor;

import org.atteo.evo.inflector.English;
import org.springframework.hateoas.core.DefaultRelProvider;

import java.util.Arrays;

@RequiredArgsConstructor
public class JsonRootRelProvider extends DefaultRelProvider {

    @Override
    public String getItemResourceRelFor(Class<?> type) {
        JsonRootName rootName = Arrays.stream(type.getAnnotationsByType(JsonRootName.class)).findFirst().orElse(null);
        return rootName != null ? rootName.value() : super.getItemResourceRelFor(type);
    }

    @Override
    public String getCollectionResourceRelFor(Class<?> type) {
        JsonRootName rootName = Arrays.stream(type.getAnnotationsByType(JsonRootName.class)).findFirst().orElse(null);
        return rootName != null ? English.plural(rootName.value())
            : English.plural(super.getCollectionResourceRelFor(type));
    }

    @Override
    public boolean supports(Class<?> delimiter) {
        return super.supports(delimiter);
    }
}
