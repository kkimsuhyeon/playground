package hayashi.apigateway.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserInfo {

    private String id;
    private String email;
    private String name;
    private List<String> authorities;

    public String toJson(ObjectMapper objectMapper) throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }

}
