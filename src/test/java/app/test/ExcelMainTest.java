package app.test;

import java.io.File;

public class ExcelMainTest {

	public static void main(String[] args) {

		File informe = new File("c:/BackUp/ws_inno_test_resources/INDICADORES_HEADCOUNT.xlsx");
		/**
		 * Como añadido, mandar un Map<SheetIndex, List<Object>> Donde
		 * SheetIndex es el índice de la hoja (0, si es la primera, 1 si es la
		 * segunda...) y la lista de Objects son los objetos necesarios para
		 * dicha hoja... mínimo uno si tiene celdas concretas o datos... dos, si
		 * tiene celdas concretas (primer object) y datos (segundo object, que
		 * será una lista de este objeto existente en el primer objeto)
		 * 
		 * Para este segundo caso, añadir el property correspondiente y adaptar
		 * el código para leer la excel
		 * 
		 */

		// Map<String, Object> objects = new HashMap<>();
		// objects.put("IndicadoresHCDTO", new IndicadoresHCDTO());
		// objects.put("DetalleIndicadoresHCDTO", new
		// DetalleIndicadoresHCDTO());
		// objects.put("DetalleHCDTO", new DetalleHCDTO());
		// String filename = "managedExcel";
		// try {
		// ExcelReader.reader(informe, objects, filename);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

}
