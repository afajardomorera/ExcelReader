package es.fajardo.app.utilities.excel.reader.utils;

import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import es.fajardo.app.utilities.excel.reader.dto.CellDTO;
import es.fajardo.app.utilities.excel.reader.dto.SheetDTO;
import es.fajardo.app.utilities.excel.reader.enums.TipoProperitiesExcelEnum;

public class PropertiesUtils {

	private static ResourceBundle PROPERTIES_REPO = null;

	public static void setPropertyFile(ResourceBundle propertyFile) {
		PROPERTIES_REPO = propertyFile;
	}

	/**
	 * Get the configuration values for the data row cells
	 * 
	 * sheetItemString String: Index of the sheet to process to recover
	 * properties value defined sheet SheetDTO: DTO that represents the sheet
	 * with the configuration defined in the property file totalCols Integer:
	 * Number of total columns to get value from a data row
	 * 
	 * @return <b>void</b>
	 * 
	 * @author afajardo
	 */
	public static void getDataRowCellsConfiguredValues(String sheetItemString, SheetDTO sheet, Integer totalCols) {
		for (int cellItem = 1; cellItem <= totalCols; cellItem++) {
			System.out.println("DATA ROW CELL TO PROCESS: " + cellItem);
			CellDTO cell = new CellDTO();
			// convert index to String to use for get properties dynamically
			String cellItemString = String.valueOf(cellItem);
			// get the property value for each key of the actual cell we are
			// iterating
			cell.setConcreteCell(Boolean.FALSE);

			cell.setCellReference(getStringProperty(TipoProperitiesExcelEnum.SHEET_DATA_ROW_CELL_COL.getPropertyValue(),
					sheetItemString, cellItemString));

			cell.setCellJavaType(
					getStringProperty(TipoProperitiesExcelEnum.SHEET_DATA_ROW_CELL_JAVA_TYPE.getPropertyValue(),
							sheetItemString, cellItemString));

			cell.setCellField(getStringProperty(TipoProperitiesExcelEnum.SHEET_DATA_ROW_CELL_FIELD.getPropertyValue(),
					sheetItemString, cellItemString));

			sheet.getCells().add(cell);
		}
	}

	/**
	 * Get the configuration values for the concrete cells
	 * 
	 * sheetItemString String: Index of the sheet to process to recover
	 * properties value defined sheet SheetDTO: DTO that represents the sheet
	 * with the configuration defined in the property file totalCells Integer:
	 * Number of total concrete cells to get value from the sheet
	 * 
	 * @return <b>void</b>
	 * 
	 * @author afajardo
	 */
	public static void getConcreteCellsConfiguredValues(String sheetItemString, SheetDTO sheet, Integer totalCells) {
		for (int cellItem = 1; cellItem <= totalCells; cellItem++) {
			System.out.println("DATA ROW CELL TO PROCESS: " + cellItem);
			CellDTO cell = new CellDTO();
			// convert index to String to use for get properties dynamically
			String cellItemString = String.valueOf(cellItem);
			// get the property value for each key of the actual cell we are
			// iterating
			cell.setConcreteCell(Boolean.TRUE);

			cell.setCellReference(
					getStringProperty(TipoProperitiesExcelEnum.SHEET_CONCRETE_CELL_REFERENCE.getPropertyValue(),
							sheetItemString, cellItemString));

			cell.setCellJavaType(
					getStringProperty(TipoProperitiesExcelEnum.SHEET_CONCRETE_CELL_JAVA_TYPE.getPropertyValue(),
							sheetItemString, cellItemString));

			cell.setCellField(getStringProperty(TipoProperitiesExcelEnum.SHEET_CONCRETE_CELL_FIELD.getPropertyValue(),
					sheetItemString, cellItemString));

			sheet.getCells().add(cell);
		}
	}

	/**
	 * This method obtain the specific Integer property value only for
	 * properties that not has dependence with other property
	 * 
	 * propertyToObtain String: the String value of the property to obtain. This
	 * String is defined in TipoPropertiesExcelEnum.getPropertyValue sheet
	 * String: In a text format, the number of the sheet in the property file
	 * (example, 0 for get information of sheet0 defined in the property file, 1
	 * for sheet1...). Null if no need cell String: In a text format, the number
	 * of the cell in the property file (example, 0 for get information of cell0
	 * defined in the property file, 1 for cell1...). Null if no need
	 * 
	 * @return <b>Integer</b>: In a number format, the value of the specific
	 *         property
	 * 
	 * @author afajardo
	 */
	public static Integer getIntegerProperty(String propertyToObtain, String sheet, String cell) {
		return Integer.valueOf(getPropertyValue(propertyToObtain, sheet, cell));
	}

	/**
	 * This method obtain the specific String property value only for properties
	 * that not has dependence with other property
	 * 
	 * propertyToObtain String: the String value of the property to obtain. This
	 * String is defined in TipoPropertiesExcelEnum.getPropertyValue sheet
	 * String: In a text format, the number of the sheet in the property file
	 * (example, 0 for get information of sheet0 defined in the property file, 1
	 * for sheet1...). Null if no need cell String: In a text format, the number
	 * of the cell in the property file (example, 0 for get information of cell0
	 * defined in the property file, 1 for cell1...). Null if no need
	 * 
	 * @return <b>String</b>: In a text format, the value of the specific
	 *         property
	 * 
	 * @author afajardo
	 */
	public static String getStringProperty(String propertyToObtain, String sheet, String cell) {
		return getPropertyValue(propertyToObtain, sheet, cell);
	}

	/**
	 * Get the value from a configured property
	 * 
	 * propertyToObtain String: the String value of the property to obtain. This
	 * String is defined in TipoPropertiesExcelEnum.getPropertyValue sheet
	 * String: In a text format, the number of the sheet in the property file
	 * (example, 0 for get information of sheet0 defined in the property file, 1
	 * for sheet1...). Null if no need cell String: In a text format, the number
	 * of the cell in the property file (example, 0 for get information of cell0
	 * defined in the property file, 1 for cell1...). Null if no need
	 * 
	 * @return <b>String</b>: In a text format, the value of the specific
	 *         property
	 * 
	 * @author afajardo
	 */
	public static String getPropertyValue(String propertyToObtain, String sheet, String cell) {
		String propertyKey = null;

		// replace special character of the property recovered with the
		// index and get the property key
		if (!StringUtils.isEmpty(sheet) && !StringUtils.isEmpty(cell)) {
			propertyKey = String.format(propertyToObtain, sheet, cell);
		} else if (!StringUtils.isEmpty(sheet)) {
			propertyKey = String.format(propertyToObtain, sheet);
		} else {
			propertyKey = propertyToObtain;
		}
		// get the property value for the property key
		String propertyValue = PROPERTIES_REPO.getString(propertyKey);
		System.out.println(propertyKey + " = " + propertyValue);
		if (propertyValue != null) {
			propertyValue = propertyValue.trim();
		}
		return propertyValue;
	}
}
