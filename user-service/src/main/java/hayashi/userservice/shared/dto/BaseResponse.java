package hayashi.userservice.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import hayashi.userservice.shared.exception.ErrorCode;
import hayashi.userservice.shared.exception.ValidationError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor(staticName = "of")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    private static final String DEFAULT_SUCCESS_CODE = "SUCCESS";
    private static final String DEFAULT_SUCCESS_MESSAGE = "정상 처리되었습니다.";

    private final String code;
    private final String message;

    private T data;

    private String description;
    private List<ValidationError> fields;

    public static BaseResponse<Void> success() {
        return BaseResponse.of(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE);
    }

    public static BaseResponse<Void> successMessage(String message) {
        return BaseResponse.of(DEFAULT_SUCCESS_CODE, message);
    }

    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = BaseResponse.of(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE);
        response.setData(data);

        return response;
    }

    public static <T> BaseResponse<T> success(String message, T data) {
        BaseResponse<T> response = BaseResponse.of(DEFAULT_SUCCESS_CODE, message);
        response.setData(data);

        return response;
    }

    public static <T> BaseResponse<T> fail(ErrorCode errorCode) {
        return BaseResponse.of(errorCode.getCode(), errorCode.getMessage());
    }

    public static <T> BaseResponse<T> fail(ErrorCode errorCode, String description) {
        BaseResponse<T> response = BaseResponse.of(errorCode.getCode(), errorCode.getMessage());
        response.setDescription(description);

        return response;
    }

    public static <T> BaseResponse<T> fail(ErrorCode errorCode, List<ValidationError> fields) {
        BaseResponse<T> response = BaseResponse.of(errorCode.getCode(), errorCode.getMessage());
        response.setFields(fields);

        return response;
    }

}
