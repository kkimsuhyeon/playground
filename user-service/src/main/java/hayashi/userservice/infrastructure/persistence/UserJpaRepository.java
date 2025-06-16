package hayashi.userservice.infrastructure.persistence;

import hayashi.userservice.domain.model.UserEntity;
import hayashi.userservice.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends UserRepository, JpaRepository<UserEntity, String>  {
}
