package es.fajardo.app.utilities.excel.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;

import es.fajardo.app.utilities.excel.reader.dto.CellDTO;
import es.fajardo.app.utilities.excel.reader.dto.SheetDTO;
import es.fajardo.app.utilities.excel.reader.dto.WorkbookDTO;
import es.fajardo.app.utilities.excel.reader.enums.TipoProperitiesExcelEnum;

public class ExcelReader {

	private static ResourceBundle EXCEL_REFERENCES = ResourceBundle.getBundle("excel/managedExcel");

	/**
	 * Method with and useful example Input: Need a Map of
	 * 
	 * @param excel
	 */
	public static void reader(File excel) {

		WorkbookDTO wb = new WorkbookDTO();

		Integer totalSheets = getIntegerProperty(TipoProperitiesExcelEnum.WB_NUM_SHEETS.getPropertyValue(), null, null);

		// Sheets iteration (starts always in 1)
		for (int sheetItem = 1; sheetItem <= totalSheets; sheetItem++) {
			if (sheetItem == 1) {
				wb.setSheets(new ArrayList<SheetDTO>());
			}
			SheetDTO sheet = new SheetDTO();
			// convert index to String to use for get properties dynamically
			String sheetItemString = String.valueOf(sheetItem);
			// get the property value
			String sheetIndexString = getStringProperty(TipoProperitiesExcelEnum.SHEET_INDEX.getPropertyValue(),
					sheetItemString, null);

			// Process concrete Cell
			processConcreteCellsProperties(sheetItemString, sheet);

			// Process data row cells
			processRowCellsProperties(sheetItemString, sheet);

			wb.getSheets().add(sheet);
		}

	}

	/**
	 * @param sheetItemString
	 */
	private static void processConcreteCellsProperties(String sheetItemString, SheetDTO sheet) {
		Integer totalConcreteCells = getIntegerProperty(
				TipoProperitiesExcelEnum.SHEET_NUM_CONCRETE_CELLS.getPropertyValue(), sheetItemString, null);
		System.out.println("CONCRETE CELLS TO PROCESS: " + totalConcreteCells);
		if (totalConcreteCells > 0) {
			sheet.setCells(new ArrayList<CellDTO>());
			// Concrete cells iteration (starts always in 1)
			for (int cellItem = 1; cellItem <= totalConcreteCells; cellItem++) {
				System.out.println("Processing concrete cell " + cellItem);
				CellDTO cell = new CellDTO();
				// convert index to String to use for get properties dynamically
				String cellItemString = String.valueOf(cellItem);
				// get the property value for each key of the actual cell we are
				// iterating
				cell.setConcreteCell(Boolean.TRUE);

				cell.setCellReference(
						getStringProperty(TipoProperitiesExcelEnum.SHEET_CONCRETE_CELL_REFERENCE.getPropertyValue(),
								sheetItemString, cellItemString));

				cell.setCellType(getStringProperty(TipoProperitiesExcelEnum.SHEET_CONCRETE_CELL_TYPE.getPropertyValue(),
						sheetItemString, cellItemString));

				cell.setCellClass(
						getStringProperty(TipoProperitiesExcelEnum.SHEET_CONCRETE_CELL_CLASS.getPropertyValue(),
								sheetItemString, cellItemString));

				cell.setCellField(
						getStringProperty(TipoProperitiesExcelEnum.SHEET_CONCRETE_CELL_FIELD.getPropertyValue(),
								sheetItemString, cellItemString));

				cell.setCellMethod(
						getStringProperty(TipoProperitiesExcelEnum.SHEET_CONCRETE_CELL_METHOD.getPropertyValue(),
								sheetItemString, cellItemString));
				sheet.getCells().add(cell);
			}
			System.out.println("CONCRETE CELLS PROCESS FINISHED");
		}
	}

