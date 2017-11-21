/**
 * 
 */
package autoscale.benchmark;

import static org.junit.Assert.*;

import org.junit.Test;

import autoscale.benchmark.config.StormConfigMaker;

/**
 * @author Roland
 *
 */
public class StormConfigMakerTest {

	/**
	 * Test method for {@link autoscale.benchmark.config.StormConfigMaker#setStormScheduler()}.
	 */
	@Test
	public void testSetStormScheduler() {
		String autoscaleplus = "\"storm.autoscale.scheduler.AutoscalePlusScheduler\"";
		String rstorm = "\"storm.autoscale.scheduler.MonitoredResourceAwareScheduler\"";
		String incremental = "\"storm.autoscale.scheduler.IncrementalScheduler\"";
		String learning = "\"storm.autoscale.scheduler.RLearningScheduler\"";
		
		StormConfigMaker cMaker = new StormConfigMaker(autoscaleplus);
		cMaker.setStormScheduler();
		assertEquals(autoscaleplus, cMaker.getStormScheduler());
		
		cMaker.setScheduler(rstorm);
		cMaker.setStormScheduler();
		assertEquals(rstorm, cMaker.getStormScheduler());
		
		cMaker.setScheduler(incremental);
		cMaker.setStormScheduler();
		assertEquals(incremental, cMaker.getStormScheduler());
		
		cMaker.setScheduler(learning);
		cMaker.setStormScheduler();
		assertEquals(learning, cMaker.getStormScheduler());
	}

}
