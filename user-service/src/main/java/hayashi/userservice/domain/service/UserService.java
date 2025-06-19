package hayashi.userservice.domain.service;

import hayashi.userservice.domain.command.JoinUserCommand;
import hayashi.userservice.domain.model.UserEntity;
import hayashi.userservice.domain.query.FindUserQuery;
import hayashi.userservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserEntity create(JoinUserCommand command);

    UserEntity getById(String id);

    List<UserEntity> searchUsers(FindUserQuery query);

}
