package es.fajardo.app.utilities.excel.reader.dto;

import java.util.List;

public class SheetDTO {

	private Integer sheetIndex;
	private String infoClass;
	private Integer sheetInitialDataRowIndex;
	private Integer sheetLastDataRowIndex;
	private String dataRowBindingClass;
	private String dataRowBindingFieldName;
	private List<CellDTO> cells;

	public Integer getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(Integer sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	public String getInfoClass() {
		return infoClass;
	}

	public void setInfoClass(String infoClass) {
		this.infoClass = infoClass;
	}

	public Integer getSheetInitialDataRowIndex() {
		return sheetInitialDataRowIndex;
	}

	public void setSheetInitialDataRowIndex(Integer sheetInitialDataRowIndex) {
		this.sheetInitialDataRowIndex = sheetInitialDataRowIndex;
	}

	public Integer getSheetLastDataRowIndex() {
		return sheetLastDataRowIndex;
	}

	public void setSheetLastDataRowIndex(Integer sheetLastDataRowIndex) {
		this.sheetLastDataRowIndex = sheetLastDataRowIndex;
	}

	public String getDataRowBindingClass() {
		return dataRowBindingClass;
	}

	public void setDataRowBindingClass(String dataRowBindingClass) {
		this.dataRowBindingClass = dataRowBindingClass;
	}

	public List<CellDTO> getCells() {
		return cells;
	}

	public void setCells(List<CellDTO> cells) {
		this.cells = cells;
	}

	public String getDataRowBindingFieldName() {
		return dataRowBindingFieldName;
	}

	public void setDataRowBindingFieldName(String dataRowBindingFieldName) {
		this.dataRowBindingFieldName = dataRowBindingFieldName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SheetDTO [sheetIndex=");
		builder.append(sheetIndex);
		builder.append(", infoClass=");
		builder.append(infoClass);
		builder.append(", sheetInitialDataRowIndex=");
		builder.append(sheetInitialDataRowIndex);
		builder.append(", sheetLastDataRowIndex=");
		builder.append(sheetLastDataRowIndex);
		builder.append(", dataRowBindingClass=");
		builder.append(dataRowBindingClass);
		builder.append(", dataRowBindingFieldName=");
		builder.append(dataRowBindingFieldName);
		builder.append(", cells=");
		builder.append(cells);
		builder.append("]");
		return builder.toString();
	}

}
