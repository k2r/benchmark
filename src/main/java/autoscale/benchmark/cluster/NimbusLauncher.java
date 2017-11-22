/**
 * 
 */
package autoscale.benchmark.cluster;

import java.io.File;
import java.util.logging.Logger;

import autoscale.benchmark.util.BufferPrinter;
import autoscale.benchmark.util.PIDFinder;

/**
 * @author Roland
 *
 */
public class NimbusLauncher implements Runnable {

	private String os;
	private String benchHome;
	private String nimbusPort;
	private Process process;
	private static Logger logger = Logger.getLogger("NimbusLauncher");
	
	public NimbusLauncher(String os, String benchHome, String nimbusPort) {
		this.setOs(os);
		this.setBenchHome(benchHome);
		this.setNimbusPort(nimbusPort);
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
	 * @param benchHome the benchHome to set
	 */
	public void setBenchHome(String benchHome) {
		this.benchHome = benchHome;
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
	 * @return the process
	 */
	public Process getProcess() {
		return process;
	}

	/**
	 * @param process the process to set
	 */
	public void setProcess(Process process) {
		this.process = process;
	}
	
	public void killNimbusProcess(){
		ProcessBuilder builder = null;
		Process p = null;
		try{
			String os = this.getOs();
			String pid = PIDFinder.getPID(os, this.getNimbusPort()).toString();
			if(os.equalsIgnoreCase("windows")){
				builder = new ProcessBuilder("cmd.exe", "/C", "kill", "-9", pid);
			}
			if(os.equalsIgnoreCase("unix")){
				builder = new ProcessBuilder("sudo", "kill", "-9", pid);
			}
			System.out.println("Stopping the nimbus...");
			p = builder.start();
			p.waitFor();
		}catch(Exception e){
			logger.severe("Nimbus process cannot be killed because " + e);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		File dir = new File(this.getBenchHome() + "/apache-storm-1.0.2/");
		ProcessBuilder builder = null;
		try{
			String os = this.getOs();
			if(os.equalsIgnoreCase("windows")){
				builder = new ProcessBuilder("cmd.exe", "/C", "storm", "nimbus");
			}
			if(os.equalsIgnoreCase("unix")){
				builder = new ProcessBuilder("bash", "./bin/storm", "nimbus");
			}
			builder.directory(dir);
			builder.inheritIO();
			this.process = builder.start();
			BufferPrinter outputStream = new BufferPrinter(this.process.getInputStream());
			BufferPrinter errorStream = new BufferPrinter(this.process.getErrorStream());
			outputStream.run();
			errorStream.run();
			this.process.waitFor();
		}catch(Exception e){
			logger.severe("Nimbus process cannot be launched because " + e);
		}
	}
}