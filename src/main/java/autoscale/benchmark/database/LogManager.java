/**
 * 
 */
package autoscale.benchmark.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

import autoscale.benchmark.util.BufferPrinter;

/**
 * @author Roland
 *
 */
public class LogManager {
	
	private String os;
	private String dbHost;
	private String dbName;
	private String dbUser;
	private String dbPwd;
	private String dbUrl;
	private String configName;
	private Connection connection;
	
	private final String root = "./autoscale_benchmark/";
	private static final String jdbcDriver = "com.mysql.jdbc.Driver";
	private static Logger logger = Logger.getLogger("LogManager");
	
	public LogManager(String os, String dbHost, String dbName, String dbUser, String dbPwd, String configName) throws ClassNotFoundException, SQLException, IOException {
		this.os = os;
		this.dbHost = dbHost;
		this.dbName = dbName;
		this.dbUser = dbUser;
		this.dbPwd = dbPwd;
		this.setDbUrl("jdbc:mysql://" + this.dbHost + "/" + this.dbName);
		this.configName = configName;	
		
		Class.forName(jdbcDriver);
		this.connection = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPwd);
		
		Path path = Paths.get(this.root);
		if(!Files.exists(path)){
			Files.createDirectory(path);
		}
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
	 * @return the dbHost
	 */
	public final String getDbHost() {
		return dbHost;
	}

	/**
	 * @param dbHost the dbHost to set
	 */
	public final void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}

	/**
	 * @return the dbName
	 */
	public final String getDbName() {
		return dbName;
	}

	/**
	 * @param dbName the dbName to set
	 */
	public final void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * @return the dbUser
	 */
	public final String getDbUser() {
		return dbUser;
	}

	/**
	 * @param dbUser the dbUser to set
	 */
	public final void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	/**
	 * @return the dbPwd
	 */
	public final String getDbPwd() {
		return dbPwd;
	}

	/**
	 * @param dbPwd the dbPwd to set
	 */
	public final void setDbPwd(String dbPwd) {
		this.dbPwd = dbPwd;
	}

	/**
	 * @return the dbUrl
	 */
	public String getDbUrl() {
		return dbUrl;
	}

	/**
	 * @param dbUrl the dbUrl to set
	 */
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	/**
	 * @return the configName
	 */
	public final String getConfigName() {
		return configName;
	}

	/**
	 * @param configName the configName to set
	 */
	public final void setConfigName(String configName) {
		this.configName = configName;
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public Statement getNewStatement() throws SQLException {
		Statement statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		return statement;
	}
	
	public int executeUpdate(String query) throws SQLException {
		Statement statement = this.getNewStatement();
		int result = statement.executeUpdate(query);
		return result;
	}
	
	public void dumpLogs(){
		ProcessBuilder builder = null;
		Process p;
		try{
			String os = this.getOs();
			if(os.equalsIgnoreCase("windows")){
				builder = new ProcessBuilder("cmd", "/C", "mysqldump", "-h", this.getDbHost(), "-u", this.getDbUser(), "-p" 
						+ this.getDbPwd(), "autoscale", ">", this.root + this.getConfigName() + ".sql");
			}
			if(os.equalsIgnoreCase("unix")){
				builder = new ProcessBuilder("/bin/sh", "-c", "sudo mysqldump -h " + this.getDbHost() + " -u " + this.getDbUser()
						+ " -p" + this.getDbPwd() + " autoscale > " + this.root + this.getConfigName() + ".sql");
			}
			p = builder.start();
			BufferPrinter outputStream = new BufferPrinter(p.getInputStream());
			BufferPrinter errorStream = new BufferPrinter(p.getErrorStream());
			outputStream.run();
			errorStream.run();
			p.waitFor();
		}catch(Exception e){
			logger.severe("Logs cannot be dumped from database because " + e);
		}
	}
	
	public void clearLogs() throws SQLException{
		String spoutTable = "all_time_spouts_stats";
		String boltTable = "all_time_bolts_stats";
		String constraintTable = "operators_constraints";
		String estimTable = "operators_estimation";
		String scaleTable = "scales";
		String statusTable = "topologies_status";
		
		ArrayList<String> tables = new ArrayList<>();
		tables.add(boltTable);
		tables.add(spoutTable);
		tables.add(constraintTable);
		tables.add(estimTable);
		tables.add(scaleTable);
		tables.add(statusTable);
		
		String cleanCommand = "DELETE FROM ";
		
		for(String table : tables){
			String cleanQuery = cleanCommand + table;
			this.executeUpdate(cleanQuery);
		}
	}

	
}