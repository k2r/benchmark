/**
 * 
 */
package autoscale.benchmark.config;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Roland
 * This class modifies the name of the topology
 */
public class TopologyConfigMaker {
	
	private String topology;
	private final String pathToFile = "./topParameters.xml";
	private final DocumentBuilderFactory factory;
	private final DocumentBuilder builder;
	private final Document document;
	
	public TopologyConfigMaker(String topology) throws ParserConfigurationException, SAXException, IOException {
		this.setTopology(topology);
		this.factory = DocumentBuilderFactory.newInstance();
		this.builder = factory.newDocumentBuilder();
		this.document = builder.parse(this.pathToFile);
	}
	
	/**
	 * @return the topology
	 */
	public String getTopology() {
		return topology;
	}


	/**
	 * @param topology the topology to set
	 */
	public void setTopology(String topology) {
		this.topology = topology;
	}

	/**
	 * @return the document
	 */
	public final Document getDocument() {
		return document;
	}

	public String getTopologyName(){
		Document doc = this.getDocument();
		final Element parameters = (Element) doc.getElementsByTagName(Fields.PARAM.toString()).item(0);
		final NodeList topologyId = parameters.getElementsByTagName(Fields.TOPID.toString());
		return topologyId.item(0).getTextContent();
	}

	public void setTopologyName() throws TransformerException{
		Document doc = this.getDocument();
		final Element parameters = (Element) doc.getElementsByTagName(Fields.PARAM.toString()).item(0);
		final NodeList topologyId = parameters.getElementsByTagName(Fields.TOPID.toString());
		topologyId.item(0).setTextContent(this.topology);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(this.pathToFile));
		transformer.transform(source, result);
	}
}
