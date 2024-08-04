package ru.gb.timesheet.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RecoverAspect {

    @Pointcut("@annotation(ru.gb.timesheet.aspect.Recover)")
    public void recoverMethodsPointcut(){}

    @Around(value = "recoverMethodsPointcut()")
    public Object aroundTimesheetServiceMethods(ProceedingJoinPoint pjp) throws Throwable {
        try {
            pjp.proceed();
        } catch (Exception e) {
            String method = pjp.getSignature().getName();
            log.info("Recovering TimesheetService#{} after Exception[{}, {}]", method, e.getClass(), e.getMessage());
            try {
                return null;
            } catch (Exception e2) {
                return 0;
            }
        }
        return 0;
    }

}
