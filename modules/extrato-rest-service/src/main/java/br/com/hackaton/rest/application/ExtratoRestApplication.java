package br.com.hackaton.rest.application;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

/**
 * @author André Fabbro
 */
@ApplicationPath("/cliente")
@Component(immediate = true, service = Application.class)
public class ExtratoRestApplication extends Application {

	private Map<String, String> extratos;
	
	private Map<String, String> personas;
	
	@Reference
	private UserLocalService userLocalService;
	
	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
	}
	
	@GET
	@Produces("text/plain")
	public String getDefault() {
	    
	    extratos = new HashMap<String, String>();
	    personas = new HashMap<String, String>();
	    
	    personas.put("joaosilva", "GOURMET");
	    personas.put("pedrocosta", "TRAVEL");
	    personas.put("joseteixeira", "");
	    
	    String extrato1 = "{\n" + 
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

	    String extrato2 = "{\n" + 
	        "    \"extrato\": {\n" + 
	        "        \"dataInicio\": \"10/10/2018\",\n" + 
	        "        \"dataFim\": \"10/01/2018\",\n" + 
	        "        \"total\": \"200.00\",\n" + 
	        "        \"lancamentos\": [{\n" + 
	        "                \"data\": \"10/01/2018\",\n" + 
	        "                \"descricao\": \"Hello World\",\n" + 
	        "                \"valor\": \"50\"\n" + 
	        "            },\n" + 
	        "            {\n" + 
	        "                \"data\": \"10/01/2018\",\n" + 
	        "                \"descricao\": \"Hello World\",\n" + 
	        "                \"valor\": \"50\"\n" + 
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
	    
	    String extrato3 = "{\n" + 
		        "    \"extrato\": {\n" + 
		        "        \"dataInicio\": \"10/10/2018\",\n" + 
		        "        \"dataFim\": \"10/01/2018\",\n" + 
		        "        \"total\": \"200.00\",\n" + 
		        "        \"lancamentos\": [{\n" + 
		        "                \"data\": \"10/01/2018\",\n" + 
		        "                \"descricao\": \"Hello World\",\n" + 
		        "                \"valor\": \"50\"\n" + 
		        "            },\n" + 
		        "            {\n" + 
		        "                \"data\": \"10/01/2018\",\n" + 
		        "                \"descricao\": \"Hello World\",\n" + 
		        "                \"valor\": \"50\"\n" + 
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
	    
	    extratos.put("joaosilva", extrato1);
	    extratos.put("fulano", extrato2);
	    extratos.put("joseteixeira", extrato3);
		return "Dados populados!";
	}
	
	@GET
	@Path("/persona/{username}")
	@Produces("text/plain")
	public String getPersona(@PathParam("username") String userId) {
		
		User user = null;
		try {
			user = userLocalService.getUser(new Long(userId));
			return personas.get(user.getScreenName());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		}
		
		return "";
	}

	@GET
	@Path("/extrato/{username}")
	@Produces("application/json")
	public String getExtrato(@PathParam("username") String userId) {
		
		User user = null;
		try {
			user = userLocalService.getUser(new Long(userId));
			return extratos.get(user.getScreenName());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		}
		
		return "";
	}

}