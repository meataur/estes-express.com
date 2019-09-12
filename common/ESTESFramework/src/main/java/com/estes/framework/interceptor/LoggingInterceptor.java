package com.estes.framework.interceptor;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


/**
 *
 * @author Farr
 *
 */
@Component
@Aspect
public class LoggingInterceptor  {

	private static Logger logger = Logger.getLogger("Profiler");

	public Object aroundLog(ProceedingJoinPoint joinPoint)
			throws Throwable {
		
		try {
			Logger logger = Logger.getLogger(joinPoint.getTarget().getClass().getName());
			logger.debug("**Start**");
			return  joinPoint.proceed();
		} catch (Throwable t) {
			StringBuffer argStr = new StringBuffer();
			Object[] args = joinPoint.getArgs();
			for (int i = 0; i < args.length; i++) {
				argStr.append(args[i]).append(",");
			}
			logger.error( " Exception >> |args| "+ argStr,  t);
			throw t;
		}finally {
			logger.debug("**End**");
		}
	}

}
