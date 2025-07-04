package hayashi.userservice.application.service;

import hayashi.userservice.domain.model.UserEntity;
import hayashi.userservice.domain.repository.UserRepository;
import hayashi.userservice.domain.service.UserService;
import hayashi.userservice.shared.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserEntity create(UserEntity entity) {
        return userRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getById(String id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
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
