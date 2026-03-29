package com.mepro.appname.service;

import com.mepro.appname.dao.LoginDao;
import com.mepro.appname.dto.UserInfoDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService implements UserDetailsService {
    @Autowired
    private LoginDao loginRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            List<UserInfoDto> userList = loginRepository.getListUserInfo(username, null);
            if (userList.isEmpty()) {
                throw new UsernameNotFoundException("User tidak ditemukan");
            }
            UserInfoDto userInfo = userList.get(0);
            return org.springframework.security.core.userdetails.User
                .withUsername(userInfo.getUserId())
                .password(userInfo.getUserId())
                .authorities(userInfo.getUserGroup())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
        } 
        catch (Exception e) {
            throw new UsernameNotFoundException("Gagal load user: " + e.getMessage(), e);
        }
    }
}
