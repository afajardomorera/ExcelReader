package es.fajardo.app.utilities.excel.reader.enums;

public enum TipoProperitiesExcelEnum {

	SHEEET_INDEX_TO_PROCESS("sheet.index.to.process"), //
	SHEET_INDEX("sheet%s.index"), //
	SHEET_NUM_CONCRETE_CELLS("sheet%s.num.concrete.cells"), //
	SHEET_CONCRETE_CELL_REFERENCE("sheet%s.cell%s.reference"), //
	SHEET_CONCRETE_CELL_JAVA_TYPE("sheet%s.cell%s.java.type"), //
	SHEET_CONCRETE_CELL_FIELD("sheet%s.cell%s.binding.field"), //
	SHEET_INFO_CLASS("sheet%s.info.class"), //
	SHEET_DATA_ROW_INITIAL_INDEX("sheet%s.data.row.initial.index"), //
	SHEET_DATA_ROW_LAST_INDEX("sheet%s.data.row.last.index"), //
	SHEET_DATA_ROW_BINDING_CLASS("sheet%s.data.row.binding.class"), //
	SHEET_DATA_ROW_BINDING_FIELD_NAME("sheet%s.data.row.binding.field.name"), //
	SHEET_DATA_ROW_NUM_CELLS("sheet%s.data.row.num.cells"), //
	SHEET_DATA_ROW_CELL_COL("sheet%s.data.row.cell%s.col"), //
	SHEET_DATA_ROW_CELL_FIELD("sheet%s.data.row.cell%s.binding.field"), //
	SHEET_DATA_ROW_CELL_JAVA_TYPE("sheet%s.data.row.cell%s.java.type");

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
