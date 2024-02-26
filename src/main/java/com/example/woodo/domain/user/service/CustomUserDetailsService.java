package com.example.woodo.domain.user.service;

import com.example.woodo.common.exception.user.UserNotFoundException;
import com.example.woodo.domain.user.User;
import com.example.woodo.domain.user.repository.UserRepository;
import com.example.woodo.domain.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return new CustomUserDetails(user);
    }
}
