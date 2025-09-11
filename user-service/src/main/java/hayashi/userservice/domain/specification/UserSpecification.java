package hayashi.userservice.domain.specification;

import hayashi.userservice.domain.model.UserEntity;
import hayashi.userservice.shared.jpa.SpecificationUtils;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<UserEntity> hasName(String name) {
        return SpecificationUtils.likeIgnoreCase("name", name);
    }

    public static Specification<UserEntity> hasEmail(String email) {
        return SpecificationUtils.likeIgnoreCase("email", email);
    }

}
