package models;

public class RouterTableRow {
	private String destinationIp;
	private int metric;
	private String gatewayIp;
	
	public RouterTableRow(String destinationIp, int metric, String outputIp) {
		this.destinationIp = destinationIp;
		this.metric = metric;
		this.gatewayIp = outputIp;
	}
}
