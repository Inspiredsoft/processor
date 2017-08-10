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
import it.inspired.wf.impl.WorkflowManager;

import org.junit.Before;
import org.junit.Test;

public class TestXml {
	
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
	public void testSequenceProcessor() {
		System.out.println( "Start testSequenceProcessor" );
		
		WorkflowManager wf = WorkflowManager.load( "workflow.xml" );
		
		wf.addContextParameter( "addendum1", addendum1 ); 
		wf.addContextParameter( "addendum2", addendum2 ); 
		
		wf.execute( "sequence" );
		
		assertEquals( sum2,  (Integer) wf.getContextParameter( "sum2" ) );
	}
	
	@Test
	public void testConcurrentProcessor() {
		System.out.println( "Start testConcurrentProcessor" );
		
		WorkflowManager wf = WorkflowManager.load( "workflow.xml" );
		
		wf.addContextParameter( "addendum1", addendum1 ); 
		wf.addContextParameter( "addendum2", addendum2 ); 
		
		wf.execute( "concurrent" );
		
		assertEquals( sum1,  (Integer) wf.getContextParameter( "sum1" ) );
		assertEquals( mult,  (Integer) wf.getContextParameter( "mult" ) );
	}
	
	@Test
	public void testWorkflow() {
		System.out.println( "Start testWorkflow" );
		
		WorkflowManager wf = WorkflowManager.load( "workflow.xml" );
		
		wf.addContextParameter( "addendum1", addendum1 ); 
		wf.addContextParameter( "addendum2", addendum2 ); 
		
		wf.execute( "workflow" );
		
		assertEquals( sum1,  (Integer) wf.getContextParameter( "sum1" ) );
		assertEquals( mult,  (Integer) wf.getContextParameter( "mult" ) );
	}
	
	@Test
	public void testCustomXml1() {
		System.out.println( "Start testCustomXml1" );
		
		WorkflowManager wf = WorkflowManager.load( "custom_xml_wf.1.xml" );
		
		wf.addContextParameter( "addendum1", addendum1 ); 
		wf.addContextParameter( "addendum2", addendum2 ); 
		
		wf.execute( "workflow" );
		
		assertEquals( sum1,  (Integer) wf.getContextParameter( "sum1" ) );
		assertEquals( sum2,  (Integer) wf.getContextParameter( "sum2" ) );
		assertEquals( mult,  (Integer) wf.getContextParameter( "mult" ) );
	}
	
	@Test
	public void testCustomXml2() {
		System.out.println( "Start testCustomXml2" );
		
		WorkflowManager wf = WorkflowManager.load( "custom_xml_wf.2.xml" );
		
		wf.addContextParameter( "addendum1", addendum1 ); 
		wf.addContextParameter( "addendum2", addendum2 ); 
		
		wf.execute( "workflow" );
		
		assertEquals( sum1,  (Integer) wf.getContextParameter( "sum1" ) );
		assertEquals( sum2,  (Integer) wf.getContextParameter( "sum2" ) );
		assertEquals( mult,  (Integer) wf.getContextParameter( "mult" ) );
	}
}
