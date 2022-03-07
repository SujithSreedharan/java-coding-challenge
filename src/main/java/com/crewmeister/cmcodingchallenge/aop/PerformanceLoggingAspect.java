package com.crewmeister.cmcodingchallenge.aop;

import com.google.common.flogger.FluentLogger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Profile("default")
public class PerformanceLoggingAspect
{
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  //AOP expression for which methods shall be intercepted
  @Around("execution(* com.crewmeister.cmcodingchallenge..*(..)))")
  public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
  {
    MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

    //Get intercepted method details
    String className = methodSignature.getDeclaringType().getSimpleName();
    String methodName = methodSignature.getName();

    final StopWatch stopWatch = new StopWatch();

    //Measure method execution time
    stopWatch.start();
    Object result = proceedingJoinPoint.proceed();
    stopWatch.stop();

    //Log method execution time
    logger.atInfo().log("Execution time of " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + " ms");

    return result;
  }
}