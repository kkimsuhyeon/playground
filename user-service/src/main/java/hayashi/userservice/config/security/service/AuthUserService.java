package hayashi.userservice.config.security.service;

import hayashi.userservice.config.security.AuthUser;
import hayashi.userservice.domain.model.UserEntity;
import hayashi.userservice.domain.repository.UserRepository;
import hayashi.userservice.shared.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserById(String id) {
        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isPresent()) return AuthUser.from(user.get());

        throw new UserNotFoundException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmail(username);

        if (user.isPresent()) return AuthUser.from(user.get());

        throw new UserNotFoundException();
    }
}
