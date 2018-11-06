package app.test.dto;

import java.math.BigInteger;

public class DetalleIndicadoresHCDTO {

	private String centro;
	private String linea;
	private String categoria;
	private String tipoContrato;
	private BigInteger headCount;

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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public BigInteger getHeadCount() {
		return headCount;
	}

	public void setHeadCount(BigInteger headCount) {
		this.headCount = headCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DetalleIndicadoresHCDTO [centro=");
		builder.append(centro);
		builder.append(", linea=");
		builder.append(linea);
		builder.append(", categoria=");
		builder.append(categoria);
		builder.append(", tipoContrato=");
		builder.append(tipoContrato);
		builder.append(", headCount=");
		builder.append(headCount);
		builder.append("]");
		return builder.toString();
	}
}
