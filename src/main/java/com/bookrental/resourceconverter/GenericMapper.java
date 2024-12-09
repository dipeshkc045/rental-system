package com.bookrental.resourceconverter;

import com.bookrental.exception.ConversionException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class GenericMapper {
    private final ObjectMapper objectMapper;

    public GenericMapper() {
        this.objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public <T> T convert(Object source, Class<T> targetClass) {
        try {
            if (source == null) {
                return null;
            }

            if (targetClass.isInstance(source)) {
                return targetClass.cast(source);
            }

            return objectMapper.convertValue(source, targetClass);
        } catch (IllegalArgumentException e) {
            throw new ConversionException("Error converting object", e);
        }
    }

    public <T> List<T> convert(List<?> sourceList, Class<T> targetClass) {
        try {
            if (sourceList == null) {
                return Collections.emptyList();
            }

            return sourceList.stream()
                    .map(item -> convert(item, targetClass))
                    .toList();
        } catch (Exception e) {
            throw new ConversionException("Error converting list", e);
        }
    }

    public <T> T convert(Map<String, Object> sourceMap, Class<T> targetClass) {
        try {
            if (sourceMap == null) {
                return null;
            }

            return objectMapper.convertValue(sourceMap, targetClass);
        } catch (IllegalArgumentException e) {
            throw new ConversionException("Error converting map to object", e);
        }
    }

    public <K, V> Map<K, V> convert(Object source, Class<K> keyClass, Class<V> valueClass) {
        try {
            if (source == null) {
                return Collections.emptyMap();
            }


            return objectMapper.convertValue(source, objectMapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass));
        } catch (IllegalArgumentException e) {
            throw new ConversionException("Error converting object to map", e);
        }
    }


    public <K, V> Map<K, V> convert(Map<?, ?> sourceMap, Class<K> keyClass, Class<V> valueClass) {
        try {
            if (sourceMap == null) {
                return Collections.emptyMap();
            }

            return objectMapper.convertValue(sourceMap, objectMapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass));
        } catch (IllegalArgumentException e) {
            throw new ConversionException("Error converting map to target map type", e);
        }
    }
}
