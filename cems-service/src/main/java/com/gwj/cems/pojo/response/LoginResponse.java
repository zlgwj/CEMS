package com.gwj.cems.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    String username;

    String name;

    String token;

    Integer role;
}
