package com.mepro.appname.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {
    private Long idMenu;
    private String name;
    private String url;
    private String namespace;
    private boolean active;
}
