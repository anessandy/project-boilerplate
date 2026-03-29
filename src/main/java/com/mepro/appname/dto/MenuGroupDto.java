package com.mepro.appname.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuGroupDto {
    private Long idMenuGroupDto;
    private String name;
    private List<MenuDto> menus = new ArrayList<>();
    private boolean open;
}
