package hayashi.userservice.application.service;

import hayashi.userservice.domain.model.UserEntity;
import hayashi.userservice.domain.repository.UserRepository;
import hayashi.userservice.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity create(UserEntity entity) {
        return userRepository.save(entity);
    }

    @Override
    public UserEntity getById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("empty"));
    }

    @Override
    public Page<UserEntity> searchUsers(String name, String email, Pageable pageable) {

        if (StringUtils.hasText(name) && StringUtils.hasText(email)) {
            return userRepository.findByNameContainingOrEmailContaining(name, email, pageable);
        }

        if (StringUtils.hasText(name)) {
            return userRepository.findByNameContaining(name, pageable);
        }

        if (StringUtils.hasText(email)) {
            return userRepository.findByEmailContaining(email, pageable);
        }

        return userRepository.findAll(pageable);

    }

}
