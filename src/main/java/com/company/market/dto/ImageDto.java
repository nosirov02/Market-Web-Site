package com.company.market.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ImageDto {
    private Integer id;
    private String url;
    private String path;
    private long size;
    private String type;
    private String token;
    private LocalDateTime createdDate;
}
