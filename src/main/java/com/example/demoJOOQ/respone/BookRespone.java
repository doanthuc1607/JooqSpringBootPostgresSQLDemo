package com.example.demoJOOQ.respone;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookRespone {
    private Integer id;
    private String  title;
    private String  author;
    private Integer quantity;

}

