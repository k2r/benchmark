package autoscale.benchmark;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import autoscale.benchmark.cluster.NimbusLauncher;
import autoscale.benchmark.cluster.TopologyKiller;
import autoscale.benchmark.cluster.TopologySubmitter;
import autoscale.benchmark.cluster.UILauncher;
import autoscale.benchmark.config.Config;
import autoscale.benchmark.config.StormConfigMaker;
import autoscale.benchmark.config.TopologyConfigMaker;
import autoscale.benchmark.config.XmlBenchmarkConfig;
import autoscale.benchmark.database.LogManager;

/**
 *
 *
 */
public class Main {
    
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException, InterruptedException, ClassNotFoundException, SQLException{
        /*Read benchmark configurations and info*/
		System.out.println("Initialization of benchmark parameters...");
		XmlBenchmarkConfig benchmark = new XmlBenchmarkConfig("benchmark.xml");
		benchmark.initParameters();
		String os = benchmark.getOs();
		String benchHome = benchmark.getBenchHome();
		
		/*Initialize the cluster manager and launch the UI once*/
		//String nimbus = benchmark.getNimbus();
		//ArrayList<String> supervisors = benchmark.getSupervisors();
		
		System.out.println("Starting Storm UI...");
		String uiPort = benchmark.getUiPort();
		UILauncher uil = new UILauncher(os, benchHome, uiPort);
		Thread threadUI = new Thread(uil);
		threadUI.start();
		Thread.sleep(30000);
		
		/*Loop on configuration*/
		System.out.println("Reading configurations to run for the benchmark...");
		ArrayList<Config> configs = benchmark.getConfigs();
		int nbConfigs = configs.size();
		
		for(int i = 0; i < nbConfigs; i++){
			Config config = configs.get(i);
			
			/*Configure the nimbus*/
			System.out.println("Setting Storm configuration...");
			String scheduler = config.getScheduler();
			StormConfigMaker scMaker = new StormConfigMaker(scheduler);
			scMaker.setStormScheduler();
			
			/*Configure the topology*/
			System.out.println("Setting topology configuration...");
			String topology = config.getTopology();
			TopologyConfigMaker tcMaker = new TopologyConfigMaker(topology);
			tcMaker.setTopologyName();
			
			/*Launch the Nimbus*/
			System.out.println("Running Nimbus with appropriate scheduler...");
			String nimbusPort = benchmark.getNimbusPort();
			NimbusLauncher nimbl = new NimbusLauncher(os, benchHome, nimbusPort);
			Thread threadNimbus = new Thread(nimbl);
			threadNimbus.start();
			
			Thread.sleep(30000);
			
			/*Initialize topology info for submission and submit n iterations*/
			String stream = config.getStream();
			String loadBalancer = config.getBalancing();
			String distribution = config.getDistribution();
			String skew = config.getSkew();
			
			System.out.println("Running configuration " + i + "...");
			int nbIterations = benchmark.getNbIter();
			for(int j = 0; j < nbIterations; j++){
			
				/*Submit the topology*/
				TopologySubmitter submitter = new TopologySubmitter(os, benchHome, topology, stream, loadBalancer, distribution, skew);
				Thread threadSubmit = new Thread(submitter);
				threadSubmit.setDaemon(true);
				threadSubmit.start();
				Thread.sleep(10000);
		
				/*Wait n seconds*/
				int iterDuration = benchmark.getIterDuration() * 1000; // convert seconds to milliseconds
				Thread.sleep(iterDuration);
		
				/*Kill the topology*/
				TopologyKiller killer = new TopologyKiller(os, benchHome, topology);
				Thread threadKiller = new Thread(killer);
				threadKiller.setDaemon(true);
				threadKiller.start();
				Thread.sleep(15000);
			}
			
			/*After n iterations, dump the database*/
			String dbHost = benchmark.getDbHost();
			String dbName = benchmark.getDbName();
			String dbUser = benchmark.getUsername();
			String dbPwd = benchmark.getPassword();
			String configName = config.getShortname();
			LogManager logManager = new LogManager(os, dbHost, dbName, dbUser, dbPwd, configName);

			System.out.println("Dumping logs for configuration " + i + " (" + configName + ")...");
			logManager.dumpLogs();
			
			/*Clear the database*/
			System.out.println("Clearing log database...");
			logManager.clearLogs();
			
			/*Stop the nimbus*/
			System.out.println("Shutting down the nimbus...");
			nimbl.killNimbusProcess();
			threadNimbus.join();
			Thread.sleep(10000);
		}
		/*Stop the nimbus*/
		System.out.println("Shutting down the UI...");
		uil.killUIProcess();
		threadUI.join();
		
		/*Send an ending message*/
		System.out.println("Benchmark terminated!");
    }
}