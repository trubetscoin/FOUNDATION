package com.foundation.service;

import com.foundation.dto.UserDto;
import com.foundation.exceptionHandling.exception.UserBanConflictException.BanUnbanType;
import com.foundation.exceptionHandling.exception.UserBanConflictException.UserBanConflictException;
import com.foundation.model.Role;
import com.foundation.model.User;
import com.foundation.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> findUsers(String query) {
        if (query == null) {
            return userRepository.findAll()
                    .stream()
                    .map(UserDto::new)
                    .toList();
        }

        return userRepository.findByEmailOrUsername(query)
                .stream()
                .map(UserDto::new)
                .toList();
    }

    @Transactional
    public String banUser(UUID userId) {
        User user = userRepository.findByIdOrThrow(userId);
        if (user.getRoles().contains(Role.ADMIN)) throw new UserBanConflictException(BanUnbanType.BAN, "User is an admin and cannot be banned");
        if (!user.isEnabled()) throw new UserBanConflictException(BanUnbanType.BAN, "User is already banned");

        user.setIsEnabled(false);
        userRepository.save(user);
        return user.getEmail();
    }

    @Transactional
    public String unbanUser(UUID userId) {
        User user = userRepository.findByIdOrThrow(userId);
        if (user.getRoles().contains(Role.ADMIN)) throw new UserBanConflictException(BanUnbanType.UNBAN, "User is an admin and cannot be unbanned");
        if (user.isEnabled()) throw new UserBanConflictException(BanUnbanType.UNBAN, "User is already unbanned");

        user.setIsEnabled(true);
        userRepository.save(user);
        return user.getEmail();
    }
}
