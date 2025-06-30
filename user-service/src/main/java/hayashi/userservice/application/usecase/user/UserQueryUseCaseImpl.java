package hayashi.userservice.application.usecase.user;

import hayashi.userservice.application.query.FindUserQuery;
import hayashi.userservice.domain.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryUseCaseImpl implements UserQueryUseCase {
    @Override
    public List<UserEntity> getUsers(FindUserQuery query) {
        return List.of();
    }

    @Override
    public UserEntity getUser(String userId) {
        return null;
    }
}
