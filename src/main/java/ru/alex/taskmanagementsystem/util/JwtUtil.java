package ru.alex.taskmanagementsystem.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@UtilityClass
public class JwtUtil {
    public static String getEmailByJwtToken(){
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwtToken = request.getHeader("Authorization").substring(7);
        DecodedJWT decodedJWT = JWT.decode(jwtToken);
        return decodedJWT.getClaim("email").asString();
    }
}
