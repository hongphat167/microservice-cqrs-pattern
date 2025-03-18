package com.hongphat.authservice.query.queries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class LoginQuery {
    private String username;
    private String password;
}
