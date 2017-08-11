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

import it.inspired.wf.Processor;
import it.inspired.wf.Workflow;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Implements a workflow manager that gets the workflow definition in an xml
 * 
 * @author Massimo Romano
 *
 */
public class XmlWorkflowManager extends WorkflowManager {
	private ApplicationContext springContext;
	
	public static Logger logger = Logger.getLogger( XmlWorkflowManager.class );
	
	/**
	 * Load all the workflow definitions defined in an xml in the classpath
	 * @param definitionLocation location of the xml 
	 * @return The workflow manager that manage all the defined workflow definition
	 */
	public static XmlWorkflowManager load( String definitionLocation ) {
		logger.debug( "Loading descriptor " + definitionLocation );
		
		XmlWorkflowManager wf = new XmlWorkflowManager();
		wf.springContext = new ClassPathXmlApplicationContext(definitionLocation);
		return wf;
	}
	
	/**
	 * Executes the worklfow definition specified.
	 * The definition can be a simple processor {@link Processor} or a complex workflow {@link Workflow}
	 * 
	 * @param name the workflow definition to execute
	 */
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
