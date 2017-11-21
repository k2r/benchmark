/**
 * 
 */
package autoscale.benchmark;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.xml.sax.SAXException;

import autoscale.benchmark.config.TopologyConfigMaker;

/**
 * @author Roland
 *
 */
public class TopologyConfigMakerTest {

	/**
	 * Test method for {@link autoscale.benchmark.config.TopologyConfigMaker#setTopologyName()}.
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 */
	@Test
	public void testSetTopologyName() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		String simpleInsensitive = "simpleInsensitive";
		String simpleSensitive = "simpleSensitive";
		String complexInsensitive = "complexInsensitive";
		String complexSensitive = "complexSensitive";
		
		TopologyConfigMaker cMaker = new TopologyConfigMaker(simpleInsensitive);
		cMaker.setTopologyName();
		assertEquals(simpleInsensitive, cMaker.getTopologyName());
		
		cMaker.setTopology(simpleSensitive);
		cMaker.setTopologyName();
		assertEquals(simpleSensitive, cMaker.getTopologyName());
		
		cMaker.setTopology(complexInsensitive);
		cMaker.setTopologyName();
		assertEquals(complexInsensitive, cMaker.getTopologyName());
		
		cMaker.setTopology(complexSensitive);
		cMaker.setTopologyName();
		assertEquals(complexSensitive, cMaker.getTopologyName());
	}

}