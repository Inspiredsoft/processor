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

import java.util.ArrayList;
import java.util.List;

import it.inspired.wf.Activity;
import it.inspired.wf.ExceptionHandler;
import it.inspired.wf.Processor;
import it.inspired.wf.impl.AbstractProcessor;
import it.inspired.wf.impl.ConcurrentProcessor;
import it.inspired.wf.impl.SequenceProcessor;

/**
 * Implements the builder pattern to build a processor
 * 
 * @author Massimo Romano
 *
 */
public class ProcessorBuilder {
	private Processor processor = null;
	private List<Activity> activities = new ArrayList<Activity>();
	
	public static ProcessorBuilder newSequence() {
		ProcessorBuilder builder = new ProcessorBuilder();
		builder.processor = new SequenceProcessor();
		return builder;
	}
	
	public static ProcessorBuilder newConcurrent() {
		ProcessorBuilder builder = new ProcessorBuilder();
		builder.processor = new ConcurrentProcessor();
		return builder;
	}
	
	public ProcessorBuilder id( String id ) {
		((AbstractProcessor)this.processor).setId(id);
		return this;
	}
	
	public ProcessorBuilder name( String name ) {
		((AbstractProcessor)this.processor).setName(name);
		return this;
	}
	
	public ProcessorBuilder defaultExceptionHandler(ExceptionHandler defaultExceptionHandler) {
		processor.setDefaultExceptionHandler(defaultExceptionHandler);
		return this;
	}
	
	public ProcessorBuilder activity( Activity activity ) {
		activities.add( activity );
		return this;
	}
	
	public Processor build() {
		processor.setActivities( activities );
		return processor;
	}
}