	/**
	 * @param sheetItemString
	 */
	private static void processRowCellsProperties(String sheetItemString, SheetDTO sheet) {
		Integer initialRow = getIntegerProperty(
				TipoProperitiesExcelEnum.SHEET_DATA_ROW_INITIAL_INDEX.getPropertyValue(), sheetItemString, null);
		if (initialRow > 0) {
			// if (CollectionUtils.isEmpty(sheet.getCells())) {
			// sheet.setCells(new ArrayList<CellDTO>());
			// }
			// Concrete cells iteration (starts always in 1)
			Integer totalCols = getIntegerProperty(TipoProperitiesExcelEnum.SHEET_DATA_ROW_NUM_CELLS.getPropertyValue(),
					sheetItemString, null);
			System.out.println("PROCESSING DATA ROWS... NUMBER OF COLS TO PROCESS FOR EACH ROW: " + totalCols);
			for (int cellItem = 1; cellItem <= totalCols; cellItem++) {
				System.out.println("DATA ROW CELL TO PROCESS: " + cellItem);
				CellDTO cell = new CellDTO();
				// convert index to String to use for get properties dynamically
				String cellItemString = String.valueOf(cellItem);
				// get the property value for each key of the actual cell we are
				// iterating
				cell.setConcreteCell(Boolean.FALSE);

				cell.setCellReference(
						getStringProperty(TipoProperitiesExcelEnum.SHEET_DATA_ROW_CELL_COL.getPropertyValue(),
								sheetItemString, cellItemString));

				cell.setCellType(getStringProperty(TipoProperitiesExcelEnum.SHEET_DATA_ROW_CELL_TYPE.getPropertyValue(),
						sheetItemString, cellItemString));

				cell.setCellClass(
						getStringProperty(TipoProperitiesExcelEnum.SHEET_DATA_ROW_CELL_CLASS.getPropertyValue(),
								sheetItemString, cellItemString));

				cell.setCellField(
						getStringProperty(TipoProperitiesExcelEnum.SHEET_DATA_ROW_CELL_FIELD.getPropertyValue(),
								sheetItemString, cellItemString));

				cell.setCellMethod(
						getStringProperty(TipoProperitiesExcelEnum.SHEET_DATA_ROW_CELL_METHOD.getPropertyValue(),
								sheetItemString, cellItemString));
				sheet.getCells().add(cell);
			}
			System.out.println("DATA ROWS PROCESS FINISHED");
		}
	}

	/**
	 * This method obtain the specific property value only for properties that
	 * not has dependence with other property
	 * 
	 * @param propertyToObtain:
	 *            the String value of the property to obtain. This String is
	 *            defined in TipoPropertiesExcelEnum.getPropertyValue
	 * @param sheet:
	 *            the number of the sheet in the property file (example, 1 for
	 *            get information of sheet1 defined in the property file, 2 for
	 *            sheet 2...). Null if no need
	 * @param cell:
	 *            the number of the cell in the property file (example, 1 for
	 *            get information of cell1 defined in the property file, 2 for
	 *            cell 2...). Null if no need
	 * @return Integer
	 * @author afajardo
	 */
	private static Integer getIntegerProperty(String propertyToObtain, String sheet, String cell) {
		return Integer.valueOf(getPropertyValue(propertyToObtain, sheet, cell));
	}

	/**
	 * This method obtain the specific String property value only for properties
	 * that not has dependence with other property
	 * 
	 * @param propertyToObtain:
	 *            the String value of the property to obtain. This String is
	 *            defined in TipoPropertiesExcelEnum.getPropertyValue
	 * @param sheet:
	 *            the number of the sheet in the property file (example, 1 for
	 *            get information of sheet1 defined in the property file, 2 for
	 *            sheet 2...). Null if no need
	 * @param cell:
	 *            the number of the cell in the property file (example, 1 for
	 *            get information of cell1 defined in the property file, 2 for
	 *            cell 2...). Null if no need
	 * @return String
	 * @author afajardo
	 */
	private static String getStringProperty(String propertyToObtain, String sheet, String cell) {
		return getPropertyValue(propertyToObtain, sheet, cell);
	}

	/**
	 * @param propertyToObtain
	 * @param sheet
	 * @param cell
	 * @return
	 */
	private static String getPropertyValue(String propertyToObtain, String sheet, String cell) {
		String propertyKey = null;

		// replace special character of the property recovered with the
		// index and get the property key
		// if (!StringUtils.isEmpty(sheet) && !StringUtils.isEmpty(cell)) {
		// propertyKey = String.format(propertyToObtain, sheet, cell);
		// } else if (!StringUtils.isEmpty(sheet)) {
		// propertyKey = String.format(propertyToObtain, sheet);
		// } else {
		// propertyKey = propertyToObtain;
		// }
		// get the property value for the property key
		String propertyValue = EXCEL_REFERENCES.getString(propertyKey);
		System.out.println(propertyKey + " = " + propertyValue);
		return propertyValue;
	}

}
