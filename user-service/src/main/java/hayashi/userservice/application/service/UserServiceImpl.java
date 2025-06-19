package hayashi.userservice.application.service;

import hayashi.userservice.domain.command.JoinUserCommand;
import hayashi.userservice.domain.model.UserEntity;
import hayashi.userservice.domain.query.FindUserQuery;
import hayashi.userservice.domain.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public UserEntity create(JoinUserCommand command) {
        return null;
    }

    @Override
    public UserEntity getById(String id) {
        return null;
    }

    @Override
    public List<UserEntity> searchUsers(FindUserQuery query) {
        return List.of();
    }
}
