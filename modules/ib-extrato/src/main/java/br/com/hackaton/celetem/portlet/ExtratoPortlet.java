package br.com.hackaton.celetem.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import br.com.hackaton.celetem.constants.ExtratoPortletKeys;
import br.com.hackaton.celetem.model.Extrato;
import br.com.hackaton.celetem.model.Lancamento;

/**
 * @author andrefabbro
 */
@Component(immediate = true, property = { "com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true", "javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp", "javax.portlet.name=" + ExtratoPortletKeys.Extrato,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" }, service = Portlet.class)
public class ExtratoPortlet extends MVCPortlet {

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		// faz um get num serviço para buscar o extrato:
		// servico.com/extrato/${userId}

		Extrato extrato = new Extrato();
		extrato.setDataFinal(new Date());
		extrato.setDataInicial(new Date());
		extrato.setTotal(new Double(100));
		extrato.setLancamentos(new ArrayList<Lancamento>());

		extrato.getLancamentos().add(mockLanc(new Date(), "Hello World 0", new Double(10)));
		extrato.getLancamentos().add(mockLanc(new Date(), "Hello World 1", new Double(10)));
		extrato.getLancamentos().add(mockLanc(new Date(), "Hello World 2", new Double(10)));
		extrato.getLancamentos().add(mockLanc(new Date(), "Hello World 3", new Double(10)));
		extrato.getLancamentos().add(mockLanc(new Date(), "Hello World 4", new Double(10)));
		extrato.getLancamentos().add(mockLanc(new Date(), "Hello World 5", new Double(10)));
		extrato.getLancamentos().add(mockLanc(new Date(), "Hello World 6", new Double(10)));

		super.doView(renderRequest, renderResponse);
	}

	private Lancamento mockLanc(Date date, String description, Double valor) {
		Lancamento lanc = new Lancamento();
		lanc.setData(date);
		lanc.setDescricao(description);
		lanc.setValor(valor);
		return lanc;
	}

}