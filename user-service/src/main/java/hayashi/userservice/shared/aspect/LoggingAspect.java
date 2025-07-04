package hayashi.userservice.shared.aspect;

import hayashi.userservice.adapter.out.external.client.LogServiceClient;
import hayashi.userservice.adapter.out.external.dto.RequestSaveLog;
import hayashi.userservice.config.security.AuthUser;
import hayashi.userservice.shared.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final LogServiceClient logServiceClient;

    @AfterReturning(pointcut = "execution(* hayashi.userservice..*Controller.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        String userId = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(principle -> {
                    if (principle instanceof AuthUser authUser) return authUser.getId();
                    return "SYSTEM";
                }).orElse("SYSTEM");

        String requestURI = Objects.requireNonNull(attr).getRequest().getRequestURI();
        String requestMethod = Objects.requireNonNull(attr).getRequest().getMethod();
        Object requestData = joinPoint.getArgs();
        BaseResponse<?> response = (BaseResponse<?>) ((ResponseEntity<?>) result).getBody();

        try {
            RequestSaveLog request = new RequestSaveLog(userId, requestURI, requestMethod, requestData, LocalDateTime.now(), response);
            logServiceClient.saveLog(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterThrowing(pointcut = "execution(* hayashi.userservice..*Controller.*(..)) || " +
            "execution(* hayashi.userservice..*Service.*(..)) || " +
            "execution(* hayashi.userservice..*UseCase.*(..)) ||" +
            "execution(* hayashi.userservice..*Repository.*(..)))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        String userId = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(principle -> {
                    if (principle instanceof AuthUser authUser) return authUser.getId();
                    return "SYSTEM";
                }).orElse("SYSTEM");

        String requestURI = Objects.requireNonNull(attr).getRequest().getRequestURI();
        String requestMethod = Objects.requireNonNull(attr).getRequest().getMethod();
        Object requestData = joinPoint.getArgs();

        try {
            RequestSaveLog request = new RequestSaveLog(userId, requestURI, requestMethod, requestData, LocalDateTime.now(), ex);
            logServiceClient.saveLog(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
