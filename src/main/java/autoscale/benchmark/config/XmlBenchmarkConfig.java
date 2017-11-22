/**
 * 
 */
package autoscale.benchmark.config;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Roland
 *
 */
public class XmlBenchmarkConfig {

	/*Launch file parameters*/
	private String filename;
	private final DocumentBuilderFactory factory;
	private final DocumentBuilder builder;
	private final Document document;
	
	/*Operating system supporting the benchmark*/
	private String os;
	private String benchHome;
	
	/*Configurations to run*/
	private ArrayList<Config> configs;
	
	/*Iteration info*/
	private Integer nbIter;
	private Integer iterDuration;
	
	/*Cluster info*/
	private String nimbusPort;
	private String uiPort;
	private ArrayList<String> supervisors;
	
	/*Database info and credentials*/
	private String dbHost;
	private String dbName;
	private String username;
	private String password;
	
	public XmlBenchmarkConfig(String filename) throws ParserConfigurationException, SAXException, IOException{
		this.filename = filename;
		this.factory = DocumentBuilderFactory.newInstance();
		this.builder = factory.newDocumentBuilder();
		this.document = builder.parse(this.getFilename());
		this.configs = new ArrayList<>();
		this.supervisors = new ArrayList<>();
	}

	/**
	 * @return the filename
	 */
	public final String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public final void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the os
	 */
	public String getOs() {
		return os;
	}

	/**
	 * @param os the os to set
	 */
	public void setOs(String os) {
		this.os = os;
	}

	/**
	 * @return the benchHome
	 */
	public String getBenchHome() {
		return benchHome;
	}

	/**
	 * @param benchHome the stormHome to set
	 */
	public void setBenchHome(String benchHome) {
		this.benchHome = benchHome;
	}

	/**
	 * @return the configs
	 */
	public final ArrayList<Config> getConfigs() {
		return configs;
	}

	/**
	 * @param configs the configs to set
	 */
	public final void setConfigs(ArrayList<Config> configs) {
		this.configs = configs;
	}

	/**
	 * @return the nbIter
	 */
	public final Integer getNbIter() {
		return nbIter;
	}

	/**
	 * @param nbIter the nbIter to set
	 */
	public final void setNbIter(Integer nbIter) {
		this.nbIter = nbIter;
	}

	

	/**
	 * @return the iterDuration
	 */
	public Integer getIterDuration() {
		return iterDuration;
	}

	/**
	 * @param iterDuration the iterDuration to set
	 */
	public void setIterDuration(Integer iterDuration) {
		this.iterDuration = iterDuration;
	}

	/**
	 * @return the nimbusPort
	 */
	public String getNimbusPort() {
		return nimbusPort;
	}

	/**
	 * @param nimbusPort the nimbusPort to set
	 */
	public void setNimbusPort(String nimbusPort) {
		this.nimbusPort = nimbusPort;
	}

	/**
	 * @return the uiPort
	 */
	public String getUiPort() {
		return uiPort;
	}

	/**
	 * @param uiPort the uiPort to set
	 */
	public void setUiPort(String uiPort) {
		this.uiPort = uiPort;
	}

	/**
	 * @return the supervisors
	 */
	public final ArrayList<String> getSupervisors() {
		return supervisors;
	}

	/**
	 * @param supervisors the supervisors to set
	 */
	public final void setSupervisors(ArrayList<String> supervisors) {
		this.supervisors = supervisors;
	}

	/**
	 * @return the dbHost
	 */
	public final String getDbHost() {
		return dbHost;
	}

	/**
	 * @param dbHost the dbHost to set
	 */
	public final void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}

	/**
	 * @return the dbName
	 */
	public final String getDbName() {
		return dbName;
	}

	/**
	 * @param dbName the dbName to set
	 */
	public final void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * @return the username
	 */
	public final String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public final void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public final String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public final void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the document
	 */
	public final Document getDocument() {
		return document;
	}
	
	public void initParameters() {
		Document doc = this.getDocument();
		final Element benchmark = (Element) doc.getElementsByTagName(Fields.BENCH.toString()).item(0);
		final NodeList os = benchmark.getElementsByTagName(Fields.OS.toString());
		this.setOs(os.item(0).getTextContent());
		final NodeList benchHome = benchmark.getElementsByTagName(Fields.BENCHHOME.toString());
		this.setBenchHome(benchHome.item(0).getTextContent());
		final NodeList configs =  benchmark.getElementsByTagName(Fields.CONFIG.toString());
		int nbConfig = configs.getLength();
		for(int i = 0; i < nbConfig; i++){
			final Element config = (Element) configs.item(i);
			final NodeList shortname = config.getElementsByTagName(Fields.SHNAME.toString());
			final NodeList scheduler = config.getElementsByTagName(Fields.SCHED.toString());
			final NodeList balancing = config.getElementsByTagName(Fields.BAL.toString());
			final NodeList topology = config.getElementsByTagName(Fields.TOPO.toString());
			final NodeList stream = config.getElementsByTagName(Fields.STREAM.toString());
			final NodeList distribution = config.getElementsByTagName(Fields.DISTR.toString());
			final NodeList skew = config.getElementsByTagName(Fields.SKEW.toString());
			Config conf = new Config(shortname.item(0).getTextContent(), scheduler.item(0).getTextContent(),
					balancing.item(0).getTextContent(), topology.item(0).getTextContent(), 
					stream.item(0).getTextContent(), distribution.item(0).getTextContent(), skew.item(0).getTextContent());
			this.configs.add(conf);
		}
		final Element iteration = (Element) doc.getElementsByTagName(Fields.ITER.toString()).item(0);
		final NodeList nbIter = iteration.getElementsByTagName(Fields.NBIT.toString());
		this.setNbIter(Integer.parseInt(nbIter.item(0).getTextContent()));
		final NodeList durIter = iteration.getElementsByTagName(Fields.DURIT.toString());
		this.setIterDuration(Integer.parseInt(durIter.item(0).getTextContent()));
		final Element cluster = (Element) doc.getElementsByTagName(Fields.CLUS.toString()).item(0);
		final NodeList nimbus = cluster.getElementsByTagName(Fields.NIMB.toString());
		this.setNimbusPort(nimbus.item(0).getTextContent());
		final NodeList ui = cluster.getElementsByTagName(Fields.UI.toString());
		this.setUiPort(ui.item(0).getTextContent());
		final NodeList supervisors = cluster.getElementsByTagName(Fields.SUP.toString());
		int nbSup = supervisors.getLength();
		for(int i = 0; i < nbSup; i++){
			final Node supervisor = supervisors.item(i);
			this.supervisors.add(supervisor.getTextContent());
		}
		final Element database = (Element) doc.getElementsByTagName(Fields.DB.toString()).item(0);
		final NodeList dbhost = database.getElementsByTagName(Fields.DBHOST.toString());
		this.setDbHost(dbhost.item(0).getTextContent());
		final NodeList dbname = database.getElementsByTagName(Fields.DBNAME.toString());
		this.setDbName(dbname.item(0).getTextContent());
		final NodeList dbuser = database.getElementsByTagName(Fields.DBUSER.toString());
		this.setUsername(dbuser.item(0).getTextContent());
		final NodeList dbpwd = database.getElementsByTagName(Fields.DBPWD.toString());
		this.setPassword(dbpwd.item(0).getTextContent());
	}
}
