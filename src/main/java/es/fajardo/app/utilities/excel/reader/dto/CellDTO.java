package es.fajardo.app.utilities.excel.reader.dto;

public class CellDTO {

	private String cellReference;
	private Integer cellType;
	private String cellClass;
	private String cellField;
	private String cellMethod;
	private String cellJavaType;
	private boolean concreteCell;

	public String getCellReference() {
		return cellReference;
	}

	public void setCellReference(String cellReference) {
		this.cellReference = cellReference;
	}

	public Integer getCellType() {
		return cellType;
	}

	public void setCellType(Integer cellType) {
		this.cellType = cellType;
	}

	public String getCellClass() {
		return cellClass;
	}

	public void setCellClass(String cellClass) {
		this.cellClass = cellClass;
	}

	public String getCellField() {
		return cellField;
	}

	public void setCellField(String cellField) {
		this.cellField = cellField;
	}

	public String getCellMethod() {
		return cellMethod;
	}

	public void setCellMethod(String cellMethod) {
		this.cellMethod = cellMethod;
	}

	public boolean isConcreteCell() {
		return concreteCell;
	}

	public void setConcreteCell(boolean concreteCell) {
		this.concreteCell = concreteCell;
	}

	public String getCellJavaType() {
		return cellJavaType;
	}

	public void setCellJavaType(String cellJavaType) {
		this.cellJavaType = cellJavaType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CellDTO [cellReference=");
		builder.append(cellReference);
		builder.append(", cellType=");
		builder.append(cellType);
		builder.append(", cellClass=");
		builder.append(cellClass);
		builder.append(", cellField=");
		builder.append(cellField);
		builder.append(", cellMethod=");
		builder.append(cellMethod);
		builder.append(", cellJavaType=");
		builder.append(cellJavaType);
		builder.append(", concreteCell=");
		builder.append(concreteCell);
		builder.append("]");
		return builder.toString();
	}
}
