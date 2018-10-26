/**
 * 
 */
package br.com.hackaton.celetem.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author André Fabbro
 *
 */
public class Lancamento implements Serializable {

	private static final long serialVersionUID = 1027722192416580175L;

	private Date data;

	private String descricao;

	private Double valor;
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

}
