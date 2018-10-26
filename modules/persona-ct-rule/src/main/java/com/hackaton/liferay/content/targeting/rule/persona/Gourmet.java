package com.hackaton.liferay.content.targeting.rule.persona;

/**
 * @author André Fabbro
 */
public class Gourmet implements Persona {

    @Override
    public String getLabel() {
        return Persona.GOURMET;
    }

//    @Override
//    public boolean getStatus(MenuOptionsRepresentation options) {
//        return options.isInstallmentWithdrawalEligible();
//    }

}
