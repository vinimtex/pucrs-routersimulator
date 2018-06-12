package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RouterTableRow {
	private String destinationIp;
	private int metric;
	private String gatewayIp;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private boolean softDeleted;
	
	public RouterTableRow(String destinationIp, int metric, String outputIp) {
		this.destinationIp = destinationIp;
		this.metric = metric;
		this.gatewayIp = outputIp;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
		this.softDeleted = false;
	}

	public String getDestinationIp() {
		return destinationIp;
	}
	
	public boolean isSoftDeleted() {
		return this.softDeleted;
	}
	
	public void softDelete() {
		this.softDeleted = true;
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
	
	public String getCreatedAtFormated() {
		return createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}
	
	public String getUpdatedAtFormated() {
		return updatedAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}
	
	
	
	
}
