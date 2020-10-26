package com.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> {

    private Object message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static ResponseData success(Object o) {
        return new ResponseData(MESSAGE.SUCCESS, o);
    }

    public static ResponseData fail() {
        return new ResponseData(MESSAGE.FAIL, null);
    }

    public enum MESSAGE {
        SUCCESS,
        FAIL,
        UNAUTHORIZED
    }
}
