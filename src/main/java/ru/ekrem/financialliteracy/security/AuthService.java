package ru.ekrem.financialliteracy.security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ekrem.financialliteracy.dto.auth.JwtDto;
import ru.ekrem.financialliteracy.dto.auth.LoginDto;
import ru.ekrem.financialliteracy.entity.User;
import ru.ekrem.financialliteracy.service.UserService;

@Component
public class AuthService {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    public JwtDto login(LoginDto loginDto){
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        User user =userService.getByPhone(loginDto.getPhone());
        System.out.println(user.toString());
        if(user.getPassword().equals(loginDto.getPassword())){
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            userService.setRefreshToken(user.getPhone(),refreshToken);
            return new JwtDto(accessToken,refreshToken);
        }
        return new JwtDto(null,null);
    }

    public JwtDto registration(User user, boolean phoneVerified){
        final String accessToken = jwtProvider.generateRegistrationToken(user,phoneVerified);
        return new JwtDto(accessToken,null);
    }


    public JwtDto getAccessToken(String refreshToken){
        if(!jwtProvider.validateRefreshToken(refreshToken))
            return new JwtDto(null,null);
        final User user = userService.getByPhone(jwtProvider.getRefreshClaims(refreshToken).getSubject());
        final String refreshTokenFromDB = user.getRefreshToken();
        if(refreshTokenFromDB!= null && refreshToken.equals(refreshTokenFromDB)){
            return new JwtDto(null, jwtProvider.generateAccessToken(user));
        }
        return new JwtDto(null,null);
    }

    public JwtDto refresh(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = userService.getByPhone(login).getRefreshToken();
            System.out.println(refreshToken);
            System.out.println(saveRefreshToken);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.getByPhone(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                userService.setRefreshToken(user.getPhone(),newRefreshToken);
                return new JwtDto(accessToken, newRefreshToken);
            }
        }
        return new JwtDto(null,null);
    }

    /*public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }*/
}
