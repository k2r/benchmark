/**
 * 
 */
package autoscale.benchmark.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author Roland
 *
 */
public class PIDFinder {

	private static Logger logger = Logger.getLogger("PIDFinder");
	
	private static BufferedReader getBufferedReader(InputStream is) {
        return new BufferedReader(new InputStreamReader(is));
    }
	
	public static ArrayList<String> getConnections(String os, String identifier){
		ArrayList<String> result = new ArrayList<>();
		ProcessBuilder builder = null;
		Process p = null;
		try{
			if(os.equalsIgnoreCase("windows")){
				builder = new ProcessBuilder("cmd.exe", "/C", "netstat", "-a", "-o", "-n", "|", "findstr", identifier);
			}
			if(os.equalsIgnoreCase("unix")){
				builder = new ProcessBuilder("/bin/sh", "-c", "sudo netstat -nlp | grep " + identifier);
			}
			p = builder.start();
			BufferedReader br = getBufferedReader(p.getInputStream());
	        String line = "";
	        try {
	            while ((line = br.readLine()) != null) {
	            	result.add(line);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        p.waitFor();
		}catch(Exception e){
			logger.severe("Cannot find PID because " + e);
		}
		return result;
	}
	
	public static Integer getPID(String os, String port){
		Integer result = null;
		ArrayList<String> connections = getConnections(os, port);
		String[] info = connections.get(0).split(" ");
		int lastIndex = info.length - 1;
		if(os.equalsIgnoreCase("windows")){
			result = Integer.parseInt(info[lastIndex]);
		}
		if(os.equalsIgnoreCase("unix")){
			result = Integer.parseInt(info[lastIndex].split("/")[0]);
		}
		return result;
	}
}