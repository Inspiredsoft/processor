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

package it.inspired.wf;

import java.util.List;

/**
 * This interface represents a processor. 
 * A processor is responsible for the execution of a set of activities {@link Activity}.
 * The order in which the activities are executed depends on the implementation.
 * 
 * @author Massimo Romano
 *
 */
public interface Processor {
	/**
	 * Sets the activities managed by the processor.
	 * 
	 * @param activities A set of activities {@link Activity}
	 */
	public void setActivities(List<? extends Activity> activities);
	
	/**
	 * Gets the default exception handler.
	 * 
	 * @return The default exception handler
	 */
	public ExceptionHandler getDefaultExceptionHandler();
	
	/**
	 * Sets the default exception handler that is used to manage an error raised during the execution of an activity.
	 * 
	 * @param defaultExceptionHandler The default error handler {@link ExceptionHandler}
	 */
	public void setDefaultExceptionHandler(ExceptionHandler defaultExceptionHandler);
	
	/**
	 * Process all the activities managed by the processor.
	 * 
	 * @param context The starting workflow context {@link WorkflowContext}
	 * @return The resulting workflow context
	 */
	public WorkflowContext execute( WorkflowContext context );
}
