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

import it.inspired.wf.Workflow;
import it.inspired.wf.WorkflowContext;
import it.inspired.wf.Processor;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WorkflowManager {
	private ApplicationContext springContext;
	private WorkflowContext context = new WorkflowContext();
	
	public static Logger logger = Logger.getLogger( WorkflowManager.class );
	
	public static WorkflowManager load( String descriptor ) {
		logger.debug( "Loading descriptor " + descriptor );
		
		WorkflowManager wf = new WorkflowManager();
		wf.springContext = new ClassPathXmlApplicationContext(descriptor);
		return wf;
	}
	
	public void addContextParameter( String key, Object value ) {
		context.put( key, value );
		logger.debug( "Added parameter " + key + "(" + value + ") to context" );
	}
	
	public Object getContextParameter( String key ) {
		return context.get( key );
	}
	
	public void execute( String name ) {
		Object bean = springContext.getBean( name );
		
		if ( Processor.class.isAssignableFrom( bean.getClass() ) ) {
			logger.debug( "Executing process: " + name );
			Processor processor = (Processor) bean;
			processor.execute(context);
			
		} else if ( Workflow.class.isAssignableFrom( bean.getClass() ) ) {
			logger.debug( "Executing workflow: " + name );
			Workflow workflow = (Workflow) bean;
			workflow.execute(context);
		}
	}
}
