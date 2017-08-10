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
import it.inspired.wf.Processor;

import java.util.List;

/**
 * Anstract class implementing the common features of a processor
 * 
 * @author Massimo Romano
 *
 */
public abstract class BaseProcessor implements Processor {

	private String id;
	private String name;
	protected List<? extends Activity> activities;
	protected ExceptionHandler defaultExceptionHandler;
	
	//--------------------------------------------------------------------------------
	
	/**
	 * Gets the unique processor identifier 
	 * @return processor identifier
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the unique processor identifier
	 * @param id processor identifier
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the processor name
	 * @return processor name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the processor name
	 * @param name processor name
	 */
	public void setName(String name) {
		this.name = name;
	}	
	
	/*
	 * (non-Javadoc)
	 * @see it.inspired.wf.Processor#setActivities(java.util.List)
	 */
	public void setActivities(List<? extends Activity> activities) {
		this.activities = activities;
	}
	
	/*
	 * (non-Javadoc)
	 * @see it.inspired.wf.Processor#getDefaultExceptionHandler()
	 */
	@Override
	public ExceptionHandler getDefaultExceptionHandler() {
		return this.defaultExceptionHandler;
	}

	/*
	 * (non-Javadoc)
	 * @see it.inspired.wf.Processor#setDefaultExceptionHandler(it.inspired.wf.ExceptionHandler)
	 */
	public void setDefaultExceptionHandler(ExceptionHandler defaultExceptionHandler) {
		this.defaultExceptionHandler = defaultExceptionHandler;
	}

}
