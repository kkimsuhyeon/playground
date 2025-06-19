package hayashi.userservice.domain.repository;

import hayashi.userservice.domain.model.UserEntity;

import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findById(String id);

    UserEntity save(UserEntity user);
}
