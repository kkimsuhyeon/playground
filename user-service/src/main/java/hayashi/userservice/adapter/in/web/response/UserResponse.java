package hayashi.userservice.adapter.in.web.response;

import hayashi.userservice.domain.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class UserResponse {

    private String id;
    private String name;
    private String email;

    public static UserResponse from(UserEntity entity) {
        return UserResponse.of(entity.getId(), entity.getName(), entity.getEmail());
    }
}
