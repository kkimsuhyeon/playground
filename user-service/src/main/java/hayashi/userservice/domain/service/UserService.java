package hayashi.userservice.domain.service;

import hayashi.userservice.application.command.JoinUserCommand;
import hayashi.userservice.domain.model.UserEntity;
import hayashi.userservice.application.query.FindUserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserEntity create(UserEntity entity);

    UserEntity getById(String id);

    Page<UserEntity> searchUsers(String name, String email, Pageable pageable);

}
