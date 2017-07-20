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
import it.inspired.wf.WorkflowContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class DelegateActivity implements Activity {
	private ErrorHandler errorHandler;		// Error handler
	private Object delegate;				// Delegated object
	private String method;					// Method to invoke
	private List<String> parameters;		// Context parameter to use
	private String resultParameter;			// Parameter to set result
	
	private Object[] parameterObjects;		// Parameter extracted from context
	private Class<?>[] parameterTypes;		// Type of the parameter extracted from context
	
	//------------------------------------------------------------------
	
	public Object getDelegate() {
		return delegate;
	}
	public void setDelegate(Object delegate) {
		this.delegate = delegate;
	}

	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}

	public List<String> getParameters() {
		return parameters;
	}
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}
	
	public void setErrorHandler( ErrorHandler errorHandler ) {
		this.errorHandler = errorHandler;
	}
	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}
	
	public String getResultParameter() {
		return resultParameter;
	}
	public void setResultParameter(String resultParameter) {
		this.resultParameter = resultParameter;
	}
	
	//------------------------------------------------------------------
	
	public WorkflowContext execute( WorkflowContext context ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		if ( this.method == null || this.method.isEmpty() ) {
			throw new RuntimeException("No method defined");
		}
		
		buildParameterList( context );
		
		Method method = delegate.getClass().getMethod( this.method, this.parameterTypes );
		Object result = method.invoke( this.delegate, this.parameterObjects );
		
		if ( this.resultParameter != null && !this.resultParameter.isEmpty() ) {
			context.put( this.resultParameter, result );
		}
		
		return context;
	}
	
	//------------------------------------------------------------------
	
	private void buildParameterList( WorkflowContext context ) {
		this.parameterObjects = new Object[this.parameters.size()];
		this.parameterTypes = new Class<?>[this.parameters.size()];
		
		Object obj = null;
		int i = 0;
		for ( String param : this.parameters ) {
			obj = context.get( param );
			if ( obj == null ) {
				throw new IllegalArgumentException( "Parameter '" + param + "' not bound in context");
			}
			this.parameterObjects[i] = obj;
			this.parameterTypes[i] = obj.getClass();
			i++;
		}
	}
	
	public String toString() {
		return this.resultParameter + "=" + this.method + "(" + this.parameters + ")";
	}
}
