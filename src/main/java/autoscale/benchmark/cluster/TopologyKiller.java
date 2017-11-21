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
public class TopologyKiller implements Runnable {

	private String os;
	private String topology;
	private static Logger logger = Logger.getLogger("TopologyKiller");
	
	public TopologyKiller(String os, String topology) {
		this.setOs(os);
		this.setTopology(topology);
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
				dir = new File("C:/These/D3/Github/benchmark/apache-storm-1.0.2/");
				builder = new ProcessBuilder("cmd", "/C", "storm", "kill", this.getTopology());
			}
			if(os.equalsIgnoreCase("unix")){
				dir = new File("/home/ubuntu/apache-storm-1.0.2/");
				builder = new ProcessBuilder("bash", "./bin/storm", "kill", this.getTopology());
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
			logger.severe("Topology " + this.getTopology() + " cannot be killed because " + e);
		}
	}
}