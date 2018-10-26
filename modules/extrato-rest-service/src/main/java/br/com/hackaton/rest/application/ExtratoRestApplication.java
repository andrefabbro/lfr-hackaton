package br.com.hackaton.rest.application;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;

/**
 * @author André Fabbro
 */
@ApplicationPath("/extrato")
@Component(immediate = true, service = Application.class)
public class ExtratoRestApplication extends Application {

	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
	}

	@GET
	@Path("/list")
	@Produces("application/json")
	public String working() {
		
		String result = "{\n" + 
				"    \"extrato\": {\n" + 
				"        \"dataInicio\": \"10/10/2018\",\n" + 
				"        \"dataFim\": \"10/01/2018\",\n" + 
				"        \"total\": \"200.00\",\n" + 
				"        \"lancamentos\": [{\n" + 
				"                \"data\": \"10/01/2018\",\n" + 
				"                \"descricao\": \"Hello World\",\n" + 
				"                \"valor\": \"104.34\"\n" + 
				"            },\n" + 
				"            {\n" + 
				"                \"data\": \"10/01/2018\",\n" + 
				"                \"descricao\": \"Hello World\",\n" + 
				"                \"valor\": \"104.34\"\n" + 
				"            }, {\n" + 
				"                \"data\": \"10/01/2018\",\n" + 
				"                \"descricao\": \"Hello World\",\n" + 
				"                \"valor\": \"104.34\"\n" + 
				"            }, {\n" + 
				"                \"data\": \"10/01/2018\",\n" + 
				"                \"descricao\": \"Hello World\",\n" + 
				"                \"valor\": \"104.34\"\n" + 
				"            }\n" + 
				"        ]\n" + 
				"    }\n" + 
				"}";
		
		return result;
	}

}