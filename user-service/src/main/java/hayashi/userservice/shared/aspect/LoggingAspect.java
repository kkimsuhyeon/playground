package hayashi.userservice.shared.aspect;

import hayashi.userservice.adapter.out.external.log.dto.RequestSaveLog;
import hayashi.userservice.shared.event.ErrorLogEvent;
import hayashi.userservice.shared.event.SuccessLogEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final ApplicationEventPublisher eventPublisher;

    @AfterReturning(pointcut = "execution(* hayashi.userservice..*UseCase.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        BaseResponse<?> response = (BaseResponse<?>) ((ResponseEntity<?>) result).getBody();

        RequestSaveLog request = RequestSaveLog.create(attr, joinPoint, result);
        eventPublisher.publishEvent(new SuccessLogEvent(this, request));
    }

    @AfterThrowing(pointcut = "execution(* hayashi.userservice..*Controller.*(..)) || " +
            "execution(* hayashi.userservice..*Service.*(..)) || " +
            "execution(* hayashi.userservice..*UseCase.*(..)) ||" +
            "execution(* hayashi.userservice..*Repository.*(..)))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        RequestSaveLog request = RequestSaveLog.create(attr, joinPoint, ex);
        eventPublisher.publishEvent(new ErrorLogEvent(this, request));
    }
}
