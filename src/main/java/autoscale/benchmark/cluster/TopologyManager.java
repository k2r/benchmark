/**
 * 
 */
package autoscale.benchmark.cluster;

import java.io.File;
import java.util.logging.Logger;

import autoscale.benchmark.util.BufferPrinter;

/**
 * @author Roland
 *
 */
public class TopologyManager {

	private String os;
	private String topology;
	private String stream;
	private String loadBalancer;
	private String distribution;
	private String skew;
	private final String packageName = "stormBench.stormBench.";
	private static Logger logger = Logger.getLogger("TopologyManager");
	
	public TopologyManager(String os, String topology, String stream, String loadBalancer, String distribution, String skew) {
		this.os = os;
		this.topology = topology;
		this.stream = stream;
		this.loadBalancer = loadBalancer;
		this.setDistribution(distribution);
		this.setSkew(skew);
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
	 * @return the topology
	 */
	public final String getTopology() {
		return topology;
	}

	/**
	 * @param topology the topology to set
	 */
	public final void setTopology(String topology) {
		this.topology = topology;
	}

	/**
	 * @return the stream
	 */
	public final String getStream() {
		return stream;
	}

	/**
	 * @param stream the stream to set
	 */
	public final void setStream(String stream) {
		this.stream = stream;
	}

	/**
	 * @return the loadBalancer
	 */
	public final String getLoadBalancer() {
		return loadBalancer;
	}

	/**
	 * @param loadBalancer the loadBalancer to set
	 */
	public final void setLoadBalancer(String loadBalancer) {
		this.loadBalancer = loadBalancer;
	}
	
	/**
	 * @return the skew
	 */
	public String getSkew() {
		return skew;
	}

	/**
	 * @param skew the skew to set
	 */
	public void setSkew(String skew) {
		this.skew = skew;
	}

	/**
	 * @return the distribution
	 */
	public String getDistribution() {
		return distribution;
	}

	/**
	 * @param distribution the distribution to set
	 */
	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}

	public void submitTopology(){
		File dir = null;
		ProcessBuilder builder = null;
		Process p;
		try{
			String os = this.getOs();
			if(os.equalsIgnoreCase("windows")){
				dir = new File("C:/These/D3/Github/benchmark/");
				builder = new ProcessBuilder("cmd", "/C", "storm", "jar", "./benchTopologies.jar", this.packageName + this.getTopology() + "Topology",
						this.getStream(), this.getLoadBalancer(), this.getDistribution(), this.getSkew());
			}
			if(os.equalsIgnoreCase("unix")){
				dir = new File("/home/ubuntu/");
				builder = new ProcessBuilder("bash", "./apache-storm-1.0.2/bin/storm", "jar", "./benchTopologies.jar", this.packageName + this.getTopology() + "Topology",
						this.getStream(), this.getLoadBalancer(), this.getDistribution(), this.getSkew());
			}
			builder.directory(dir);
			p = builder.start();
			BufferPrinter outputStream = new BufferPrinter(p.getInputStream());
			BufferPrinter errorStream = new BufferPrinter(p.getErrorStream());
			outputStream.run();
			errorStream.run();
			p.waitFor();
			
		}catch(Exception e){
			logger.severe("Topology " + this.getTopology() + " cannot be submitted because " + e);
		}
	}
	
	public void stopTopology(){
		File dir = null;
		ProcessBuilder builder = null;
		Process p;
		try{
			String os = this.getOs();
			if(os.equalsIgnoreCase("windows")){
				dir = new File("C:/These/D3/Github/benchmark/apache-storm-1.0.2/");
				builder = new ProcessBuilder("cmd", "/C", "storm", "kill", this.getTopology());
			}
			if(os.equalsIgnoreCase("unix")){
				dir = new File("/home/ubuntu/apache-storm-1.0.2/");
				builder = new ProcessBuilder("bash", "./bin/storm", "kill", this.getTopology());
			}
			builder.directory(dir);
			p = builder.start();
			BufferPrinter outputStream = new BufferPrinter(p.getInputStream());
			BufferPrinter errorStream = new BufferPrinter(p.getErrorStream());
			outputStream.run();
			errorStream.run();
			p.waitFor();
			
		}catch(Exception e){
			logger.severe("Topology " + this.getTopology() + " cannot be killed because " + e);
		}
	}
}