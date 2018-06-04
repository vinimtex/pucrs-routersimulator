package models;

public class Router {
	
	private String ip;
	private RouterTable routerTable;
	
	public Router(String ip) {
		this.ip = ip;
		routerTable = new RouterTable();
	}
	
	public Router(String ip, RouterTable routerTable) {
		this.ip = ip;
		this.routerTable = routerTable;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public RouterTable getRouterTable() {
		return routerTable;
	}

	public void setRouterTable(RouterTable routerTable) {
		this.routerTable = routerTable;
	}
	
	
	
}
