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
	
	public static ArrayList<String> getConnections(String os, String port){
		ArrayList<String> result = new ArrayList<>();
		ProcessBuilder builder = null;
		Process p = null;
		try{
			if(os.equalsIgnoreCase("windows")){
				builder = new ProcessBuilder("cmd.exe", "/C", "netstat", "-a", "-o", "-n", "|", "findstr", port);
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
		}catch(Exception e){
			logger.severe("Cannot find nimbus PID because " + e);
		}
		return result;
	}
	
	public static String getPID(String os, String port){
		String result = "";
		ArrayList<String> nimbusConnections = getConnections(os, port);
		String[] info = nimbusConnections.get(0).split(" ");
		int lastIndex = info.length - 1;
		if(os.equalsIgnoreCase("windows")){
			result = info[lastIndex];
		}
		return result;
	}
}