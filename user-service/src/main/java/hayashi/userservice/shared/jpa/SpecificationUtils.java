package hayashi.userservice.shared.jpa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SpecificationUtils {

    public static <T> Specification<T> likeIgnoreCase(String fieldName, String value) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(value)) return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)), "%" + value.toLowerCase() + "%");
        };
    }

    public static <T> Specification<T> equalTo(String fieldName, Object value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) return null;
            return criteriaBuilder.equal(root.get(fieldName), value);
        };
    }

    public static <T> Specification<T> in(String fieldName, Collection<?> values) {
        return (root, query, criteriaBuilder) -> {
            if (values == null || values.isEmpty()) return null;
            return root.get(fieldName).in(values);
        };
    }
}
