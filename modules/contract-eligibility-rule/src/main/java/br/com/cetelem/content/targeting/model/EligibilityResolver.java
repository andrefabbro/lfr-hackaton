package br.com.cetelem.content.targeting.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author André Fabbro
 */
public class EligibilityResolver {

    private Map<String, Eligibility> eligibilities;

    private static EligibilityResolver INSTANCE = null;

    private EligibilityResolver() {

        eligibilities = new HashMap<String, Eligibility>();

        eligibilities.put(Eligibility.PERSONAL_LOAN, new PersonalLoan());
        eligibilities.put(
            Eligibility.STATEMENT_INSTALLMENT, new StatementInstallment());
        eligibilities.put(Eligibility.CASH_ADVANCE, new CashAdvance());
        eligibilities.put(
            Eligibility.INSTALLMENT_WITHDRAWAL, new InstallmentWithdrawal());
    }

    public static synchronized EligibilityResolver getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new EligibilityResolver();
        }

        return INSTANCE;
    }

    public Eligibility getEligibility(String label) {

        return eligibilities.get(label);
    }

}
