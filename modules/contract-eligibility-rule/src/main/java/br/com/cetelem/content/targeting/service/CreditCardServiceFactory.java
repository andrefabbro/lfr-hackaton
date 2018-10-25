package br.com.cetelem.content.targeting.service;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component(immediate = true, service = CreditCardServiceFactory.class)
public class CreditCardServiceFactory {

    private Log _log = LogFactoryUtil.getLog(CreditCardServiceFactory.class);

    public CreditCardService newCreditCardService(String baseUrl) {

        _log.debug("Get eligibilities from " + baseUrl);

        Retrofit retrofit =
            new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(
                GsonConverterFactory.create()).build();

        CreditCardService service = retrofit.create(CreditCardService.class);

        return service;
    }

}
