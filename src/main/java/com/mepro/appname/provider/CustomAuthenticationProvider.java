package com.mepro.appname.provider;

import com.mepro.appname.dao.LoginDao;
import com.mepro.appname.dto.UserInfoDto;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    
    @Autowired
    private LoginDao loginRepository;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        try {
            List<UserInfoDto> userList = loginRepository.getListUserInfo(username, password);

            if (userList.isEmpty()) {
                throw new BadCredentialsException("User/password tidak valid");
            }

            UserInfoDto userInfo = userList.get(0);
            CustomUserDetails userDetails = new CustomUserDetails(userInfo);
            return new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
            );

        } catch (Exception e) {
            logger.error("Error autentikasi:"+e.getMessage());
            throw new AuthenticationServiceException("Error autentikasi: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
