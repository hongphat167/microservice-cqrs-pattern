package com.hongphat.common_service.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The enum Response code.
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum ResponseCode {
    /**
     * Success response code.
     */
    SUCCESS("00", "Success"),
    /**
     * Error response code.
     */
    ERROR("01", "Error"),
    /**
     * The Invalid request.
     */
    INVALID_REQUEST("02", "Invalid request"),
    /**
     * The Not found.
     */
    NOT_FOUND("03", "Not found"),
    /**
     * Unauthorized response code.
     */
    UNAUTHORIZED("04", "Unauthorized"),
    /**
     * Forbidden response code.
     */
    FORBIDDEN("05", "Forbidden"),
    /**
     * The Internal server error.
     */
    INTERNAL_SERVER_ERROR("06", "Internal server error"),
    /**
     * The Bad request.
     */
    BAD_REQUEST("07", "Bad request"),
    /**
     * Duplicate response code.
     */
    DUPLICATE("08", "Duplicate"),
    /**
     * Expired response code.
     */
    EXPIRED("09", "Expired"),
    /**
     * Invalid response code.
     */
    INVALID("10", "Invalid"),
    /**
     * The Not acceptable.
     */
    NOT_ACCEPTABLE("11", "Not acceptable"),
    /**
     * The Not implemented.
     */
    NOT_IMPLEMENTED("12", "Not implemented"),
    /**
     * The Not modified.
     */
    NOT_MODIFIED("13", "Not modified"),
    /**
     * The Payment required.
     */
    PAYMENT_REQUIRED("14", "Payment required"),
    /**
     * The Service unavailable.
     */
    SERVICE_UNAVAILABLE("15", "Service unavailable"),
    /**
     * The Too many requests.
     */
    TOO_MANY_REQUESTS("16", "Too many requests"),
    /**
     * The Unprocessable entity.
     */
    UNPROCESSABLE_ENTITY("17", "Unprocessable entity"),
    /**
     * The Unsupported media type.
     */
    UNSUPPORTED_MEDIA_TYPE("18", "Unsupported media type"),
    /**
     * The Method not allowed.
     */
    METHOD_NOT_ALLOWED("19", "Method not allowed"),
    /**
     * The Gateway timeout.
     */
    GATEWAY_TIMEOUT("20", "Gateway timeout"),
    /**
     * Conflict response code.
     */
    CONFLICT("21", "Conflict"),
    /**
     * The Length required.
     */
    LENGTH_REQUIRED("22", "Length required"),
    /**
     * The Precondition failed.
     */
    PRECONDITION_FAILED("23", "Precondition failed"),
    /**
     * The Request timeout.
     */
    REQUEST_TIMEOUT("24", "Request timeout"),
    /**
     * The Request entity too large.
     */
    REQUEST_ENTITY_TOO_LARGE("25", "Request entity too large"),
    /**
     * The Request uri too long.
     */
    REQUEST_URI_TOO_LONG("26", "Request URI too long"),
    /**
     * The Requested range not satisfiable.
     */
    REQUESTED_RANGE_NOT_SATISFIABLE("27", "Requested range not satisfiable"),
    /**
     * The Upgrade required.
     */
    UPGRADE_REQUIRED("28", "Upgrade required"),
    /**
     * The Unsupported version.
     */
    UNSUPPORTED_VERSION("29", "Unsupported version"),
    /**
     * The Variant also negotiates.
     */
    VARIANT_ALSO_NEGOTIATES("30", "Variant also negotiates"),
    /**
     * The Insufficient storage.
     */
    INSUFFICIENT_STORAGE("31", "Insufficient storage"),
    /**
     * The Loop detected.
     */
    LOOP_DETECTED("32", "Loop detected"),
    /**
     * The Bandwidth limit exceeded.
     */
    BANDWIDTH_LIMIT_EXCEEDED("33", "Bandwidth limit exceeded"),
    /**
     * The Not extended.
     */
    NOT_EXTENDED("34", "Not extended"),
    /**
     * The Network authentication required.
     */
    NETWORK_AUTHENTICATION_REQUIRED("35", "Network authentication required"),
    /**
     * The Network read timeout.
     */
    NETWORK_READ_TIMEOUT("36", "Network read timeout"),
    /**
     * The Network connect timeout.
     */
    NETWORK_CONNECT_TIMEOUT("37", "Network connect timeout"),
    /**
     * The Network error.
     */
    NETWORK_ERROR("38", "Network error"),
    /**
     * The Network unknown error.
     */
    NETWORK_UNKNOWN_ERROR("39", "Network unknown error"),
    /**
     * The Network timeout.
     */
    NETWORK_TIMEOUT("40", "Network timeout"),
    /**
     * The Network no connection.
     */
    NETWORK_NO_CONNECTION("41", "Network no connection"),
    /**
     * The Network no route to host.
     */
    NETWORK_NO_ROUTE_TO_HOST("42", "Network no route to host");

    private final String code;
    private final String description;

}
