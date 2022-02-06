package ru.apolonov.lombok;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserRequest {

    private String name;
    private String job;

}

