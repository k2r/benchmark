/**
 * 
 */
package autoscale.benchmark.config;

/**
 * @author Roland
 *
 */
public class Config {

	private String shortname;
	private String scheduler;
	private String balancing;
	private String topology;
	private String stream;
	private String distribution;
	private String skew;

	public Config(String shortname, String scheduler, String balancing, String topology, String stream, String distribution, String skew){
		this.shortname = shortname;
		this.scheduler = scheduler;
		this.balancing = balancing;
		this.topology = topology;
		this.stream = stream;
		this.distribution = distribution;
		this.skew = skew;
	}

	/**
	 * @return the shortname
	 */
	public final String getShortname() {
		return shortname;
	}

	/**
	 * @param shortname the shortname to set
	 */
	public final void setShortname(String shortname) {
		this.shortname = shortname;
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

	/**
	 * @return the balancing
	 */
	public String getBalancing() {
		return balancing;
	}

	/**
	 * @param balancing the balancing to set
	 */
	public void setBalancing(String balancing) {
		this.balancing = balancing;
	}
	
	/**
	 * @return the topology
	 */
	public final String getTopology() {
		return topology;
	}

	/**
	 * @param topology the topology to set
	 */
	public final void setTopology(String topology) {
		this.topology = topology;
	}

	/**
	 * @return the stream
	 */
	public final String getStream() {
		return stream;
	}

	/**
	 * @param stream the stream to set
	 */
	public final void setStream(String stream) {
		this.stream = stream;
	}
	
	/**
	 * @return the distribution
	 */
	public String getDistribution() {
		return distribution;
	}

	/**
	 * @param distribution the distribution to set
	 */
	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}

	/**
	 * @return the skew
	 */
	public String getSkew() {
		return skew;
	}

	/**
	 * @param skew the skew to set
	 */
	public void setSkew(String skew) {
		this.skew = skew;
	}

	@Override
	public boolean equals(Object o){
		Config config = (Config) o;
		return this.shortname.equalsIgnoreCase(config.getShortname()) && this.scheduler.equalsIgnoreCase(config.getScheduler()) && 
				this.balancing.equalsIgnoreCase(config.getBalancing()) && this.topology.equalsIgnoreCase(config.getTopology()) &&
				this.stream.equalsIgnoreCase(config.getStream());
	}
}
