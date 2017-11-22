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
	private String benchHome;
	private String topology;
	private static Logger logger = Logger.getLogger("TopologyKiller");
	
	public TopologyKiller(String os, String benchHome, String topology) {
		this.setOs(os);
		this.setBenchHome(benchHome);
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
		File dir = new File(this.getBenchHome() + "apache-storm-1.0.2/");
		ProcessBuilder builder = null;
		Process p;
		try{
			String os = this.getOs();
			if(os.equalsIgnoreCase("windows")){
				builder = new ProcessBuilder("cmd", "/C", "storm", "kill", this.getTopology(), "-w" , "0");
			}
			if(os.equalsIgnoreCase("unix")){
				builder = new ProcessBuilder("bash", "./bin/storm", "kill", this.getTopology(), "-w" , "0");
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