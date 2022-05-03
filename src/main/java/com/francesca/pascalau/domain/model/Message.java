package com.francesca.pascalau.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class Message {

    private String id;
    private String body;
    private LocalDate issuedAt;
}
