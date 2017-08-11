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
import it.inspired.wf.impl.DelegateActivity;

import java.util.ArrayList;

/**
 * Implements the builder pattern to build a delegate activity
 * 
 * @author Massimo Romano
 *
 */
public class ActivityBuilder {
	private DelegateActivity activity = new DelegateActivity();
	
	public static ActivityBuilder newDelegateActivity( Object delegate ) {
		ActivityBuilder builder = new ActivityBuilder();
		builder.activity.setDelegate(delegate);
		return builder;
	}
	
	public ActivityBuilder exceptionHandler( ExceptionHandler exceptionHandler ) {
		this.activity.setExceptionHandler(exceptionHandler);
		return this;
	}
	
	public ActivityBuilder method( String method ) {
		this.activity.setMethod(method);
		return this;
	}
	
	public ActivityBuilder resultParameter( String resultParameter ) {
		this.activity.setResultParameter(resultParameter);
		return this;
	}
	
	public ActivityBuilder parameter( String parameter ) {
		if ( this.activity.getParameters() == null ) {
			this.activity.setParameters( new ArrayList<String>() );
		}
		this.activity.getParameters().add( parameter );
		return this;
	}
	
	public DelegateActivity build() {
		return activity;
	}
}
