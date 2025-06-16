package hayashi.userservice.common.redis;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisDatabaseType {
    DEFAULT(0),
    TOKEN(1),
    ;

    private final int code;

}
