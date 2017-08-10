package it.inspired.wf;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestSumActivity {
	
	private WorkflowContext context = new WorkflowContext();
	private Integer addendum1 = 3;
	private Integer addendum2 = 2;
	private Integer sum1  = addendum1 + addendum2;

	
	@Before
	public void init() {
		context.put( "addendum1", addendum1 );
		context.put( "addendum2", addendum2 );
	}
	
	@Test
	public void test() throws Exception {
		SumActivity activity = new SumActivity();
		
		context = activity.execute(context);
		
		assertEquals( sum1, (Integer) context.get( "sum") );
	}
}
