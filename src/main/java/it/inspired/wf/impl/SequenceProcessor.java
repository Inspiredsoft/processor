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

/**
 * Execute activities in sequential order
 * 
 * @author Massimo Romano
 *
 */
public class SequenceProcessor extends AbstractProcessor {

	/*
	 * (non-Javadoc)
	 * @see it.inspired.wf.Processor#execute(it.inspired.wf.WorkflowContext)
	 */
	public WorkflowContext execute(WorkflowContext context) {
		for ( Activity activity : activities ) {
			try {
				context = activity.execute( context );
			 } catch (Throwable th) {
				 ExceptionHandler errorHandler = activity.getExceptionHandler();
				 if ( errorHandler != null ) {
					 if ( errorHandler.handleException(context, th) ) {
						 break;
					 }
				 } else if ( defaultExceptionHandler != null ) {
					 if ( defaultExceptionHandler.handleException(context, th) ) {
						 break;
					 }
				 } else {
					 throw new RuntimeException( th );
				 }
			 }
		}
		return context;
	}
}
