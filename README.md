# ExcelReader
Java JAR Utility Tool to process an Excel and extract the information to the specific objects needed
Type of Excels that could be processed: 
- One or more sheets 
- Each sheet has or concrete cells to process (know the complete cell reference), or data rows of a table to process (know at less initial row, if don't know the last row the tool process data rows until end of sheet), or combination of concrete cells and data row table.

Use method:

1.- Define an object for each sheet to process with needed fields. If you want to set the values from each row of a table, you can specify a different object for this data Example: In a sheet I have 3 concrete cells (report date, author, version), in a header section, and a table with 3 columns (id, name, counter)

I create a TableDTO with the following information:
-- BigInteger id
-- String name
-- BigInteger counter

I create a ReportDTO with the following information: 
-- Date reportDate 
-- String author 
-- String version 
-- List<TableDTO> rows
2.- In your application, define a property file with the following information (using the example above): 
For each sheet: 

#Sheet index in the Workbook 
#sheetX partial property must be the same that sheetX.index value informed
sheet0.index=0

#Class for sheet data sheet 
sheet0.info.class = ReportDTO

# Number of concrete cells. If don't have concrete cells, the value has to be 0 
sheet0.num.concrete.cells=3

# For each concrete cell (required if "Number of concrete cells" is greater than 0) If the cell is a combined cell, the full reference is the first coll and row of the combined cell
sheet0.cell1.reference=B4
sheet0.cell1.java.type=Date
sheet0.cell1.binding.field=reportDate

sheet0.cell2.reference=F3
sheet0.cell2.java.type=String
sheet0.cell2.binding.field=author

sheet0.cell3.reference=F4
sheet0.cell3.java.type=String
sheet0.cell3.binding.field=version

# First row index with data to process. If don't have rows to process, the value has to be 0
sheet0.data.row.initial.index=7

# Last row index with data to process. If you don't know, set 0 to this value and the application process until last row of the sheet
sheet0.data.row.last.index=0

# Class Binding for data row
# If you want to use different class to binding data rows information, you must to have a List typed with the object you specify here in the object you set in sheetX.info.class property
sheet0.data.row.binding.class=TableDTO
# No need to inform next property if the binding class is the same that sheetX.info.class
sheet0.data.row.binding.field.name=rows

# Number of columns for each data row 
sheet0.data.row.num.cells=5

# Columns Configuration
sheet0.data.row.cell1.col=A
sheet0.data.row.cell1.java.type=BigInteger
sheet0.data.row.cell1.binding.field=id

sheet0.data.row.cell2.col=B
sheet0.data.row.cell2.java.type=String
sheet0.data.row.cell2.binding.field=name

sheet0.data.row.cell3.col=C
sheet0.data.row.cell3.java.type=BigInteger
sheet0.data.row.cell3.binding.field=counter

//----------------------------------------------------------------- 
//Java Types admitted in version (Beta) 0.0.1
//----------------------------------------------------------------- 
//Numeric: Integer, BigInteger, BigDecimal, Double, Long --> This use CellTypes.NUMERIC 
//Text: String --> This use CellTypes.STRING 
//Dates: Date (java.util) --> This use CellTypes.NUMERIC
//Logic: Boolean --> This use CellTypes.BOOLEAN

3.- From a class of your application:
-- Get the property file defined in section 2 of this documentation like a ResourceBundle

-- Define a Map<String, Object>, where String is the SimpleName of one of the objects you define in the property file as binding class, and Object is an instance (could be an empty instance) of that Object
Example:
Map<String, Object> mapBindingObjects = new HashMap<>();
mapBindingObjects.put("ReportDTO"; new ReportDTO());
mapBindingObjects.put("TableDTO"; new TableDTO());

-- Define the map to get the result. It will be a Map<Integer, List<Object>> where Integer is the sheet index and the list Object is a List with instance of Object of the type you define in sheet0.info.class

-- Invoke the reader!!!
Map<Integer, List<Object>> resultMap = ExcelReader.reader(excelFile, mapBindingObjects, resourceBundle);

-- Finally, if don't receive errors, you will have a map with a list of objects that has the excel information
List<ReportDTO> sheet0 = new ArrayList<>();
for (Object obj : resultMap.get(0)) {
sheet0.add((ReportDTO) obj);
}

Author:
afajardo
