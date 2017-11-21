/**
 * 
 */
package autoscale.benchmark.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author Roland
 * This class manages the configuration file storm.yaml of Apache Storm according to a configuration 
 */
public class StormConfigMaker {

	private String scheduler;
	private final String filename = "./apache-storm-1.0.2/conf/storm.yaml";
	private Logger logger = Logger.getLogger("StormConfigMaker");
	
	public StormConfigMaker(String scheduler){
		this.scheduler = scheduler;
	}

	/**
	 * @return the scheduler
	 */
	public final String getScheduler() {
		return scheduler;
	}

	/**
	 * @param scheduler the scheduler to set
	 */
	public final void setScheduler(String scheduler) {
		this.scheduler = scheduler;
	}

	public String getStormScheduler(){
		String result = "";
		Path path = Paths.get(this.filename);
		try {
			ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(path, Charset.defaultCharset());
			for(String line : lines){
				if(!line.startsWith("#") && line.contains("storm.scheduler")){
					String[] fragments = line.split(" ");
					result += fragments[2];
				}
			}
		} catch (IOException e) {
			logger.severe("Storm configuration cannot be retrieved from storm.yaml because of " + e);
		} 
		return result;
	}
	
	public void setStormScheduler(){
		Path path = Paths.get(this.filename);
		try {
			ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(path, Charset.defaultCharset());
			ArrayList<String> newLines = new ArrayList<>();
			String newLine = " storm.scheduler: " + this.getScheduler();
			int index = -1;
			for(String line : lines){
				if(!line.startsWith("#") && line.contains("storm.scheduler")){
					index = lines.indexOf(line);
				}
			}
			int nbLines = lines.size();
			for(int i = 0; i < nbLines; i++){
				if(i == index){
					newLines.add(newLine);
				}else{
					newLines.add(lines.get(i));
				}
			}
			Files.write(path, newLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			logger.severe("Storm configuration cannot be retrieved from storm.yaml because of " + e);
		} 
	}
}