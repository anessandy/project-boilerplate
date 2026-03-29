package com.mepro.appname.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UserInfoDto {

    public UserInfoDto(Long nik, String namaLengkap) {
        this.nik = nik;
        this.namaLengkap = namaLengkap;
    }
    
    private Long nik;
    private String namaLengkap;
    private String userId;
    private Boolean isValid;
    private String password;
    private Long idUserGroup;
    private String userGroup;
}
