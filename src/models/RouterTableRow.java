package models;

import java.time.LocalDateTime;

public class RouterTableRow {
	private String destinationIp;
	private int metric;
	private String gatewayIp;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public RouterTableRow(String destinationIp, int metric, String outputIp) {
		this.destinationIp = destinationIp;
		this.metric = metric;
		this.gatewayIp = outputIp;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public String getDestinationIp() {
		return destinationIp;
	}

	public void setDestinationIp(String destinationIp) {
		this.updatedAt = LocalDateTime.now();
		this.destinationIp = destinationIp;
	}

	public int getMetric() {
		return metric;
	}

	public void setMetric(int metric) {
		this.updatedAt = LocalDateTime.now();
		this.metric = metric;
	}

	public String getGatewayIp() {
		return gatewayIp;
	}

	public void setGatewayIp(String gatewayIp) {
		this.updatedAt = LocalDateTime.now();
		this.gatewayIp = gatewayIp;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	
	
	
}
