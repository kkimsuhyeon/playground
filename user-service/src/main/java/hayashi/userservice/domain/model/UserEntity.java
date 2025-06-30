package hayashi.userservice.domain.model;

import hayashi.userservice.shared.annotation.TsId;
import hayashi.userservice.shared.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
@Builder
public class UserEntity extends BaseEntity {

    @Id
    @TsId(prefix = "USER_")
    @Column(name = "user_id", nullable = false, updatable = false, length = 30)
    private String id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;
}
