package fajardo.utilities.excel.reader.enums;

public enum TipoProperitiesExcelEnum {

	SHEET_INDEX_INDICADORES("indicadores.sheet.index"), //
	SHEET_INDEX_DETALLES("detalles.sheet.index"), //
	//
	INDICADORES_FECHA_REF("indicadores.celda.fecha"), //
	INDICADORES_FECHA_DESDE_REF("indicadores.celda.fecha.desde"), //
	INDICADORES_FECHA_HASTA_REF("indicadores.celda.fecha.hasta"), //
	//
	INDICADORES_DATA_ROW_START_INDEX("indicadores.fila.inicio.datos"), //
	INDICADORES_DATA_COL_CENTRO("indicadores.columna.centro"), //
	INDICADORES_DATA_COL_LINEA("indicadores.columna.linea"), //
	INDICADORES_DATA_COL_CATEGORIA("indicadores.columna.categoria"), //
	INDICADORES_DATA_COL_CONTRATO("indicadores.columna.contrato"), //
	INDICADORES_DATA_COL_HC("indicadores.columna.hc"),
	//
	DETALLES_DATA_ROW_START_INDEX("detalles.fila.inicio.datos"), //
	DETALLES_DATA_COL_NUM_EMPLEADO("detalles.columna.num.empleado"), //
	DETALLES_DATA_COL_NOMBRE("detalles.columna.nombre"), //
	DETALLES_DATA_COL_APELLIDOS("detalles.columna.apellidos"), //
	DETALLES_DATA_COL_CATEGORIA("detalles.columna.categoria"), //
	DETALLES_DATA_COL_CARGO("detalles.columna.cargo"), //
	DETALLES_DATA_COL_HORARIO("detalles.columna.horario"), //
	DETALLES_DATA_COL_CONTRATO("detalles.columna.contrato"), //
	DETALLES_DATA_COL_FECHA_INCORPORACION("detalles.columna.fecha.incorporacion"), //
	DETALLES_DATA_COL_FECHA_BAJA("detalles.columna.fecha.baja"), //
	DETALLES_DATA_COL_CENTRO("detalles.columna.centro"), //
	DETALLES_DATA_COL_LINEA("detalles.columna.linea"), //
	DETALLES_DATA_COL_CELDA("detalles.columna.celda"), //
	DETALLES_DATA_COL_TECNOLOGIA("detalles.columna.tecnologia"),
	//
	//
	WB_NUM_SHEETS("wb.num.sheets"), //
	SHEET_INDEX("sheet%s.index"), //
	SHEET_NUM_CONCRETE_CELLS("sheet%s.num.concrete.cells"), //
	SHEET_CONCRETE_CELL_REFERENCE("sheet%s.cell%s.reference"), //
	SHEET_CONCRETE_CELL_TYPE("sheet%s.cell%s.type"), //
	SHEET_CONCRETE_CELL_CLASS("sheet%s.cell%s.class"), //
	SHEET_CONCRETE_CELL_FIELD("sheet%s.cell%s.binding.field"), //
	SHEET_CONCRETE_CELL_METHOD("sheet%s.cell%s.binding.method"), //
	SHEET_DATA_ROW_INITIAL_INDEX("sheet%s.data.row.initial.index"), //
	SHEET_DATA_ROW_NUM_CELLS("sheet%s.data.row.num.cells"), //
	SHEET_DATA_ROW_CELL_COL("sheet%s.data.row.cell%s.col"), //
	SHEET_DATA_ROW_CELL_TYPE("sheet%s.data.row.cell%s.type"), //
	SHEET_DATA_ROW_CELL_CLASS("sheet%s.data.row.cell%s.class"), //
	SHEET_DATA_ROW_CELL_FIELD("sheet%s.data.row.cell%s.binding.field"), //
	SHEET_DATA_ROW_CELL_METHOD("sheet%s.data.row.cell%s.binding.method");

	private String propertyValue;

	private TipoProperitiesExcelEnum(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
}
