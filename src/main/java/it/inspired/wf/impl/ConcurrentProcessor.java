/*******************************************************************************
* Inspired Processor is a framework to implement a processor manager.
* Copyright (C) 2017 Inspired Soft
* 
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.    
*******************************************************************************/

package it.inspired.wf.impl;

import it.inspired.wf.Activity;
import it.inspired.wf.ExceptionHandler;
import it.inspired.wf.WorkflowContext;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Executes actovities in parrallel
 * 
 * @author Massimo Romano
 *
 */
public class ConcurrentProcessor extends AbstractProcessor {
	
	private static final CompletionService<Boolean> compService = new ExecutorCompletionService<Boolean>( Executors.newFixedThreadPool( 10 ) );

	/*
	 * (non-Javadoc)
	 * @see it.inspired.wf.Processor#execute(it.inspired.wf.WorkflowContext)
	 */
	public WorkflowContext execute(WorkflowContext context) {
		Set<Future<Boolean>> futures = new HashSet<Future<Boolean>>();
		for ( Activity activity : activities ) {
			futures.add( compService.submit( new ActivityExecutor(context, activity) ) );
		}
		
		Boolean stop = null;
		Future<Boolean> completedFuture = null;
		try {
			while (futures.size() > 0) {
				// block until a callable completes
			    completedFuture = compService.take();
			    futures.remove(completedFuture);
			    
			    stop = completedFuture.get();
			    
			    if ( stop ) {
			    	for (Future<Boolean> future: futures) {
			    		future.cancel(true);
			        }
			    }
			}
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException( e );
		} 
		return context;
	}

	/**
	 * Utility class to execute parallel activities
	 * @author Massimo Romano
	 *
	 */
	private class ActivityExecutor implements Callable<Boolean> {

		private WorkflowContext context;
		private Activity activity;

		public ActivityExecutor( WorkflowContext context, Activity activity ) {
			this.context = context;
			this.activity = activity;
		}

		public Boolean call() throws Exception {
			Boolean result = false;
			try {
				context = activity.execute( context );
			 } catch (Throwable th) {
				 ExceptionHandler errorHandler = activity.getExceptionHandler();
				 if ( errorHandler != null ) {
					 result = errorHandler.handleException(context, th);
				 } else if ( defaultExceptionHandler != null ) {
					 result = defaultExceptionHandler.handleException(context, th);
				 } else {
					 throw new RuntimeException( th );
				 }
			 }
			return result;
		}	
	}
}
