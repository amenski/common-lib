package it.aman.common.util;

import org.springframework.stereotype.Component;
import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;

@Component
public class DateMapper {

    public java.time.LocalDate toJavaTimeLocalDate(LocalDate value) {
        if(value == null) return null;
        return java.time.LocalDate.parse(value.toString());
    }
    
    public java.time.OffsetDateTime toJavaTimeOffsetDateTime(OffsetDateTime value) {
        if(value == null) return null;
        return java.time.OffsetDateTime.parse(value.toString());
    }
    
    public LocalDate toThreetenLocalDate(java.time.LocalDate value) {
        if(value == null) return null;
        return LocalDate.parse(value.toString());
    }
    
    public OffsetDateTime toThreetenOffsetDateTime(java.time.OffsetDateTime value) {
        if(value == null) return null;
        return OffsetDateTime.parse(value.toString());
    }
}
