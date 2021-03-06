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

import org.apache.log4j.Logger;

import it.inspired.wf.WorkflowContext;

/**
 * Implements a workflow manager that execute a workflow 
 * 
 * @author Massimo Romano
 *
 */
public abstract class WorkflowManager {
	
	protected static Logger logger = Logger.getLogger( WorkflowManager.class );
	
	protected WorkflowContext context = new WorkflowContext();
	
	/**
	 * Add a parameter in the workflow context
	 * @param key parameter name
	 * @param value parameter value
	 */
	public void addContextParameter( String key, Object value ) {
		context.put( key, value );
		logger.debug( "Added parameter " + key + "(" + value + ") to context" );
	}
	
	/**
	 * Gets a workflow parameter value
	 * @param key parameter name 
	 * @return parameter value
	 */
	public Object getContextParameter( String key ) {
		return context.get( key );
	}
}
