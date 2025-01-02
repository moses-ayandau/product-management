package com.moses.code.utils;



import com.moses.code.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        User user = new User();
        user.setEmail(jwt.getSubject());


        List<String> roles = jwt.getClaimAsStringList("roles");


        List<SimpleGrantedAuthority> authorities;
        if (roles != null && !roles.isEmpty()) {
            authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } else {

            authorities = List.of(new SimpleGrantedAuthority("USER"));
        }

        return new UsernamePasswordAuthenticationToken(user, jwt, authorities);
    }
}
