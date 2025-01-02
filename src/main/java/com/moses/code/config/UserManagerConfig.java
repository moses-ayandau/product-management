package com.moses.code.config;


import com.moses.code.config.UserConfig;
import com.moses.code.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserManagerConfig implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(emailId)
                .map(UserConfig::new)
                .orElseThrow(()-> new UsernameNotFoundException("UserEmail: "+emailId+" does not exist"));
    }
}
