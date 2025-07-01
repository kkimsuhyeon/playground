package hayashi.userservice.adapter.in.web.request;

import hayashi.userservice.application.query.FindUserQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UserFind {

    @Schema(description = "이름")
    private String name;

    @Schema(description = "페이지")
    @Min(1)
    private Integer page;

    @Schema(description = "사이즈")
    @Min(1)
    private Integer size;

    public FindUserQuery toQuery() {
        return FindUserQuery.of(name, page, size);
    }
}
