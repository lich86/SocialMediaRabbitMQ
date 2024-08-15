package com.chervonnaya.publicationsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDTO {

    private Long id;
    private Long userId;
    private String text;
}
