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
	private Process process;
	private static Logger logger = Logger.getLogger("UILauncher"); 
	
	
	
	public UILauncher(String os) {
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
	
	public void killUIProcess(){
		ProcessBuilder builder = null;
		Process p = null;
		try{
			String os = this.getOs();
			if(os.equalsIgnoreCase("windows")){
				builder = new ProcessBuilder("cmd.exe", "/C", "kill", "-9", PIDFinder.getPID(os, "5371").toString());
			}
			if(os.equalsIgnoreCase("unix")){
				builder = new ProcessBuilder("sudo", "kill", "-9", PIDFinder.getPID(os, "5371").toString());
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
		File dir = null;
		ProcessBuilder builder = null;
		try{
			String os = this.getOs();
			if(os.equalsIgnoreCase("windows")){
				dir = new File("C:/These/D3/Github/benchmark/");
				builder = new ProcessBuilder("cmd.exe", "/C", "storm", "ui");
			}
			if(os.equalsIgnoreCase("unix")){
				dir = new File("/home/ubuntu/");
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