package hayashi.userservice.domain.model;

import hayashi.userservice.shared.annotation.TsId;
import hayashi.userservice.shared.converter.EncryptConverter;
import hayashi.userservice.shared.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
@SuperBuilder
public class UserEntity extends BaseEntity {

    @Id
    @TsId(prefix = "USER_")
    @Column(name = "user_id", nullable = false, updatable = false, length = 30)
    @Comment("아이디")
    private String id;

    @Column(name = "name", nullable = false, length = 50)
    @Comment("이름")
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    @Comment("이메일")
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    @Comment("패스워드")
    @Convert(converter = EncryptConverter.class)
    private String password;

    @Column(name = "last_login_at")
    @Comment("마지막 로그인 일시")
    private LocalDateTime lastLoginAt;

    public void updateLastLoginAt() {
        this.lastLoginAt = LocalDateTime.now();
    }
}
