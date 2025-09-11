package hayashi.userservice.domain.repository;

import hayashi.userservice.domain.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findById(String id);

    Optional<UserEntity> findByEmail(String email);

    UserEntity save(UserEntity user);

    Page<UserEntity> searchUsers(String name, String email, Pageable pageable);

}
