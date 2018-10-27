import core from 'metal';
import JSXComponent, {Config} from 'metal-jsx';
import axios from 'axios';

export default class AppComponent extends JSXComponent {

	created() {
		axios.get(`/o/extrato-rest-service/cliente/extrato/${Liferay.ThemeDisplay.getUserId()}`).then((response) => {
			this.state.extrato = response.data;
		}).catch((e) => {
			console.error(e);
		})
	}

	renderExtrato() {
		return (
			<div>
				<table style={{width: "100%", padding:"10px", padding: 0, borderCollapse: 'collapse'}}>
					<thead style={{fontWeight: '600'}}>
						<tr>
							<td>Data</td>
							<td>Descrição</td>
							<td>Valor</td>
						</tr>
					</thead>

					<tbody>
						{
							extrato.extrato.lancamentos.map(lancamento => {
								return (
									<tr>
										<td style={tdStyle}>{lancamento.data}</td>
										<td style={tdStyle}>{lancamento.descricao}</td>
										<td style={tdStyle}>R$ {lancamento.valor}</td>
									</tr>
								)
							})
						}
					</tbody>
				</table>

				<div style={{display: 'flex', justifyContent: 'flex-end', marginTop: '20px', fontWeight: '600', paddingRight: '127px'}}>
				Total: R${extrato.extrato.total}
				</div>
			</div>
		);
	}

	renderEmpty() {
		return (
			<div>Carregando...</div>
		)
	}

	render() {

		const tdStyle = {borderBottom: '1px solid #d8d8d8', padding: '10px'};
		const {extrato} = this.state;
		console.log(extrato);

		if(extrato.extrato.lancamentos.length > 0) {

			return (
				<div>
					<h1>Extrato</h1>

					<table style={{width: "100%", padding:"10px", padding: 0, borderCollapse: 'collapse'}}>
						<thead style={{fontWeight: '600'}}>
							<tr>
								<td>Data</td>
								<td>Descrição</td>
								<td>Valor</td>
							</tr>
						</thead>

						<tbody>
							{
								extrato.extrato.lancamentos.map(lancamento => {
									return (
										<tr>
											<td style={tdStyle}>{lancamento.data}</td>
											<td style={tdStyle}>{lancamento.descricao}</td>
											<td style={tdStyle}>R$ {lancamento.valor}</td>
										</tr>
									)
								})
							}
						</tbody>
					</table>

					<div style={{display: 'flex', justifyContent: 'flex-end', marginTop: '20px', fontWeight: '600', paddingRight: '127px'}}>
					Total: R${extrato.extrato.total}
					</div>

					
				</div>
			);
		}else {
			return (
				<div>
					<h1>Extrato</h1>
					<div>Carregando...</div>
			</div>
			)
		}
		
    }
}

AppComponent.STATE = {
	extrato: {
		value: {
			extrato: {
				lancamentos: [],
				total: 0
			}
		}
	}
}