package it.aman.common.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class WebConfig {

    @Bean
    @Primary
    ObjectMapper defaultMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultPropertyInclusion(Include.NON_EMPTY);
        mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        timeModule.addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer());
        timeModule.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());
        mapper.registerModule(timeModule);
        return mapper;
    }
    
    public static class OffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {
        @Override
        public void serialize(OffsetDateTime arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
            arg1.writeString(arg0.toString());
        }
    }

    public static class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {
        @Override
        public OffsetDateTime deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException {
            return OffsetDateTime.parse(arg0.getText());
        }
    }

    public static class LocalDateSerializer extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
            arg1.writeString(arg0.toString());
        }
    }

    public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException {
            return LocalDate.parse(arg0.getText());
        }
    }
}
