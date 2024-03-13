package ru.dmitriispiridonov.restfulservice.service;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dmitriispiridonov.restfulservice.dto.entity.UserEntity;
import ru.dmitriispiridonov.restfulservice.repository.UserRepository;
import ru.dmitriispiridonov.restfulservice.security.UserDetailsImpl;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByName(username);
        return user.map(UserDetailsImpl::new).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}
