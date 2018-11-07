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

/**
 * <b>Utility Tool to process an Excel and extract the information to the
 * specific objects needed</b>
 * <p>
 * Type of Excels that could be processed: <br>
 * - One or more sheets <br>
 * - Each sheet has or concrete cells to process (know the complete cell
 * reference), or data rows of a table to process (know at less initial row, if
 * don't know the last row the tool process data rows until end of sheet), or
 * combination of concrete cells and data row table.
 * <p>
 * Use method:
 * <p>
 * <b>1.- Define an object for each sheet to process with needed fields.</b> If
 * you want to set the values from each row of a table, you can specify a
 * different object for this data Example: In a sheet I have 3 concrete cells
 * (report date, author, version), in a header section, and a table with 3
 * columns (id, name, counter)
 * <p>
 * I create a TableDTO with the following information:<br>
 * -- BigInteger id<br>
 * -- String name<br>
 * -- BigInteger counter
 * <p>
 * I create a ReportDTO with the following information: <br>
 * -- Date reportDate <br>
 * -- String author <br>
 * -- String version <br>
 * -- List&lt;TableDTO&gt; rows<br>
 * <p>
 * <b>2.- In your application, define a property file with the following
 * information</b> (using the example above): <br>
 * 
 * # Sheet indexes to process (values with comma separates)<br>
 * sheet.index.to.process=0,1
 * <p>
 * For each sheet: <br>
 * #Sheet index in the Workbook <br>
 * #sheetX partial property must be the same that sheetX.index value
 * informed<br>
 * <b>sheet0.index=0</b>
 * <p>
 * #Class for sheet data sheet <br>
 * <b>sheet0.info.class = </b>ReportDTO
 * <p>
 * # Number of concrete cells. If don't have concrete cells, the value has to be
 * 0 <br>
 * <b>sheet0.num.concrete.cells=</b>3
 * <p>
 * # For each concrete cell (required if "Number of concrete cells" is greater
 * than 0) If the cell is a combined cell, the full reference is the first coll
 * and row of the combined cell<br>
 * <b>sheet0.cell1.reference=</b>B4<br>
 * <b>sheet0.cell1.java.type=</b>Date<br>
 * <b>sheet0.cell1.binding.field=</b>reportDate
 * <p>
 * <b>sheet0.cell2.reference=</b>F3<br>
 * <b>sheet0.cell2.java.type=</b>String<br>
 * <b>sheet0.cell2.binding.field=</b>author
 * <p>
 * <b>sheet0.cell3.reference=</b>F4<br>
 * <b>sheet0.cell3.java.type=</b>String<br>
 * <b>sheet0.cell3.binding.field=</b>version
 * <p>
 * # First row index with data to process. If don't have rows to process, the
 * value has to be 0<br>
 * <b>sheet0.data.row.initial.index=</b>7
 * <p>
 * # Last row index with data to process. If you don't know, set 0 to this value
 * and the application process until last row of the sheet<br>
 * <b>sheet0.data.row.last.index=</b>0
 * <p>
 * # Class Binding for data row<br>
 * # If you want to use different class to binding data rows information, you
 * must to have <br>
 * # a List typed with the object you specify here in the object you set in
 * sheetX.info.class property<br>
 * <b>sheet0.data.row.binding.class=</b>TableDTO<br>
 * #No need to inform next property if the binding class is the same that
 * sheetX.info.class<br>
 * <b>sheet0.data.row.binding.field.name=</b>rows
 * <p>
 * # Number of columns for each data row <br>
 * <b>sheet0.data.row.num.cells=</b>5
 * <p>
 * # Columns Configuration<br>
 * <b>sheet0.data.row.cell1.col=</b>A<br>
 * <b>sheet0.data.row.cell1.java.type=</b>BigInteger<br>
 * <b>sheet0.data.row.cell1.binding.field=</b>id
 * <p>
 * <b>sheet0.data.row.cell2.col=</b>B<br>
 * <b>sheet0.data.row.cell2.java.type=</b>String<br>
 * <b>sheet0.data.row.cell2.binding.field=</b>name
 * <p>
 * <b>sheet0.data.row.cell3.col=</b>C<br>
 * <b>sheet0.data.row.cell3.java.type=</b>BigInteger<br>
 * <b>sheet0.data.row.cell3.binding.field=</b>counter
 * <p>
 * <i><b>#----------------------------------------------------------------- <br>
 * #Java Types admitted in version (Beta) 0.0.1<br>
 * #----------------------------------------------------------------- </b><br>
 * <b>#Numeric: </b>Integer, BigInteger, BigDecimal, Double, Long --&gt; This
 * use CellTypes.NUMERIC <br>
 * <b>#Text: </b>String --&gt; This use CellTypes.STRING <br>
 * <b>#Dates: </b>Date (java.util) --&gt; This use CellTypes.NUMERIC<br>
 * <b>#Logic: </b>Boolean --&gt; This use CellTypes.BOOLEAN</i><br>
 * <p>
 * <b>3.- From a class of your application:</b><br>
 * -- Get the property file defined in section 2 of this documentation like a
 * ResourceBundle
 * <p>
 * -- Define a Map&lt;String, Object&gt;, where String is the SimpleName of one
 * of the objects you define in the property file as binding class, and Object
 * is an instance (could be an empty instance) of that Object<br>
 * Example:<br>
 * <i>Map&lt;String, Object&gt; mapBindingObjects = new HashMap&lt;&gt;();<br>
 * mapBindingObjects.put("ReportDTO"; new ReportDTO());<br>
 * mapBindingObjects.put("TableDTO"; new TableDTO());</i>
 * <p>
 * -- Define the map to get the result. It will be a Map&lt;Integer,
 * List&lt;Object&gt;&gt; where Integer is the sheet index and the list Object
 * is a List with instance of Object of the type you define in sheet0.info.class
 * <p>
 * -- Invoke the reader!!!<br>
 * <i>Map&lt;Integer, List&lt;Object&gt;&gt; resultMap =
 * ExcelReader.reader(excelFile, mapBindingObjects, resourceBundle);</i>
 * <p>
 * -- Finally, if don't receive errors, you will have a map with a list of
 * objects that has the excel information<br>
 * <i>List&lt;ReportDTO&gt; sheet0 = new ArrayList&lt;&gt;();<br>
 * for (Object obj : resultMap.get(0)) {<br>
 * sheet0.add((ReportDTO) obj);<br>
 * }</i>
 * 
 * @author afajardo
 *
 */
