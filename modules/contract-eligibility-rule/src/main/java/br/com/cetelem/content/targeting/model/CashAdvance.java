package br.com.cetelem.content.targeting.model;

/**
 * @author André Fabbro
 */
public class CashAdvance implements Eligibility {

    @Override
    public String getLabel() {
        return Eligibility.CASH_ADVANCE;
    }

    @Override
    public boolean getStatus(MenuOptionsRepresentation options) {
        return options.isCashAdvanceEligible();
    }

}
