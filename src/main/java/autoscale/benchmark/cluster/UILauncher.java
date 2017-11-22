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
public class UILauncher implements Runnable {

	private String os;
	private String benchHome;
	private String uiPort;
	private Process process;
	private static Logger logger = Logger.getLogger("UILauncher"); 
	
	public UILauncher(String os, String benchHome, String uiPort) {
		this.setOs(os);
		this.setBenchHome(benchHome);
		this.setUiPort(uiPort);
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

	public void killUIProcess(){
		ProcessBuilder builder = null;
		Process p = null;
		try{
			String os = this.getOs();
			String pid = PIDFinder.getPID(os, this.getUiPort()).toString();
			if(os.equalsIgnoreCase("windows")){
				builder = new ProcessBuilder("cmd.exe", "/C", "kill", "-9", pid);
			}
			if(os.equalsIgnoreCase("unix")){
				builder = new ProcessBuilder("sudo", "kill", "-9", pid);
			}
			System.out.println("Stopping the UI...");
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
		File dir = new File(this.getBenchHome());
		ProcessBuilder builder = null;
		try{
			String os = this.getOs();
			if(os.equalsIgnoreCase("windows")){
				builder = new ProcessBuilder("cmd.exe", "/C", "storm", "ui");
			}
			if(os.equalsIgnoreCase("unix")){
				builder = new ProcessBuilder("bash", "./apache-storm-1.0.2/bin/storm", "ui");
			}
			builder.directory(dir);
			this.process = builder.start();
			BufferPrinter outputStream = new BufferPrinter(this.process.getInputStream());
			BufferPrinter errorStream = new BufferPrinter(this.process.getErrorStream());
			outputStream.run();
			errorStream.run();
			this.process.waitFor();
		}catch(Exception e){
			logger.severe("UI process cannot be launched because " + e);
		}
	}

}