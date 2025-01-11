package com.hongphat.common_service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * LoggingAspect
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 12 :24 SA 11/01/2025
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {
	/**
	 * Log execution time and details of methods annotated with @LogExecutionTime
	 *
	 * @param joinPoint the join point
	 * @return the object
	 * @throws Throwable the throwable
	 */
	@Around("@annotation(com.hongphat.common_service.annotation.LogExecutionTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		String className = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();

		log.info("Started execution of {}.{}", className, methodName);

		try {
			// Execute the method
			Object result = joinPoint.proceed();

			long executionTime = System.currentTimeMillis() - startTime;
			log.info("{}.{} executed in {} ms", className, methodName, executionTime);

			return result;
		} catch (Exception e) {
			log.error("Exception in {}.{}: {}", className, methodName, e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Log all controller method executions
	 *
	 * @param joinPoint the join point
	 * @return the object
	 * @throws Throwable the throwable
	 */
	@Around("within(@org.springframework.web.bind.annotation.RestController *)")
	public Object logControllerExecution(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		String className = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();

		log.info("Handling request for {}.{}", className, methodName);

		try {
			Object result = joinPoint.proceed();

			long executionTime = System.currentTimeMillis() - startTime;
			log.info("Request for {}.{} completed in {} ms", className, methodName, executionTime);

			return result;
		} catch (Exception e) {
			log.error("Error handling request for {}.{}: {}", className, methodName, e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Log all service method executions
	 *
	 * @param joinPoint the join point
	 * @return the object
	 * @throws Throwable the throwable
	 */
	@Around("within(@org.springframework.stereotype.Service *)")
	public Object logServiceExecution(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		String className = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();

		log.debug("Executing service method {}.{}", className, methodName);

		try {
			Object result = joinPoint.proceed();

			long executionTime = System.currentTimeMillis() - startTime;
			log.debug("Service method {}.{} completed in {} ms", className, methodName, executionTime);

			return result;
		} catch (Exception e) {
			log.error("Error in service method {}.{}: {}", className, methodName, e.getMessage(), e);
			throw e;
		}
	}
}
