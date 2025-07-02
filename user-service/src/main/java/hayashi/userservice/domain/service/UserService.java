package hayashi.userservice.domain.service;

import hayashi.userservice.domain.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserEntity create(UserEntity entity);

    UserEntity getById(String id);

    UserEntity getByEmail(String email);

    Page<UserEntity> searchUsers(String name, String email, Pageable pageable);

}
