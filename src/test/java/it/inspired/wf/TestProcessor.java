package it.inspired.wf;

import static org.junit.Assert.assertEquals;
import it.inspired.wf.impl.ConcurrentProcessor;
import it.inspired.wf.impl.ConsoleExceptionHandler;
import it.inspired.wf.impl.DelegateActivity;
import it.inspired.wf.impl.SequenceProcessor;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class TestProcessor {

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
		
		SequenceProcessor processor = new SequenceProcessor();
		
		processor.setDefaultExceptionHandler( new ConsoleExceptionHandler() );
		
		processor.setActivities( Arrays.asList( new SumActivity() ) );
		
		processor.execute( context );
		
		assertEquals( sum1,  (Integer) context.get( "sum") );
	}
	
	@Test
	public void testSequenceDelegate() {
		System.out.println( "Start testSequenceDelegate" );
		
		DelegateActivity activity1 = new DelegateActivity();
		activity1.setDelegate( new Calculator() );
		activity1.setMethod( "sum" );
		activity1.setParameters( Arrays.asList( "addendum1", "addendum2") );
		activity1.setResultParameter( "sum1" );
		
		DelegateActivity activity2 = new DelegateActivity();
		activity2.setDelegate( new Calculator() );
		activity2.setMethod( "sum" );
		activity2.setParameters( Arrays.asList( "sum1", "sum1") );
		activity2.setResultParameter( "sum2" );
		
		SequenceProcessor processor = new SequenceProcessor();
		processor.setDefaultExceptionHandler( new ConsoleExceptionHandler() );
		processor.setActivities( Arrays.asList(  activity1, activity2 ) );
		
		processor.execute( context );
		
		assertEquals( sum1,  (Integer) context.get( "sum1" ) );
		assertEquals( sum2,  (Integer) context.get( "sum2" ) );
	}
	
	@Test
	public void testConcurrentDelegate() {
		System.out.println( "Start testConcurrentDelegate" );
		
		DelegateActivity activity1 = new DelegateActivity();
		activity1.setDelegate( new Calculator() );
		activity1.setMethod( "sum" );
		activity1.setParameters( Arrays.asList( "addendum1", "addendum2") );
		activity1.setResultParameter( "sum1" );
		
		DelegateActivity activity2 = new DelegateActivity();
		activity2.setDelegate( new Calculator() );
		activity2.setMethod( "mult" );
		activity2.setParameters( Arrays.asList( "addendum1", "addendum2") );
		activity2.setResultParameter( "mult" );
		
		ConcurrentProcessor processor = new ConcurrentProcessor();
		processor.setDefaultExceptionHandler( new ConsoleExceptionHandler() );
		processor.setActivities( Arrays.asList(  activity1, activity2 ) );
		
		processor.execute( context );
		
		assertEquals( sum1, (Integer) context.get( "sum1") );
		assertEquals( mult, (Integer) context.get( "mult") );	
	}
}
