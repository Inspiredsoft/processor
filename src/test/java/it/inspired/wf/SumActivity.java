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

import it.inspired.wf.Activity;
import it.inspired.wf.ExceptionHandler;
import it.inspired.wf.WorkflowContext;
import it.inspired.wf.impl.ConsoleExceptionHandler;

/**
 * An activity that gets two parameters from the workflow context (named addendum1 and addendum2)
 * executes the sum operation and return the result into a new parameter in the context (named sum).
 * 
 * <pre>
 * {@code
 * sum = addendum1 + addendum2
 * }
 * </pre>
 * 
 * @author Massimo Romano
 *
 */
public class SumActivity implements Activity {

	/*
	 * (non-Javadoc)
	 * @see it.inspired.wf.Activity#execute(it.inspired.wf.WorkflowContext)
	 */
	public WorkflowContext execute(WorkflowContext context) throws Exception {
		Integer addendum1 = (Integer) context.get( "addendum1" );
		Integer addendum2 = (Integer) context.get( "addendum2" );
		
		context.put( "sum", addendum1 + addendum2 );
		
		return context;
	}

	/**
	 * Return a console exception handler
	 */
	public ExceptionHandler getExceptionHandler() {
		return new ConsoleExceptionHandler();
	}
	
}
