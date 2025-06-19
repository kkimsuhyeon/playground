package hayashi.userservice.adapter.in.web.request;

import hayashi.userservice.domain.query.FindUserQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserFind {

    @Schema(description = "이름")
    private String name;

    @Schema(description = "페이지")
    private Integer page;

    @Schema(description = "사이즈")
    private Integer size;

    public FindUserQuery toQuery() {
        return FindUserQuery.of(name, page, size);
    }
}
