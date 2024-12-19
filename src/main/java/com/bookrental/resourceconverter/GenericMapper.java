package com.bookrental.resourceconverter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.MappingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenericMapper implements ApplicationContextAware {
    private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();


    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Hibernate5Module());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setVisibility(mapper.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        return mapper;
    }

    @SuppressWarnings("unchecked")
    public <T> T convert(Object source, Class<T> targetClass) {
        try {
            if (source == null) {
                return null;
            }

            // First convert to Map to handle nested properties
            Map<String, Object> propertyMap = OBJECT_MAPPER.convertValue(source, Map.class);

            // Create instance of target class
            T target = targetClass.getDeclaredConstructor().newInstance();

            // If target is an entity, handle relationships
            if (targetClass.isAnnotationPresent(Entity.class)) {
                handleEntityConversion(propertyMap, target);
            }
            // If source is an entity and target is DTO
            else if (source.getClass().isAnnotationPresent(Entity.class)) {
                handleDtoConversion(source, propertyMap, target);
            }
            // For simple properties, use direct mapping
            else {
                target = OBJECT_MAPPER.convertValue(propertyMap, targetClass);
            }

            return target;
        } catch (Exception e) {
            throw new MappingException("Error converting " + source.getClass().getSimpleName()
                    + " to " + targetClass.getSimpleName(), e);
        }
    }

    private <T> void handleEntityConversion(Map<String, Object> propertyMap, T target) {
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                handleField(field, propertyMap, target);
            } catch (Exception e) {
                throw new MappingException("Error handling field: " + field.getName(), e);
            } finally {
                field.setAccessible(false);
            }
        }
    }

    private <T> void handleField(Field field, Map<String, Object> propertyMap, T target) throws IllegalAccessException {
        String fieldName = field.getName();
        Object value = propertyMap.get(fieldName);

        if (value == null) {
            return;
        }

        // Handle relationships based on annotations
        if (field.isAnnotationPresent(ManyToOne.class)) {
            handleManyToOne(field, value, target);
        }
        else if (field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(ManyToMany.class)) {
            handleCollectionRelationship(field, value, target);
        }
        // Handle simple fields
        else {
            field.set(target, OBJECT_MAPPER.convertValue(value, field.getType()));
        }
    }

    private <T> void handleManyToOne(Field field, Object value, T target) throws IllegalAccessException {
        if (value instanceof Number) {  // Handle ID reference
            Object entity = findEntityById(field.getType(), ((Number) value).longValue());
            field.set(target, entity);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> void handleCollectionRelationship(Field field, Object value, T target) throws IllegalAccessException {
        if (value instanceof Collection) {
            Class<?> genericType = getGenericType(field);
            Collection<Object> collection;

            // Initialize appropriate collection type
            if (Set.class.isAssignableFrom(field.getType())) {
                collection = new HashSet<>();
            } else {
                collection = new ArrayList<>();
            }

            // Handle each element in the collection
            for (Object elem : (Collection<?>) value) {
                if (elem instanceof Number) {  // Handle ID reference
                    Object entity = findEntityById(genericType, ((Number) elem).longValue());
                    collection.add(entity);
                } else {
                    collection.add(OBJECT_MAPPER.convertValue(elem, genericType));
                }
            }

            field.set(target, collection);
        }
    }

    private <T> void handleDtoConversion(Object source, Map<String, Object> propertyMap, T target) {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.isAnnotationPresent(Id.class)) {
                    propertyMap.put(field.getName(), field.get(source));
                }
                else if (field.isAnnotationPresent(ManyToOne.class) ||
                        field.isAnnotationPresent(OneToOne.class)) {
                    Object value = field.get(source);
                    if (value != null) {
                        propertyMap.put(field.getName() + "Id", getEntityId(value));
                    }
                }
                else if (field.isAnnotationPresent(OneToMany.class) ||
                        field.isAnnotationPresent(ManyToMany.class)) {
                    Collection<?> values = (Collection<?>) field.get(source);
                    if (values != null) {
                        propertyMap.put(field.getName(),
                                values.stream().map(this::getEntityId).collect(Collectors.toList()));
                    }
                }
            } catch (Exception e) {
                throw new MappingException("Error converting field: " + field.getName(), e);
            } finally {
                field.setAccessible(false);
            }
        }
        BeanUtils.copyProperties(propertyMap, target);
    }

    private Object findEntityById(Class<?> entityType, Long id) {
        String repositoryBeanName = entityType.getSimpleName() + "Repository";
        repositoryBeanName = repositoryBeanName.substring(0, 1).toLowerCase() +
                repositoryBeanName.substring(1);

        try {
            JpaRepository<?, Long> repository =
                    (JpaRepository<?, Long>) applicationContext.getBean(repositoryBeanName);
            return repository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(
                            entityType.getSimpleName() + " not found with id: " + id));
        } catch (BeansException e) {
            throw new MappingException("Repository not found for entity: " +
                    entityType.getSimpleName());
        }
    }

    private Long getEntityId(Object entity) {
        try {
            Field idField = Arrays.stream(entity.getClass().getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Id.class))
                    .findFirst()
                    .orElseThrow(() -> new MappingException("No ID field found in entity"));

            idField.setAccessible(true);
            return (Long) idField.get(entity);
        } catch (Exception e) {
            throw new MappingException("Error getting entity ID", e);
        }
    }

    private Class<?> getGenericType(Field field) {
        ParameterizedType type = (ParameterizedType) field.getGenericType();
        return (Class<?>) type.getActualTypeArguments()[0];
    }

    public <T> List<T> convertList(Collection<?> sourceList, Class<T> targetClass) {
        if (sourceList == null) {
            return new ArrayList<>();
        }
        return sourceList.stream()
                .map(source -> convert(source, targetClass))
                .collect(Collectors.toList());
    }

    public <T> Set<T> convertSet(Collection<?> sourceSet, Class<T> targetClass) {
        if (sourceSet == null) {
            return new HashSet<>();
        }
        return sourceSet.stream()
                .map(source -> convert(source, targetClass))
                .collect(Collectors.toSet());
    }



}
