package runnables;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import models.RouterTableRow;
import services.RouterService;

public class RemoveOutdatedRows implements Runnable {

	public RemoveOutdatedRows() {
		
	}
	
	@Override
	public void run() {
		
		while(true) {
			Iterator<RouterTableRow> iterator = RouterService.router.getRouterTable().getRows().iterator();
			while(iterator.hasNext()) {
				
				RouterTableRow row = iterator.next();
				if(row.getDestinationIp() != RouterService.router.getIp()) {
					if(!row.isSoftDeleted()) {
						if(row.getUpdatedAt().until(LocalDateTime.now(), ChronoUnit.SECONDS) > 30) {
							System.out.println("REMOVENDO IP " + row.getDestinationIp() + " POR FALTA DE ATUALIZAÇÃO");
							//row.softDelete();
							RouterService.router.getRouterTable().removeRow(row);
							RouterService.sendRouterTableNow();
							RouterService.updateRouterTableModelView();
						}
					}
					
					/*
					 * 
					 * if(!row.isSoftDeleted()) {
						if(row.getUpdatedAt().until(LocalDateTime.now(), ChronoUnit.SECONDS) > 30) {
							System.out.println("REMOVENDO IP " + row.getDestinationIp() + " POR FALTA DE ATUALIZAÇÃO");
							//row.softDelete();
							RouterService.router.getRouterTable().removeRow(row);
							RouterService.sendRouterTableNow();
							RouterService.updateRouterTableModelView();
						}
					} else {
						if(row.getUpdatedAt().until(LocalDateTime.now(), ChronoUnit.SECONDS) > 120)
							RouterService.router.getRouterTable().removeRow(row);
					}
					 * 
					 * 
					 * 
					 */
					
				}
			}
			
		}
		
	}
	
}
