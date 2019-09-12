package com.estes.framework.interceptor;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

/**
 * aspect for profiling
 *
 * @author Data Concepts
 *
 */
@Aspect
public class ProfilingInterceptor  {
	private static Logger logger = Logger.getLogger("Profiler");

	/**
	 * flag to control profiling. By default, it is set to true. Set it to false if you like to disable profiling
	 */
	private boolean enableProfiling = true;


	public Object profile(ProceedingJoinPoint call) throws Throwable {
		StringBuffer output = new StringBuffer();
		output.append(call.toShortString());
		StopWatch clock = new StopWatch(output.toString());
		try {
			clock.start(output.toString());
			return call.proceed();
		} finally {
			clock.stop();
			output.append("  ");
			output.append(" took: ");
			output.append(clock.getTotalTimeMillis());
			output.append(" ms.");
			logger.info(output);
		}
	}

	/**
	 * Setter for enableProfiling
	 *
	 * @param value
	 *          value to be set for enableProfiling
	 */
	public void setEnableProfiling(boolean value) {
		enableProfiling = value;
	}

	/**
	 * getter for enableProfiling
	 *
	 * @return value of enableProfiling
	 */
	public boolean getEnableProfiling() {
		return enableProfiling;
	}
}
