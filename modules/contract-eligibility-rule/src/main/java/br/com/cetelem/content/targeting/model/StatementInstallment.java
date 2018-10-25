package br.com.cetelem.content.targeting.model;

/**
 * @author André Fabbro
 */
public class StatementInstallment implements Eligibility {

    @Override
    public String getLabel() {
        return Eligibility.STATEMENT_INSTALLMENT;
    }

    @Override
    public boolean getStatus(MenuOptionsRepresentation options) {
        return options.isStatementInstallmentEligible();
    }

}
