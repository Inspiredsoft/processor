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
import it.inspired.wf.ErrorHandler;
import it.inspired.wf.Processor;

import java.util.List;

public abstract class BaseProcessor implements Processor {

	private String id;
	private String name;
	protected List<? extends Activity> activities;
	protected ErrorHandler defaultErrorHandler;
	
	//--------------------------------------------------------------------------------
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}	
	
	public void setActivities(List<? extends Activity> activities) {
		this.activities = activities;
	}
	
	@Override
	public ErrorHandler getErrorHandler() {
		return this.defaultErrorHandler;
	}

	public void setDefaultErrorHandler(ErrorHandler defaultErrorHandler) {
		this.defaultErrorHandler = defaultErrorHandler;
	}

}
