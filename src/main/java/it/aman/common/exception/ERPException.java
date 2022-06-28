package it.aman.common.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import it.aman.common.util.StringUtils;
import lombok.Getter;

@Getter
public class ERPException extends Exception {
    private static final long serialVersionUID = -5464526854597284442L;

    private final HttpStatus httpCode;
    private final int internalCode;
    private String errorMessage;
    private final List<String> errors;

    public ERPException(String message) {
        this(null, 500001, message);
    }

    public ERPException(HttpStatus httpCode, int internalCode, String message) {
        this(httpCode, internalCode, message, new ArrayList<>());
    }

    public ERPException(HttpStatus httpCode, int internalCode, String message, List<String> errors) {
        super();
        this.httpCode = httpCode;
        this.internalCode = internalCode;
        this.errorMessage = message;
        this.errors = errors;
    }

    public ERPException setErrorMessage(String message) {
        if(!StringUtils.isBlank(message)) {
            this.errorMessage = message;
        }
        return this;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
    
    public void message(final String message) {
        if(!StringUtils.isBlank(message)) {
            this.errorMessage = message;
        }
    }
}
