package ru.gb.timesheet.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import ru.gb.timesheet.model.Timesheet;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* ru.gb.timesheet.service.TimesheetService.*(..))")
    public void timesheetServiceMethodsPointcut() {
    }

    @Before(value = "timesheetServiceMethodsPointcut()")
    public void beforeTimesheetsServiceFindById(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        Object[] signatureArgs = jp.getArgs();
        //Long id = (Long) jp.getArgs()[0];
        if (Arrays.stream(signatureArgs).filter(Objects::nonNull).toList().isEmpty()) {
            log.info("Before -> TimesheetService#{} without arguments", methodName);
        } else {
            StringBuilder args = new StringBuilder("Args:");
            for (Object signatureArg : signatureArgs) {
                String typeName = signatureArg.getClass().getSimpleName();
                args.append(" (").append(typeName).append(" = ")
                        .append(signatureArg).append(")");
            }
            log.info("Before -> TimesheetService#{} " + args, methodName);
        }
    }

//    @Before(value = "timesheetServiceMethodsPointcut()")
//    public void beforeTimesheetServiceFindById(JoinPoint jp) {
//        String methodName = jp.getSignature().getName();
//
//        StringBuilder args = new StringBuilder("Args:");
//        Object[] signatureArgs = jp.getArgs();
//        for (Object signatureArg : signatureArgs) {
//            String typeName = signatureArg.getClass().getSimpleName();
//            args.append(" (").append(typeName).append(" = ")
//                    .append(signatureArg).append(")");
//        }
//        log.info("Before -> TimesheetService#{} " + args, methodName);
//    }

//    @AfterThrowing(value = "timesheetServiceMethodsPointcut()", throwing = "exception")
//    public void AfterTimesheetsServiceFindById(JoinPoint jp, Exception exception){
//        String methodName = jp.getSignature().getName();
//        //Long id = (Long) jp.getArgs()[0];
//        log.info("AfterThrowing -> TimesheetService#{} -> {}", methodName, exception.getClass().getName());
//    }

    //Для логгирования определённого метода

    //@Before(value = "execution(* ru.gb.timesheet.service.TimesheetService.findById(Long))")
//    public void beforeTimesheetsServiceFindById(JoinPoint jp){
//        Long id = (Long) jp.getArgs()[0];
//        log.info("TimesheetService#findById(), id = {}", id);
//    }
}
