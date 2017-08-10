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
import it.inspired.wf.WorkflowContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * An workflow activity that can execute a method defined in a bean.
 * 
 * To define the action to execute inject an instance of the delegate object and define the method name to execute.
 * Eventually the parameter name used to invoke the method can be defined as an ordered list of workflow item name.
 * 
 * <pre>
 * {@code 
 * DelegateActivity activity1 = new DelegateActivity();
 * activity1.setDelegate( new Calculator() );
 * activity1.setMethod( "sum" );
 * activity1.setParameters( Arrays.asList( "addendum1", "addendum2") );
 * activity1.setResultParameter( "sum1" );
 * }
 * </pre>
 *  
 * @author Massimo Romano
 *
 */
public class DelegateActivity implements Activity {
	private ExceptionHandler exceptionHandler;	// Exception handler
	private Object delegate;					// Delegated object
	private String method;						// Method to invoke
	private List<String> parameters;			// Context parameter to use
	private String resultParameter;				// Parameter to set result
	
	private Object[] parameterObjects;			// Parameter extracted from context
	private Class<?>[] parameterTypes;			// Type of the parameter extracted from context
	
	//------------------------------------------------------------------
	
	/**
	 * Gets the delegate object 
	 * @return the delegate object
	 */
	public Object getDelegate() {
		return delegate;
	}
	/**
	 * Sets the delegate object
	 * @param delegate the delegate object
	 */
	public void setDelegate(Object delegate) {
		this.delegate = delegate;
	}

	/**
	 * Gets the method name to execute
	 * @return the method name
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * Sets the method name to execute
	 * @param method the method name
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * Gets the parameters name used to invoke the method.
	 * @return the parameters name
	 */
	public List<String> getParameters() {
		return parameters;
	}
	/**
	 * Sets the ordered parameters name used to invoke the method.
	 * These names are used to get the invocation parameters from the Workflow context.
	 * 
	 * @param parameters the ordered parameters name
	 */
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * Sets the error handler
	 * @param errorHandler the error handler
	 */
	public void setExceptionHandler( ExceptionHandler exceptionHandler ) {
		this.exceptionHandler = exceptionHandler;
	}
	
	/*
	 * (non-Javadoc)
	 * @see it.inspired.wf.Activity#getExceptionHandler()
	 */
	public ExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}
	
	/**
	 * Gets the name to use to inject the resulting invocation value into the workflow context
	 * @return the name of the resulting workflow name
	 */
	public String getResultParameter() {
		return resultParameter;
	}
	/**
	 * Sets the name to use to inject the resulting invocation value into the workflow context
	 * @param resultParameter the name of the resulting workflow name
	 */
	public void setResultParameter(String resultParameter) {
		this.resultParameter = resultParameter;
	}
	
	//------------------------------------------------------------------
	
	/*
	 * (non-Javadoc)
	 * @see it.inspired.wf.Activity#execute(it.inspired.wf.WorkflowContext)
	 * 
	 */
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
	
	/**
	 * Extract the parameter value from the workflow context 
	 * @param context
	 */
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
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.resultParameter + "=" + this.method + "(" + this.parameters + ")";
	}
}
