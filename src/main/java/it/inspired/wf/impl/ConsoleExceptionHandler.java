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

import it.inspired.wf.ExceptionHandler;
import it.inspired.wf.WorkflowContext;

/**
 * implements an exception handler the print the stack trace to the console error
 *  
 * @author Massimo Romano
 *
 */
public class ConsoleExceptionHandler implements ExceptionHandler {
	
	/* Define if the error should stop the execution */
	public boolean stopOnError = false;
	
	public ConsoleExceptionHandler(){}
	
	public ConsoleExceptionHandler( boolean stopOnError ){
		this.stopOnError = stopOnError;
	}
	
	/*
	 * (non-Javadoc)
	 * @see it.inspired.wf.ErrorHandler#handleError(it.inspired.wf.WorkflowContext, java.lang.Throwable)
	 */
	public boolean handleException(WorkflowContext context, Throwable th) {
		th.printStackTrace();
		return true;
	}
}
