package hayashi.userservice.application.usecase.user;

import hayashi.userservice.domain.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryUseCase {

    Page<UserEntity> getUsers(String name, String email, Pageable pageable);

    UserEntity getUser(String userId);
}
