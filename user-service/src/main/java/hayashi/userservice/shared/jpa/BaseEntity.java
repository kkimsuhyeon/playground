package hayashi.userservice.shared.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@MappedSuperclass
@SuperBuilder
public class BaseEntity {

    @CreatedBy
    @Comment("등록자")
    @Column(name = "created_by", nullable = false, updatable = false, length = 50)
    private String createdBy;

    @CreatedDate
    @Comment("등록일시")
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Comment("변경자")
    @Column(name = "updated_by", nullable = false, length = 50)
    private String updatedBy;

    @LastModifiedDate
    @Comment("변경일시")
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
