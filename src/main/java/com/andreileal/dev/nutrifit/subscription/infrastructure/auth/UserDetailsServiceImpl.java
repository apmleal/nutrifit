package com.andreileal.dev.nutrifit.subscription.infrastructure.auth;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.andreileal.dev.nutrifit.subscription.infrastructure.mappers.UserMapper;
import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories.JpaUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final JpaUserRepository userRepositoryJpa;

    public UserDetailsServiceImpl(JpaUserRepository userRepositoryJpa) {
        this.userRepositoryJpa = userRepositoryJpa;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userEntity = userRepositoryJpa.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usu\u00e1rio n\u00e3o encontrado: " + username));

        var user = UserMapper.toDomain(userEntity);

        return new User(
                user.getEmail().valor(),
                user.getSenhaHasheada().hash(),
                Collections.emptyList());
    }
}
