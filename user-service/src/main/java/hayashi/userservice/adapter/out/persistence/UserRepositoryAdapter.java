package hayashi.userservice.adapter.out.persistence;

import hayashi.userservice.domain.model.UserEntity;
import hayashi.userservice.domain.repository.UserRepository;
import hayashi.userservice.domain.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<UserEntity> findById(String id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public UserEntity save(UserEntity user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Page<UserEntity> searchUsers(String name, String email, Pageable pageable) {
        Specification<UserEntity> spec = Specification
                .where(UserSpecification.hasName(name))
                .or(UserSpecification.hasEmail(email));
        
        return userJpaRepository.findAll(spec, pageable);
    }

}
