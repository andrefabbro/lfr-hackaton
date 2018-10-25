package br.com.cetelem.content.targeting.service;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import br.com.cetelem.content.targeting.model.MenuOptionsRepresentation;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author André Fabbro
 */
public class CreditCardServiceTest {

    @Test
    @Ignore
    public void testCallService()
        throws Exception {

        String jsessionId = "JSESSIONID=999999999";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(
            "http://localhost:8089").addConverterFactory(
                GsonConverterFactory.create()).build();

        CreditCardService service = retrofit.create(CreditCardService.class);

        Call<MenuOptionsRepresentation> call =
            service.findMenuOptions(jsessionId, "1222");

        Response<MenuOptionsRepresentation> response = call.execute();

        assertEquals(
            "Response{protocol=http/1.1, code=200, message=OK, url=http://localhost:8089/v1/contracts/1222/menuOptions}",
            response.toString());

        assertEquals(
            "MenuOptionsRepresentation [personalLoanEligible=true, statementInstallmentEligible=true, cashAdvanceEligible=false, installmentWithdrawalEligible=false]",
            response.body().toString());
    }

}