public class ExcelReader {

	private static ResourceBundle PROPERTIES_REPO = null;

	/**
	 * Read an Excel file and set the values read into the distinct object
	 * specified in the input map, using the configuration set in the resource
	 * bundle
	 * 
	 * @param excel
	 *            File: The Excel File to process and extract information
	 * @param bindingObjects
	 *            Map&lt;String, Object&gt;: Map that contains the objects where
	 *            set the values. Key is the SimpleName of the Object, Value is
	 *            an instance (could be empty instance) of the object
	 * @param resource
	 *            ResourceBundle: The ResourceBundle that has the configuration
	 *            properties
	 * 
	 * @return <b>Map&lt;Integer, List&lt;Object&gt;&gt;</b>: The Map with the
	 *         objects. Key is the sheet index. Value is a list with the objects
	 *         referenced in input map param with the values set as they are
	 *         defined in the configuration property indicated as ResourceBundle
	 * 
	 * @author afajardo
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

		String sheetIndexToProcess = PROPERTIES_REPO
				.getString(TipoProperitiesExcelEnum.SHEEET_INDEX_TO_PROCESS.getPropertyValue());
		String[] differentIndexes = sheetIndexToProcess.split(",");
		List<Integer> sheetIndexes = new ArrayList<>();
		for (String index : differentIndexes) {
			sheetIndexes.add(Integer.valueOf(index));
		}

		// Sheets iteration
		for (Integer sheetItem : sheetIndexes) {
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
			System.out.println("Sheet: " + sheet.getSheetIndex());
			if (CollectionUtils.isNotEmpty(sheet.getCells())) {
				for (CellDTO cell : sheet.getCells()) {
					System.out.println(cell.toString());
				}
			}
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
	 * @param sheet
	 *            SheetDTO: DTO that represents the sheet with the configuration
	 *            defined in the property file
	 * @param sheetItemStringIndex
	 *            String: Index of the sheet to process to recover properties
	 *            value defined
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
	 * @param sheetExcel
	 *            XSSFSheet: The Excel sheet with the values
	 * @param sheetClass
	 *            Class: The object class
	 * @param sheetObject
	 *            Object: The object with the field where set the values
	 * @param cell
	 *            CellDTO: DTO that represents the cell with the configuration
	 *            defined in the property file
	 * @param ref
	 *            CellReference: Cell reference to get value from the sheetExcel
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
			} else if (cell.getCellJavaType().equals(BigDecimal.class.getSimpleName())) {
				BigDecimal value = new BigDecimal(
						String.valueOf(sheetExcel.getRow(ref.getRow()).getCell(ref.getCol()).getNumericCellValue()));
				field.set(sheetObject, value);
			} else if (cell.getCellJavaType().equals(Integer.class.getSimpleName())) {
				Integer value = new Double(
						String.valueOf(sheetExcel.getRow(ref.getRow()).getCell(ref.getCol()).getNumericCellValue()))
								.intValue();
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
	 * @param sheetItemString
	 *            String: Index of the sheet to process to recover properties
	 *            value defined
	 * @param sheet
	 *            SheetDTO: DTO that represents the sheet with the configuration
	 *            defined in the property file
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
	 * @param sheetItemString
	 *            String: Index of the sheet to process to recover properties
	 *            value defined
	 * @param sheet
	 *            SheetDTO: DTO that represents the sheet with the configuration
	 *            defined in the property file
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
