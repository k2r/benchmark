/**
 * 
 */
package autoscale.benchmark;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import autoscale.benchmark.config.Config;
import autoscale.benchmark.config.XmlBenchmarkConfig;

/**
 * @author Roland
 *
 */
public class XmlBenchmarkConfigTest {

	public static XmlBenchmarkConfig config; 
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		config = new XmlBenchmarkConfig("benchmarkTest.xml");
		config.initParameters();
	}
	
	/**
	 * Test method for {@link autoscale.benchmark.config.XmlBenchmarkConfig#setConfigs(java.util.ArrayList)}.
	 */
	@Test
	public void testSetConfigs() {
		ArrayList<Config> actual = config.getConfigs();
		ArrayList<Config> expected = new ArrayList<>();
		Config config1 = new Config("config1", "\"storm.autoscale.scheduler.AutoscalePlusScheduler\"", "shuffle", "simpleSensitive", "uneven", "uniform", "1.0");
		Config config2 = new Config("config2", "\"storm.autoscale.scheduler.AutoscalePlusScheduler\"", "osg", "simpleSensitive", "uneven", "zipf", "1.5");
		Config config3 = new Config("config3", "\"storm.autoscale.scheduler.MonitoredResourceAwareScheduler\"", "shuffle", "simpleSensitive", "uneven", "zipf", "1.5");
		
		expected.add(config1);
		expected.add(config2);
		expected.add(config3);
		
		int nbConfig = config.getConfigs().size();
		
		for(int i = 0; i < nbConfig; i++){
			Config expectedConfig = expected.get(i);
			Config actualConfig = actual.get(i);
			assertTrue(expectedConfig.equals(actualConfig));
		}
	}

	/**
	 * Test method for {@link autoscale.benchmark.config.XmlBenchmarkConfig#setNbIter(java.lang.Integer)}.
	 */
	@Test
	public void testSetNbIter() {
		Integer actual = config.getNbIter();
		Integer expected = 10;
		
		assertEquals(expected, actual, 0);
	}

	/**
	 * Test method for {@link autoscale.benchmark.config.XmlBenchmarkConfig#setDurationIter(java.lang.Integer)}.
	 */
	@Test
	public void testSetDurationIter() {
		Integer actual = config.getIterDuration();
		Integer expected = 15;
		
		assertEquals(expected, actual, 0);
	}

	/**
	 * Test method for {@link autoscale.benchmark.config.XmlBenchmarkConfig#setNimbus(java.lang.String)}.
	 */
	@Test
	public void testSetNimbus() {
		String actual = config.getNimbusPort();
		String expected = "6627";
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link autoscale.benchmark.config.XmlBenchmarkConfig#setSupervisors(java.util.ArrayList)}.
	 */
	@Test
	public void testSetSupervisors() {
		ArrayList<String> actual = config.getSupervisors();
		ArrayList<String> expected = new ArrayList<>();
		expected.add("192.168.0.1");
		expected.add("192.168.0.2");
		expected.add("192.168.0.3");
		expected.add("192.168.0.4");
		expected.add("192.168.0.5");
		expected.add("192.168.0.6");
		expected.add("192.168.0.7");
		expected.add("192.168.0.8");
		expected.add("192.168.0.9");
		
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link autoscale.benchmark.config.XmlBenchmarkConfig#setDbHost(java.lang.String)}.
	 */
	@Test
	public void testSetDbHost() {
		String actual = config.getDbHost();
		String expected = "127.0.0.1";
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link autoscale.benchmark.config.XmlBenchmarkConfig#setDbName(java.lang.String)}.
	 */
	@Test
	public void testSetDbName() {
		String actual = config.getDbName();
		String expected = "autoscale";
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link autoscale.benchmark.config.XmlBenchmarkConfig#setUsername(java.lang.String)}.
	 */
	@Test
	public void testSetUsername() {
		String actual = config.getUsername();
		String expected = "root";
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link autoscale.benchmark.config.XmlBenchmarkConfig#setPassword(java.lang.String)}.
	 */
	@Test
	public void testSetPassword() {
		String actual = config.getPassword();
		String expected = "autoscale";
		assertEquals(expected, actual);
	}

}