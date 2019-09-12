package com.estes.framework.scheduling;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * This is the base class for the scheduling jobs.
 * @author thiruar
 *
 */
public abstract class EstesScheduler extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		runJob();
	}
	
	protected abstract void runJob();
	
}
