# ExcelReader
Java JAR Utility Tool to process an Excel and extract the information to the specific objects needed
Type of Excels that could be processed: 
- One or more sheets 
- Each sheet has or concrete cells to process (know the complete cell reference), or data rows of a table to process (know at less initial row, if don't know the last row the tool process data rows until end of sheet), or combination of concrete cells and data row table.

Use method:

1.- Define an object for each sheet to process with needed fields. If you want to set the values from each row of a table, you can specify a different object for this data Example: In a sheet I have 3 concrete cells (report date, author, version), in a header section, and a table with 3 columns (id, name, counter)

I create a TableDTO with the following information:# ExcelReader
Java JAR Utility Tool to process an Excel and extract the information to the specific objects needed
Type of Excels that could be processed: 
- One or more sheets 
- Each sheet has or concrete cells to process (know the complete cell reference), or data rows of a table to process (know at less initial row, if don't know the last row the tool process data rows until end of sheet), or combination of concrete cells and data row table.

Use method:

1.- Define an object for each sheet to process with needed fields. If you want to set the values from each row of a table, you can specify a different object for this data Example: In a sheet I have 3 concrete cells (report date, author, version), in a header section, and a table with 3 columns (id, name, counter)

I create a TableDTO with the following information:<br>
-- BigInteger id<br>
-- String name<br>
-- BigInteger counter<p>

I create a ReportDTO with the following information:<br> 
-- Date reportDate <br>
-- String author <br>
-- String version <br>
-- List<TableDTO> rows<p>
2.- In your application, define a property file with the following information (using the example above): <br>

<b>Sheet indexes to process (values with comma separates)</b><br>
sheet.index.to.process=0,1<p>
For each sheet:<br> 

<b>Sheet index in the Workbook </b><br>
<b>sheetX partial property must be the same that sheetX.index value informed</b><br>
sheet0.index=0<p>

<b>Class for sheet data sheet </b><br>
sheet0.info.class = ReportDTO<p>

<b> Number of concrete cells. If don't have concrete cells, the value has to be 0 </b><br>
sheet0.num.concrete.cells=3<p>

<b> For each concrete cell (required if "Number of concrete cells" is greater than 0) If the cell is a combined cell, the full reference is the first coll and row of the combined cell</b><br>
sheet0.cell1.reference=B4<br>
sheet0.cell1.java.type=Date<br>
sheet0.cell1.binding.field=reportDate<p>

sheet0.cell2.reference=F3<br>
sheet0.cell2.java.type=String<br>
sheet0.cell2.binding.field=author<p>

sheet0.cell3.reference=F4<br>
sheet0.cell3.java.type=String<br>
sheet0.cell3.binding.field=version<p>

<b> First row index with data to process. If don't have rows to process, the value has to be 0</b><br>
sheet0.data.row.initial.index=7<p>

<b> Last row index with data to process. If you don't know, set 0 to this value and the application process until last row of the sheet</b><br>
sheet0.data.row.last.index=0<p>

<b> Class Binding for data row</b><br>
<b> If you want to use different class to binding data rows information, you must to have a List typed with the object you specify here in the object you set in sheetX.info.class property</b><br>
sheet0.data.row.binding.class=TableDTO<br>
<b> No need to inform next property if the binding class is the same that sheetX.info.class</b><br>
sheet0.data.row.binding.field.name=rows<p>

<b> Number of columns for each data row </b><br>
sheet0.data.row.num.cells=5<p>

<b> Columns Configuration</b><br>
sheet0.data.row.cell1.col=A<br>
sheet0.data.row.cell1.java.type=BigInteger<br>
sheet0.data.row.cell1.binding.field=id<p>

sheet0.data.row.cell2.col=B<br>
sheet0.data.row.cell2.java.type=String<br>
sheet0.data.row.cell2.binding.field=name<p>

sheet0.data.row.cell3.col=C<br>
sheet0.data.row.cell3.java.type=BigInteger<br>
sheet0.data.row.cell3.binding.field=counter<p>

//----------------------------------------------------------------- <br>
//Java Types admitted in version (Beta) 0.0.1<br>
//----------------------------------------------------------------- <br>
//Numeric: Integer, BigInteger, BigDecimal, Double, Long --> This use CellTypes.NUMERIC <br>
//Text: String --> This use CellTypes.STRING <br>
//Dates: Date (java.util) --> This use CellTypes.NUMERIC<br>
//Logic: Boolean --> This use CellTypes.BOOLEAN<br>

3.- From a class of your application:
-- Get the property file defined in section 2 of this documentation like a ResourceBundle<p>

-- Define a Map<String, Object>, where String is the SimpleName of one of the objects you define in the property file as binding class, and Object is an instance (could be an empty instance) of that Object<br>
Example:<br>
<i>
Map<String, Object> mapBindingObjects = new HashMap<>();<br>
mapBindingObjects.put("ReportDTO"; new ReportDTO());<br>
mapBindingObjects.put("TableDTO"; new TableDTO());<br>
</i>
<p>
-- Define the map to get the result. It will be a Map<Integer, List<Object>> where Integer is the sheet index and the list Object is a List with instance of Object of the type you define in sheet0.info.class<p>

-- Invoke the reader!!!<br>
<i>
Map<Integer, List<Object>> resultMap = ExcelReader.reader(excelFile, mapBindingObjects, resourceBundle);
</i>
<p>
-- Finally, if don't receive errors, you will have a map with a list of objects that has the excel information<br>
<i>List<ReportDTO> sheet0 = new ArrayList<>();<br>
for (Object obj : resultMap.get(0)) {<br>
sheet0.add((ReportDTO) obj);<br>
  }</i>
<p>
<b>Author:</b><br>
afajardomorera