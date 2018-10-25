package br.com.cetelem.content.targeting.model;

/**
 * @author André Fabbro
 */
public class InstallmentWithdrawal implements Eligibility {

    @Override
    public String getLabel() {
        return Eligibility.INSTALLMENT_WITHDRAWAL;
    }

    @Override
    public boolean getStatus(MenuOptionsRepresentation options) {
        return options.isInstallmentWithdrawalEligible();
    }

}
