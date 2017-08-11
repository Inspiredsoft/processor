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

package it.inspired.wf.builder;

import it.inspired.wf.ExceptionHandler;
import it.inspired.wf.Processor;
import it.inspired.wf.impl.WorkflowDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the builder pattern to build a workdlow definition
 * 
 * @author Massimo Romano
 *
 */
public class WorkflowBuilder {
	private WorkflowDefinition workflowDefinition = new WorkflowDefinition();
	private List<Processor> processors = new ArrayList<Processor>();
	
	public static  WorkflowBuilder newDefinition( String id ) {
		WorkflowBuilder builder = new WorkflowBuilder();
		builder.workflowDefinition.setId(id);
		return builder;
	}
	
	public WorkflowBuilder name( String name ) {
		workflowDefinition.setName(name);
		return this;
	}
	
	public WorkflowBuilder processor( Processor processor ) {
		processors.add( processor );
		return this;
	}
	
	public WorkflowBuilder defaultExceptionHandler(ExceptionHandler defaultExceptionHandler) {
		workflowDefinition.setDefaultExceptionHandler(defaultExceptionHandler);
		return this;
	}
	
	public WorkflowDefinition build() {
		workflowDefinition.setProcessors( processors );
		return workflowDefinition;
	}
	
}
