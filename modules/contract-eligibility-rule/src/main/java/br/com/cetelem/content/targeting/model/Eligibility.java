package br.com.cetelem.content.targeting.model;

/**
 * @author André Fabbro
 */
public interface Eligibility {

    public static final String PERSONAL_LOAN = "personalLoan";

    public static final String STATEMENT_INSTALLMENT = "statementInstallment";

    public static final String CASH_ADVANCE = "cashAdvance";

    public static final String INSTALLMENT_WITHDRAWAL = "installmentWithdrawal";

    String getLabel();

    boolean getStatus(MenuOptionsRepresentation options);

}
