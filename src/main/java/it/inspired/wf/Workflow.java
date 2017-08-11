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
 * This interface represents a workflow.
 * 
 * A workflow is made of an ordered list of processor {@link Processor} that are executed in the given order.
 * 
 * @author Massimo Romano
 *
 */
public interface Workflow {
	/**
	 * Sets the processors composing the workflow
	 *  
	 * @param processors An ordered list of {@link Processor}
	 */
	public void setProcessors(List<? extends Processor> processors);
	
	/**
	 * Gets the processors composing the workflow
	 * 
	 * @return the processors 
	 */
	public List<? extends Processor> getProcessors();
	
	/**
	 * Sets the default error handler that is used to manage an error raised during the execution.
	 * 
	 * @param defaultExceptionHandler The default error handler {@link ExceptionHandler}
	 */
	public void setDefaultExceptionHandler(ExceptionHandler defaultExceptionHandler);
	
	/**
	 * Execute all the processor included in the workflow in the specified order.
	 * 
	 * @param context The starting workflow context {@link WorkflowContext}
	 * @return The resulting workflow context
	 */
	public WorkflowContext execute( WorkflowContext context );
}
