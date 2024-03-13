package ru.dmitriispiridonov.restfulservice.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class HttpError {

    private final Integer status;
    private final String message;
    private final Date timestamp;

    public HttpError(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
