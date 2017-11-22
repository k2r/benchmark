/**
 * 
 */
package autoscale.benchmark.config;

/**
 * @author Roland
 *
 */
public enum Fields {

	BENCH("benchmark"),
	OS("operating_system"),
	BENCHHOME("bench_home"),
	CONFIG("configuration"),
	SHNAME("shortname"),
	SCHED("scheduler"),
	BAL("balancing"),
	TOPO("topology"),
	STREAM("stream"),
	DISTR("distribution"),
	SKEW("skew"),
	ITER("iteration"),
	NBIT("number"),
	DURIT("duration"),
	CLUS("cluster"),
	NIMB("nimbus_port"),
	UI("ui_port"),
	SUPS("supervisors"),
	SUP("supervisor"),
	DB("database"),
	DBHOST("host"),
	DBNAME("name"),
	DBUSER("username"),
	DBPWD("password"),
	PARAM("parameters"),
	TOPID("topology_id");
	
	private String name;
	
	private Fields(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
}
