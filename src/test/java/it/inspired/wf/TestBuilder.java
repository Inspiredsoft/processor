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

package it.inspired.wf;

import static org.junit.Assert.assertEquals;
import it.inspired.wf.builder.ActivityBuilder;
import it.inspired.wf.builder.ProcessorBuilder;
import it.inspired.wf.builder.WorkflowBuilder;
import it.inspired.wf.impl.ConsoleExceptionHandler;
import it.inspired.wf.impl.DelegateActivity;
import it.inspired.wf.impl.WorkflowDefinition;

import org.junit.Before;
import org.junit.Test;

public class TestBuilder {
	
	private WorkflowContext context = new WorkflowContext();
	private Integer addendum1 = 3;
	private Integer addendum2 = 2;
	private Integer sum1  = addendum1 + addendum2;
	private Integer sum2  = sum1 + sum1;
	private Integer mult = addendum1 * addendum2;
	
	@Before
	public void init() {
		context.put( "addendum1", addendum1 );
		context.put( "addendum2", addendum2 );
	}

	@Test
	public void test() {
		
		DelegateActivity activity1 = ActivityBuilder
										.newDelegateActivity( new Calculator() )
										.method("sum")
										.parameter("addendum1")
										.parameter("addendum2")
										.resultParameter("sum1")
										.build();
		
		DelegateActivity activity2 = ActivityBuilder
										.newDelegateActivity( new Calculator() )
										.method("sum")
										.parameter("sum1")
										.parameter("sum1")
										.resultParameter("sum2")
										.build(); 
		
		DelegateActivity activity3 = ActivityBuilder
										.newDelegateActivity( new Calculator() )
										.method("mult")
										.parameter("addendum1")
										.parameter("addendum2")
										.resultParameter("mult")
										.build(); 
		
		WorkflowDefinition workflow = WorkflowBuilder
										.newDefinition( "sum2number" )
										.defaultExceptionHandler( new ConsoleExceptionHandler( false ) )
										.processor( ProcessorBuilder.newSequence()
													.activity( activity1 )
													.activity( activity2 )
													.build() )
										.processor( ProcessorBuilder.newConcurrent()
													.activity( activity1 )
													.activity( activity3 )
													.build() )
										.build();
		
		workflow.execute( context );
		
		assertEquals( sum1, (Integer) context.get( "sum1") );
		assertEquals( sum2, (Integer) context.get( "sum2" ) );
		assertEquals( mult, (Integer) context.get( "mult") );
	}
}
