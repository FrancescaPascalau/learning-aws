package com.francesca.pascalau.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Message {

    private String id;
    private String body;
    private LocalDate issuedAt;
}
