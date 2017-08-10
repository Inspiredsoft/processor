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
import it.inspired.wf.Processor;
import it.inspired.wf.Workflow;
import it.inspired.wf.WorkflowContext;

import java.util.List;

/**
 * Implements a workflow according to the interface {@link Workflow}.
 * 
 * @author Massimo Romano
 *
 */
public class WorkflowDefinition implements Workflow {

	private String id;
	private String name;
	private List<? extends Processor> processors;
	private ExceptionHandler defaultExceptionHandler;
	
	//--------------------------------------------------------------------------------
	
	/**
	 * Gets the unique workflow identifier 
	 * @return workflow identifier
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the unique workflow identifier
	 * @param id workflow identifier
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the workflow name
	 * @return workflow name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the workflow name
	 * @param name workflow name
	 */
	public void setName(String name) {
		this.name = name;
	}	

	/*
	 * (non-Javadoc)
	 * @see it.inspired.wf.Workflow#setProcessors(java.util.List)
	 */
	@Override
	public void setProcessors(List<? extends Processor> processors) {
		this.processors = processors;
	}

	/*
	 * (non-Javadoc)
	 * @see it.inspired.wf.Workflow#setDefaultErrorHandler(it.inspired.wf.ErrorHandler)
	 */
	@Override
	public void setDefaultExceptionHandler(ExceptionHandler defaultExceptionHandler) {
		this.defaultExceptionHandler = defaultExceptionHandler;
	}

	//--------------------------------------------------------------------------------
	
	/*
	 * (non-Javadoc)
	 * @see it.inspired.wf.Workflow#execute(it.inspired.wf.WorkflowContext)
	 */
	@Override
	public WorkflowContext execute(WorkflowContext context) {
		
		if ( processors == null || processors.isEmpty() ) {
			throw new RuntimeException( "No processor defined" );
		}
		
		for ( Processor processor : processors ) {
			try {
				context = processor.execute( context );
			 } catch (Throwable th) {
				 ExceptionHandler errorHandler = processor.getDefaultExceptionHandler();
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
