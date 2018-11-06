package app.test.dto;

import java.util.Date;
import java.util.List;

public class IndicadoresHCDTO {

	private Date fecha;
	private Date fechaDesde;
	private Date fechaHasta;
	private List<DetalleIndicadoresHCDTO> datos;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public List<DetalleIndicadoresHCDTO> getDatos() {
		return datos;
	}

	public void setDatos(List<DetalleIndicadoresHCDTO> datos) {
		this.datos = datos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IndicadoresHCDTO [fecha=");
		builder.append(fecha);
		builder.append(", fechaDesde=");
		builder.append(fechaDesde);
		builder.append(", fechaHasta=");
		builder.append(fechaHasta);
		builder.append(", datos=");
		builder.append(datos);
		builder.append("]");
		return builder.toString();
	}

}
