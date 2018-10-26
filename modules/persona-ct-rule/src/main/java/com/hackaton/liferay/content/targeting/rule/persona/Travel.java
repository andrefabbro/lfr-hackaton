package com.hackaton.liferay.content.targeting.rule.persona;

/**
 * @author André Fabbro
 */
public class Travel implements Persona {

    @Override
    public String getLabel() {
        return Persona.TRAVEL;
    }

//    @Override
//    public boolean getStatus(MenuOptionsRepresentation options) {
//        return options.isInstallmentWithdrawalEligible();
//    }

}
