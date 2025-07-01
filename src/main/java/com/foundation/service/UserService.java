package com.foundation.service;

import com.foundation.dto.UserLoginDto;
import com.foundation.dto.UserRegisterDto;
import com.foundation.exceptionHandling.exception.CredentialsAlreadyExistsException;
import com.foundation.exceptionHandling.exception.UserBannedException;
import com.foundation.model.Role;
import com.foundation.model.User;
import com.foundation.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserRegisterDto userRegisterDto) {
        if (userRepository.findByEmail(userRegisterDto.getEmail()).isPresent()) {
            throw new CredentialsAlreadyExistsException("email", userRegisterDto.getEmail());
        } else if (userRepository.findByUsername(userRegisterDto.getUsername()).isPresent()) {
            throw new CredentialsAlreadyExistsException("username", userRegisterDto.getUsername());
        }

        User user = new User(
                userRegisterDto.getUsername(),
                userRegisterDto.getEmail(),
                passwordEncoder.encode(userRegisterDto.getPassword()),
                Role.USER
        );

        return userRepository.save(user);
    }

    public User loginUser(UserLoginDto userLoginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userLoginDto.getEmail(),
                    userLoginDto.getPassword()
            ));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return (User) authentication.getPrincipal(); // User Entity implements UserDetails
        }
        catch (DisabledException e) {
            throw new UserBannedException();
        }
        catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid login or password");
        }
    }

}
