package br.com.cetelem.content.targeting.model;

/**
 * @author André Fabbro
 */
public class PersonalLoan implements Eligibility {

    @Override
    public String getLabel() {
        return Eligibility.PERSONAL_LOAN;
    }

    @Override
    public boolean getStatus(MenuOptionsRepresentation options) {
        return options.isPersonalLoanEligible();
    }

}
