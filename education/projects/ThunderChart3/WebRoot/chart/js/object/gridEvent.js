Ext.namespace("extgrid");
extgrid.headerclick= function(grid,columnIndex,e){
	alert(grid.getColumnModel().getColumnHeader(columnIndex));
}