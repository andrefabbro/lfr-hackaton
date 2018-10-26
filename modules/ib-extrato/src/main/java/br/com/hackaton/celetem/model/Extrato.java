/**
 * 
 */
package br.com.hackaton.celetem.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author André Fabbro
 *
 */
public class Extrato implements Serializable {

	private static final long serialVersionUID = -433039552985036580L;

	private Date dataInicial;

	private Date dataFinal;

	private List<Lancamento> lancamentos;

	private Double total;

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
