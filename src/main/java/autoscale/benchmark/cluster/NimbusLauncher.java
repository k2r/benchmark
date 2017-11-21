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
	private Process process;
	private static Logger logger = Logger.getLogger("NimbusLauncher");
	
	public NimbusLauncher(String os) {
		this.setOs(os);
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
			if(os.equalsIgnoreCase("windows")){
				builder = new ProcessBuilder("cmd.exe", "/C", "kill", "-9", PIDFinder.getPID(os, "6627"));
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
		File dir = null;
		ProcessBuilder builder = null;
		try{
			String os = this.getOs();
			if(os.equalsIgnoreCase("windows")){
				dir = new File("C:/These/D3/Github/benchmark/apache-storm-1.0.2/");
				builder = new ProcessBuilder("cmd.exe", "/C", "storm", "nimbus");
				
			}
			if(os.equalsIgnoreCase("unix")){
				dir = new File("/home/ubuntu/apache-storm-1.0.2/");
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