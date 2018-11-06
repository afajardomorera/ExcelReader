package es.fajardo.app.utilities.excel.reader.dto;

import java.util.List;

public class WorkbookDTO {

	private List<SheetDTO> sheets;

	public List<SheetDTO> getSheets() {
		return sheets;
	}

	public void setSheets(List<SheetDTO> sheets) {
		this.sheets = sheets;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkbooDTO [sheets=");
		builder.append(sheets);
		builder.append("]");
		return builder.toString();
	}
}
