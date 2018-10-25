package br.com.cetelem.content.targeting.service;

import br.com.cetelem.content.targeting.model.MenuOptionsRepresentation;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface CreditCardService {

    @GET("/v1/contracts/{contractId}/menuOptions")
    public Call<MenuOptionsRepresentation> findMenuOptions(
        @Header("Cookie") String cookies,
        @Path("contractId") String contractId);

}
