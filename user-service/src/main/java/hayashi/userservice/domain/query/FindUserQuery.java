package hayashi.userservice.domain.query;

import lombok.Data;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class FindUserQuery {

    String name;

    Integer page;
    Integer size;
}
