package hayashi.userservice.domain.model;

import hayashi.userservice.global.annotation.TsId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user")
public class UserEntity extends BaseEntity {

    @Id
    @TsId(prefix = "")
    @Column(name = "user_id", nullable = false, updatable = false, length = 15)
    private String id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;
}
