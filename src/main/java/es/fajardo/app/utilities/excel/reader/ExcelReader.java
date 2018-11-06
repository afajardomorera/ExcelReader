package es.fajardo.app.utilities.excel.reader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import es.fajardo.app.utilities.excel.reader.dto.CellDTO;
import es.fajardo.app.utilities.excel.reader.dto.SheetDTO;
import es.fajardo.app.utilities.excel.reader.dto.WorkbookDTO;
import es.fajardo.app.utilities.excel.reader.enums.TipoProperitiesExcelEnum;
import es.fajardo.app.utilities.excel.reader.utils.PropertiesUtils;

public class ExcelReader {

	private static ResourceBundle PROPERTIES_REPO = null;

	/**
	 * Method with and useful example
	 * 
	 * @param excel
	 */
	public static Map<Integer, List<Object>> reader(File excel, Map<String, Object> bindingObjects,
			ResourceBundle resource) throws Exception {

		PROPERTIES_REPO = resource;

		PropertiesUtils.setPropertyFile(PROPERTIES_REPO);

		WorkbookDTO wbDTO = new WorkbookDTO();

		XSSFWorkbook wbExcel = null;
		try {
			wbExcel = new XSSFWorkbook(excel);
		} catch (InvalidFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Integer totalSheets = wbExcel.getNumberOfSheets();

		// Sheets iteration (starts always in 1)
		for (int sheetItem = 0; sheetItem < totalSheets; sheetItem++) {
			if (CollectionUtils.isEmpty(wbDTO.getSheets())) {
				wbDTO.setSheets(new ArrayList<SheetDTO>());
			}
			SheetDTO sheet = new SheetDTO();
			// convert index to String to use for get properties dynamically
			String sheetItemString = String.valueOf(sheetItem);

			setSheetConfiguredValues(sheet, sheetItemString);

			// Process concrete Cell
			processConcreteCellsProperties(sheetItemString, sheet);

			// Process data row cells
			processRowCellsProperties(sheetItemString, sheet);

			// Add the processed sheet to the workbook DTO
			wbDTO.getSheets().add(sheet);
		}

		// For only util for console log
		int i = 1;
		for (SheetDTO sheet : wbDTO.getSheets()) {
			System.out.println("Sheet: " + i);
			if (CollectionUtils.isNotEmpty(sheet.getCells())) {
				for (CellDTO cell : sheet.getCells()) {
					System.out.println(cell.toString());
				}
			}
			i++;
		}

		// Initialization of the map to return
		Map<Integer, List<Object>> returnMap = new HashMap<>();
		try {
			if (CollectionUtils.isNotEmpty(wbDTO.getSheets())) {
				// For each sheet, get the index and start the binding
				for (SheetDTO sheet : wbDTO.getSheets()) {
					System.out.println("Processing sheet " + sheet.getSheetIndex());
					if (returnMap.get(sheet.getSheetIndex()) == null) {
						returnMap.put(sheet.getSheetIndex(), new ArrayList<Object>());
					}
					XSSFSheet sheetExcel = wbExcel.getSheetAt(sheet.getSheetIndex());
					Class<?> sheetClass = bindingObjects.get(sheet.getInfoClass()).getClass();
					if (!sheetClass.getSimpleName().equals(sheet.getInfoClass())) {
						throw new Exception(
								"There is no object in the input map with the key name " + sheet.getInfoClass());
					}

					/*
					 * If sheet.infoClass is the same that
					 * sheet.dataRowBindingClass create an instance of the
					 * specific object for each rows iteration, including
					 * concrete cells values for each object
					 * 
					 * If sheet.infoClass is different than
					 * sheet.dataRowBindingClass, data row processing add the
					 * value for the specific object and, at the end of process,
					 * add this list to the list property in the sheet.infoClass
					 */

					// Cells processing and set value for binding fields
					if (CollectionUtils.isNotEmpty(sheet.getCells())) {
						Integer lastRowIndex = sheet.getSheetLastDataRowIndex();
						if (lastRowIndex == 0) {
							lastRowIndex = sheetExcel.getLastRowNum();
						}
						// get the information of the configured concrete
						// cells
						// in the property file
						Integer rowIndex = sheet.getSheetInitialDataRowIndex();
						if (rowIndex == null || rowIndex < 1) {
							rowIndex = 1;
						}
						if (sheet.getInfoClass().equals(sheet.getDataRowBindingClass())) {
							for (int index = rowIndex; index <= lastRowIndex; index++) {
								Object sheetObject = sheetClass.newInstance();
								for (CellDTO cell : sheet.getCells()) {
									CellReference ref = null;
									if (cell.isConcreteCell()) {
										ref = new CellReference(cell.getCellReference());
									} else {
										// Processing of data row cells
										ref = new CellReference(cell.getCellReference().concat(String.valueOf(index)));
									}
									setFieldValue(sheetExcel, sheetClass, sheetObject, cell, ref);
								}
								// add the object with its values to the return
								// map
								returnMap.get(sheet.getSheetIndex()).add(sheetObject);
							}
						} else {
							if (StringUtils.isEmpty(sheet.getDataRowBindingClass())) {
								throw new Exception("Must specify a field name in sheet" + sheet.getSheetIndex()
										+ ".data.row.binding.field.name property");
							}
							// First, process concrete cells and add value to
							// the object specify in sheetX.info.class
							Object sheetObject = sheetClass.newInstance();
							for (CellDTO cell : sheet.getCells()) {
								CellReference ref = null;
								if (cell.isConcreteCell()) {
									ref = new CellReference(cell.getCellReference());
									setFieldValue(sheetExcel, sheetClass, sheetObject, cell, ref);
								}

							}
							// Then, create an instance of the
							// dataRowBindingClass and process excel
							Class<?> listFieldClass = bindingObjects.get(sheet.getDataRowBindingClass()).getClass();
							List<Object> fieldList = new ArrayList<>();
							for (int index = rowIndex; index <= lastRowIndex; index++) {
								Object fieldObject = listFieldClass.newInstance();
								for (CellDTO cell : sheet.getCells()) {
									CellReference ref = null;
									if (!cell.isConcreteCell()) {
										ref = new CellReference(cell.getCellReference().concat(String.valueOf(index)));
										setFieldValue(sheetExcel, listFieldClass, fieldObject, cell, ref);
									}
								}
								fieldList.add(fieldObject);
							}
							// set the field list objects to the list attribute
							// of sheet Object
							Field field = sheetClass.getDeclaredField(sheet.getDataRowBindingFieldName());
							field.setAccessible(true);
							field.set(sheetObject, fieldList);
							// add the object with its values to the return
							// map
							returnMap.get(sheet.getSheetIndex()).add(sheetObject);
						}
					}
				}
			}
			return returnMap;
		} catch (InvalidFormatException | IOException | InstantiationException | IllegalAccessException
				| NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Set the configured values to the processing SheetDTO
	 * 
	 * @param <b>sheet
	 *            SheetDTO</b>: DTO that represents the sheet with the
	 *            configuration defined in the property file
	 * @param <b>sheetItemStringIndex
	 *            String</b>: Index of the sheet to process to recover
	 *            properties value defined
	 * @return <b>void</b>
	 * 
	 * @author afajardo
	 */
	private static void setSheetConfiguredValues(SheetDTO sheet, String sheetItemString) {
		// get the sheet index to process
		sheet.setSheetIndex(PropertiesUtils.getIntegerProperty(TipoProperitiesExcelEnum.SHEET_INDEX.getPropertyValue(),
				sheetItemString, null));

		// get the class for binding general sheet information
		sheet.setInfoClass(PropertiesUtils.getStringProperty(
				TipoProperitiesExcelEnum.SHEET_INFO_CLASS.getPropertyValue(), sheetItemString, null));

		// get the class for binding data row sheet information
		sheet.setDataRowBindingClass(PropertiesUtils.getStringProperty(
				TipoProperitiesExcelEnum.SHEET_DATA_ROW_BINDING_CLASS.getPropertyValue(),
				String.valueOf(sheet.getSheetIndex()), null));

		// get the field name of type List of Object for binding data row sheet
		// information
		try {
			sheet.setDataRowBindingFieldName(PropertiesUtils.getStringProperty(
					TipoProperitiesExcelEnum.SHEET_DATA_ROW_BINDING_FIELD_NAME.getPropertyValue(),
					String.valueOf(sheet.getSheetIndex()), null));
		} catch (MissingResourceException e) {
			sheet.setDataRowBindingFieldName(null);
		}

		// get initial data row index
		sheet.setSheetInitialDataRowIndex(PropertiesUtils.getIntegerProperty(
				TipoProperitiesExcelEnum.SHEET_DATA_ROW_INITIAL_INDEX.getPropertyValue(),
				String.valueOf(sheet.getSheetIndex()), null));

		// get last data row index
		sheet.setSheetLastDataRowIndex(PropertiesUtils.getIntegerProperty(
				TipoProperitiesExcelEnum.SHEET_DATA_ROW_LAST_INDEX.getPropertyValue(),
				String.valueOf(sheet.getSheetIndex()), null));
	}

	/**
	 * Set the excel value, from the specific cell of the specific excel sheet,
	 * to the specific field of the specific object.
	 * 
	 * @param <b>sheetExcel
	 *            XSSFSheet</b>: The Excel sheet with the values
	 * @param <b>sheetClass
	 *            Class</b>: The object class
	 * @param <b>sheetObject
	 *            Object</b>: The object with the field where set the values
	 * @param <b>cell
	 *            CellDTO</b>: DTO that represents the cell with the
	 *            configuration defined in the property file
	 * @param <b>ref
	 *            CellReference</b>: Cell reference to get value from the
	 *            sheetExcel
	 * @return <b>void</b>
	 * @throws NoSuchFieldException
	 * 
	 * @author afajardo
	 */
	private static void setFieldValue(XSSFSheet sheetExcel, Class<?> sheetClass, Object sheetObject, CellDTO cell,
			CellReference ref) throws NoSuchFieldException {
		try {
			Field field = sheetClass.getDeclaredField(cell.getCellField());
			field.setAccessible(true);
			System.out
					.println("Processing Field [" + field.getName() + "] + JavaType [" + cell.getCellJavaType() + "]");
			if (cell.getCellJavaType().equals(Boolean.class.getSimpleName())) {
				field.set(sheetObject, sheetExcel.getRow(ref.getRow()).getCell(ref.getCol()).getBooleanCellValue());
			} else if (cell.getCellJavaType().equals(BigInteger.class.getSimpleName())) {
				BigInteger value = new BigDecimal(
						String.valueOf(sheetExcel.getRow(ref.getRow()).getCell(ref.getCol()).getNumericCellValue()))
								.toBigInteger();
				field.set(sheetObject, value);
			} else if (cell.getCellJavaType().equals(Double.class.getSimpleName())) {
				Double value = new Double(
						String.valueOf(sheetExcel.getRow(ref.getRow()).getCell(ref.getCol()).getNumericCellValue()));
				field.set(sheetObject, value);
			} else if (cell.getCellJavaType().equals(Long.class.getSimpleName())) {
				Long value = new Long(
						String.valueOf(sheetExcel.getRow(ref.getRow()).getCell(ref.getCol()).getNumericCellValue()));
				field.set(sheetObject, value);
			} else if (cell.getCellJavaType().equals(Date.class.getSimpleName())) {
				field.set(sheetObject, sheetExcel.getRow(ref.getRow()).getCell(ref.getCol()).getDateCellValue());
			} else if (cell.getCellJavaType().equals(String.class.getSimpleName())) {
				field.set(sheetObject, sheetExcel.getRow(ref.getRow()).getCell(ref.getCol()).getStringCellValue());
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Process concrete cells defined
	 * 
	 * @param <b>sheetItemString
	 *            String</b>: Index of the sheet to process to recover
	 *            properties value defined
	 * @param <b>sheet
	 *            SheetDTO</b>: DTO that represents the sheet with the
	 *            configuration defined in the property file
	 * @return <b>void</b>
	 * 
	 * @author afajardo
	 */
	private static void processConcreteCellsProperties(String sheetItemString, SheetDTO sheet) {
		Integer totalConcreteCells = PropertiesUtils.getIntegerProperty(
				TipoProperitiesExcelEnum.SHEET_NUM_CONCRETE_CELLS.getPropertyValue(), sheetItemString, null);
		System.out.println("CONCRETE CELLS TO PROCESS: " + totalConcreteCells);
		if (totalConcreteCells > 0) {
			sheet.setCells(new ArrayList<CellDTO>());
			// Concrete cells iteration (starts always in 1)
			PropertiesUtils.getConcreteCellsConfiguredValues(sheetItemString, sheet, totalConcreteCells);
			System.out.println("CONCRETE CELLS PROCESS FINISHED");
		}
	}

	/**
	 * Process data row cells defined
	 * 
	 * @param <b>sheetItemString
	 *            String</b>: Index of the sheet to process to recover
	 *            properties value defined
	 * @param <b>sheet
	 *            SheetDTO</b>: DTO that represents the sheet with the
	 *            configuration defined in the property file
	 * @return <b>void</b>
	 * 
	 * @author afajardo
	 */
	private static void processRowCellsProperties(String sheetItemString, SheetDTO sheet) {
		Integer initialRow = PropertiesUtils.getIntegerProperty(
				TipoProperitiesExcelEnum.SHEET_DATA_ROW_INITIAL_INDEX.getPropertyValue(), sheetItemString, null);
		sheet.setSheetInitialDataRowIndex(initialRow);
		if (initialRow > 0) {
			if (CollectionUtils.isEmpty(sheet.getCells())) {
				sheet.setCells(new ArrayList<CellDTO>());
			}
			// Concrete cells iteration (starts always in 1)
			Integer totalCols = PropertiesUtils.getIntegerProperty(
					TipoProperitiesExcelEnum.SHEET_DATA_ROW_NUM_CELLS.getPropertyValue(), sheetItemString, null);
			System.out.println("PROCESSING DATA ROWS... NUMBER OF COLS TO PROCESS FOR EACH ROW: " + totalCols);
			PropertiesUtils.getDataRowCellsConfiguredValues(sheetItemString, sheet, totalCols);
			System.out.println("DATA ROWS PROCESS FINISHED");
		}
	}

}
