package appRouterSimulator.models;

import javax.swing.table.AbstractTableModel;

import models.RouterTableRow;
import services.RouterService;

public class RouterTableModel extends AbstractTableModel {
	private static final int COL_DESTINO = 0;
    private static final int COL_METRICA = 1;
    private static final int COL_SAIDA = 2;
    private static final int COL_CREATEDAT = 3;
    private static final int COL_UPDATEDAT = 4;
    private static RouterTableModel self;
    
	private String[] colunas = new String[]{"Destino", "Métrica", "Saída", "Data Criação", "Atualizado Em"};
	
	protected RouterTableModel() {
		
	}
	
	public static RouterTableModel getInstance() {
	      if(self == null) {
	         self = new RouterTableModel();
	      }
	      return self;
	   }
	
	@Override
	public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
	}
	
	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public int getRowCount() {
		if(RouterService.router != null)
			return RouterService.router.getRouterTable().getRows().size();
		
		return 0;
	}

	@Override
	public Object getValueAt(int row, int column) {
		
		if(RouterService.router != null) {
			RouterTableRow r = RouterService.router.getRouterTable().getRows().get(row);
	
	        if (column == COL_DESTINO) {
	            return r.getDestinationIp();
	        } else if (column == COL_METRICA) {
	            return r.getMetric();
	        } else if (column == COL_SAIDA) {
	            return r.getGatewayIp();
	        } else if (column == COL_CREATEDAT) {
	            return r.getCreatedAtFormated();
	        } else if (column == COL_UPDATEDAT) {
	        	return r.getUpdatedAtFormated();
	        }
		}
		
		return null;
		
		
	}
	
	public void updateData() {
		fireTableDataChanged();
	}
	
}
