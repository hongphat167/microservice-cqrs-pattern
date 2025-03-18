package com.hongphat.common_service.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Base response.
 *
 * @param <T> the type parameter
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String code;
    private String description;
    private T data;

    /**
     * Success base response that returns JSON string.
     *
     * @param <T>         the type parameter
     * @param code        the code
     * @param description the description
     * @param data        the data
     * @return JSON string representation of response
     */
    public static <T> String makeBaseResponseSuccess(String code, String description, T data) {
        BaseResponse<T> response = BaseResponse.<T>builder()
                .code(code)
                .description(description)
                .data(data)
                .build();
        try {
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            return "{\"code\":\"ERROR\",\"description\":\"Error converting response to JSON\"}";
        }
    }

    /**
     * Error base response that returns JSON string.
     *
     * @param <T>         the type parameter
     * @param code        the code
     * @param description the description
     * @return JSON string representation of response
     */
    public static <T> String makeBaseResponseError(String code, String description) {
        BaseResponse<T> response = BaseResponse.<T>builder()
                .code(code)
                .description(description)
                .build();
        try {
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            return "{\"code\":\"ERROR\",\"description\":\"Error converting response to JSON\"}";
        }
    }
}