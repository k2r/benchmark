/**
 * 
 */
package autoscale.benchmark.cluster;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author Roland
 *
 */
public class SupervisorLauncher implements Runnable {

	private String os;
	private Process process;
	private ArrayList<String> supervisors;
	private static Logger logger = Logger.getLogger("SupervisorLauncher");
	
	public SupervisorLauncher(String os, ArrayList<String> supervisors) {
		this.setOs(os);
		this.setSupervisors(supervisors);
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
	 * @return the supervisors
	 */
	public ArrayList<String> getSupervisors() {
		return supervisors;
	}

	/**
	 * @param supervisors the supervisors to set
	 */
	public void setSupervisors(ArrayList<String> supervisors) {
		this.supervisors = supervisors;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

}
