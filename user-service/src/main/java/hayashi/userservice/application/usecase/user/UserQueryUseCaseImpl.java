package hayashi.userservice.application.usecase.user;

import hayashi.userservice.domain.model.UserEntity;
import hayashi.userservice.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryUseCaseImpl implements UserQueryUseCase {

    private final UserService userService;

    @Override
    public Page<UserEntity> getUsers(String name, String email, Pageable pageable) {
        return userService.searchUsers(name, email, pageable);
    }

    @Override
    public UserEntity getUser(String userId) {
        return userService.getById(userId);
    }
}
