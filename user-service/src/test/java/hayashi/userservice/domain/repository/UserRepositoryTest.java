package hayashi.userservice.domain.repository;

import hayashi.userservice.config.JpaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import({JpaConfig.class})
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findById() {
        PageRequest pageRequest = PageRequest.of(1, 100, Sort.by("id").descending());
        assertNotNull(userRepository.findByNameContainingOrEmailContaining("test", null, pageRequest));
    }

}