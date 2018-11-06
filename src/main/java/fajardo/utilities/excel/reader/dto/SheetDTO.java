package fajardo.utilities.excel.reader.dto;

import java.util.List;

public class SheetDTO {

	private List<CellDTO> cells;

	public List<CellDTO> getCells() {
		return cells;
	}

	public void setCells(List<CellDTO> cells) {
		this.cells = cells;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SheetDTO [cells=");
		builder.append(cells);
		builder.append("]");
		return builder.toString();
	}
}
