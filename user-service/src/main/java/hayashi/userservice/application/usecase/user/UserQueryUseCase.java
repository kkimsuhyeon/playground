package hayashi.userservice.application.usecase.user;

import hayashi.userservice.application.query.FindUserQuery;
import hayashi.userservice.domain.model.UserEntity;

import java.util.List;

public interface UserQueryUseCase {

    List<UserEntity> getUsers(FindUserQuery query);

    UserEntity getUser(String userId);
}
