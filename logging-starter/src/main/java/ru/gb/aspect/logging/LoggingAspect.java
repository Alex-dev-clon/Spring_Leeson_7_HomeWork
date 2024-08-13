package ru.gb.aspect.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

    private final LoggingProperties properties;

    @Pointcut("@annotation(ru.gb.aspect.logging.Logging)")
    public void loggingMethodsPointcut() {
    }

    @Pointcut("@within(ru.gb.aspect.logging.Logging)")
    public void loggingTypePointcut() {
    }

    @Around(value = "loggingMethodsPointcut()  || loggingTypePointcut()")
    public Object loggingMethod(ProceedingJoinPoint pjp) throws Throwable {

        Object[] args = pjp.getArgs();
        String methodName = pjp.getSignature().getName();
        if (properties.isPrintArgs()) {
            log.atLevel(properties.getLevel()).log("Before -> TimesheetService#{}. Args = #{}", methodName, args);
            try {
                return pjp.proceed();
            } finally {
                log.atLevel(properties.getLevel()).log("After -> TimesheetService#{}. Args = #{}", methodName, args);
            }
        } else {
            log.atLevel(properties.getLevel()).log("Before -> TimesheetService#{}", methodName);
            try {
                return pjp.proceed();
            } finally {
                log.atLevel(properties.getLevel()).log("After -> TimesheetService#{}", methodName);
            }
        }
    }


//    @Pointcut("execution(* ru.gb.timesheet.service.TimesheetService.*(..))")
//    public void timesheetServiceMethodsPointcut() {
//    }

//    @Before(value = "timesheetServiceMethodsPointcut()")
//    public void beforeTimesheetsServiceFindById(JoinPoint jp) {
//        String methodName = jp.getSignature().getName();
//        Object[] signatureArgs = jp.getArgs();
//        //Long id = (Long) jp.getArgs()[0];
//        if (Arrays.stream(signatureArgs).filter(Objects::nonNull).toList().isEmpty()) {
//            log.info("Before -> TimesheetService#{} without arguments", methodName);
//        } else {
//            StringBuilder args = new StringBuilder("Args:");
//            for (Object signatureArg : signatureArgs) {
//                String typeName = signatureArg.getClass().getSimpleName();
//                args.append(" (").append(typeName).append(" = ")
//                        .append(signatureArg).append(")");
//            }
//            log.info("Before -> TimesheetService#{} " + args, methodName);
//        }
//    }

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
