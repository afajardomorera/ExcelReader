package app.test.dto;

import java.math.BigInteger;
import java.util.Date;

public class DetalleHCDTO {

	private BigInteger numeroEmpleado;
	private String nombre;
	private String apellidos;
	private String categoriaProfesional;
	private String cargo;
	private String horario;
	private String contrato;
	private Date fechaIncorporacion;
	private String fechaBaja;
	private String centro;
	private String linea;
	private String celda;
	private String tecnologia;
	private Date fechaDatos;
	private Date fechaDesde;
	private Date fechaHasta;

	public BigInteger getNumeroEmpleado() {
		return numeroEmpleado;
	}

	public void setNumeroEmpleado(BigInteger numeroEmpleado) {
		this.numeroEmpleado = numeroEmpleado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCategoriaProfesional() {
		return categoriaProfesional;
	}

	public void setCategoriaProfesional(String categoriaProfesional) {
		this.categoriaProfesional = categoriaProfesional;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public Date getFechaIncorporacion() {
		return fechaIncorporacion;
	}

	public void setFechaIncorporacion(Date fechaIncorporacion) {
		this.fechaIncorporacion = fechaIncorporacion;
	}

	public String getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getCentro() {
		return centro;
	}

	public void setCentro(String centro) {
		this.centro = centro;
	}

	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}

	public String getCelda() {
		return celda;
	}

	public void setCelda(String celda) {
		this.celda = celda;
	}

	public String getTecnologia() {
		return tecnologia;
	}

	public void setTecnologia(String tecnologia) {
		this.tecnologia = tecnologia;
	}

	public Date getFechaDatos() {
		return fechaDatos;
	}

	public void setFechaDatos(Date fechaDatos) {
		this.fechaDatos = fechaDatos;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DetalleHeadCountDTO [numeroEmpleado=");
		builder.append(numeroEmpleado);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", apellidos=");
		builder.append(apellidos);
		builder.append(", categoriaProfesional=");
		builder.append(categoriaProfesional);
		builder.append(", cargo=");
		builder.append(cargo);
		builder.append(", horario=");
		builder.append(horario);
		builder.append(", contrato=");
		builder.append(contrato);
		builder.append(", fechaIncorporacion=");
		builder.append(fechaIncorporacion);
		builder.append(", fechaBaja=");
		builder.append(fechaBaja);
		builder.append(", centro=");
		builder.append(centro);
		builder.append(", linea=");
		builder.append(linea);
		builder.append(", celda=");
		builder.append(celda);
		builder.append(", tecnologia=");
		builder.append(tecnologia);
		builder.append(", fechaDatos=");
		builder.append(fechaDatos);
		builder.append(", fechaDesde=");
		builder.append(fechaDesde);
		builder.append(", fechaHasta=");
		builder.append(fechaHasta);
		builder.append("]");
		return builder.toString();
	}
}
