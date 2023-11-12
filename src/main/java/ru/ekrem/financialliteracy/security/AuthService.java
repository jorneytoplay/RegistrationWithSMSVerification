package ru.ekrem.financialliteracy.security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.ekrem.financialliteracy.dto.LoginDto;
import ru.ekrem.financialliteracy.entity.User;
import ru.ekrem.financialliteracy.service.UserService;

@Component
public class AuthService {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    public JwtResponse login(LoginDto loginDto){

        User user =userService.getByPhone(loginDto.getPhone());
        System.out.println(user.toString());
        if(user.getPassword().equals(loginDto.getPassword())){
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            userService.setRefreshToken(user.getPhone(),refreshToken);
            return new JwtResponse(accessToken,refreshToken);
        }
        return new JwtResponse(null,null);
    }

    public JwtResponse registration(User user){
        final String accessToken = jwtProvider.generateRegistrationToken(user);
        return new JwtResponse(accessToken,null);
    }


    public JwtResponse getAccessToken(String refreshToken){
        if(!jwtProvider.validateRefreshToken(refreshToken))
            return new JwtResponse(null,null);
        final User user = userService.getByPhone(jwtProvider.getRefreshClaims(refreshToken).getSubject());
        final String refreshTokenFromDB = user.getRefreshToken();
        if(refreshTokenFromDB!= null && refreshToken.equals(refreshTokenFromDB)){
            return new JwtResponse(null, jwtProvider.generateAccessToken(user));
        }
        return new JwtResponse(null,null);
    }

    public JwtResponse refresh(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = userService.getByPhone(login).getPhone();
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.getByPhone(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                userService.setRefreshToken(user.getPhone(),refreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        return new JwtResponse(null,null);
    }

    /*public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }*/
}
