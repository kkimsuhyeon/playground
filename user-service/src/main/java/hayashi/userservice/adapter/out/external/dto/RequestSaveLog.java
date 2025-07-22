package hayashi.userservice.adapter.out.external.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hayashi.userservice.shared.dto.BaseResponse;
import hayashi.userservice.shared.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
public class RequestSaveLog {

    private String userId;

    private String requestUri;

    private String requestMethod;

    private Object requestData;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestAt;

    private Object response;

    public static RequestSaveLog create(ServletRequestAttributes attr, JoinPoint joinPoint, Object response) {

        String userId = SecurityUtils.getCurrentUserId();
        String requestURI = Objects.requireNonNull(attr).getRequest().getRequestURI();
        String requestMethod = Objects.requireNonNull(attr).getRequest().getMethod();
        Object requestData = joinPoint.getArgs();

        return new RequestSaveLog(userId, requestURI, requestMethod, requestData, LocalDateTime.now(), response);
    }

    public static RequestSaveLog create(ServletRequestAttributes attr, JoinPoint joinPoint, Throwable response) {

        String userId = SecurityUtils.getCurrentUserId();
        String requestURI = Objects.requireNonNull(attr).getRequest().getRequestURI();
        String requestMethod = Objects.requireNonNull(attr).getRequest().getMethod();
        Object requestData = joinPoint.getArgs();

        return new RequestSaveLog(userId, requestURI, requestMethod, requestData, LocalDateTime.now(), response);
    }

}
