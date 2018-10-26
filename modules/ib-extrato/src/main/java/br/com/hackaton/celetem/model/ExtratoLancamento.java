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
public class ExtratoLancamento implements Serializable {

	private static final long serialVersionUID = 1027722192416580175L;

	private Date data;

	private String estabelecimento;

	private String valor;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(String estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
