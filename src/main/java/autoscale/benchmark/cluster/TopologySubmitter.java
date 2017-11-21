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
public class TopologySubmitter implements Runnable {

	private String os;
	private String topology;
	private String stream;
	private String loadBalancer;
	private String distribution;
	private String skew;
	private final String packageName = "stormBench.stormBench.";
	private static Logger logger = Logger.getLogger("TopologyManager");
	
	public TopologySubmitter(String os, String topology, String stream, String loadBalancer, String distribution, String skew) {
		this.setOs(os);
		this.setTopology(topology);
		this.setStream(stream);
		this.setLoadBalancer(loadBalancer);
		this.setDistribution(distribution);
		this.setSkew(skew);
	}
	
	/**
	 * @return the os
	 */
	public final String getOs() {
		return os;
	}

	/**
	 * @param os the os to set
	 */
	public final void setOs(String os) {
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
	 * @return the distribution
	 */
	public final String getDistribution() {
		return distribution;
	}

	/**
	 * @param distribution the distribution to set
	 */
	public final void setDistribution(String distribution) {
		this.distribution = distribution;
	}

	/**
	 * @return the skew
	 */
	public final String getSkew() {
		return skew;
	}

	/**
	 * @param skew the skew to set
	 */
	public final void setSkew(String skew) {
		this.skew = skew;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
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
			builder.inheritIO();
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

}
