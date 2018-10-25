package br.com.cetelem.content.targeting.model;

public class MenuOptionsRepresentation {

    private boolean personalLoanEligible;

    private boolean statementInstallmentEligible;

    private boolean cashAdvanceEligible;

    private boolean installmentWithdrawalEligible;

    public MenuOptionsRepresentation(
        boolean personalLoanEligible, boolean statementInstallmentEligible,
        boolean cashAdvanceEligible, boolean installmentWithdrawalEligible) {

        this.personalLoanEligible = personalLoanEligible;
        this.statementInstallmentEligible = statementInstallmentEligible;
        this.cashAdvanceEligible = cashAdvanceEligible;
        this.installmentWithdrawalEligible = installmentWithdrawalEligible;
    }

    public boolean isPersonalLoanEligible() {

        return personalLoanEligible;
    }

    public void setPersonalLoanEligible(boolean personalLoanEligible) {

        this.personalLoanEligible = personalLoanEligible;
    }

    public boolean isStatementInstallmentEligible() {

        return statementInstallmentEligible;
    }

    public void setStatementInstallmentEligible(
        boolean statementInstallmentEligible) {

        this.statementInstallmentEligible = statementInstallmentEligible;
    }

    public boolean isCashAdvanceEligible() {

        return cashAdvanceEligible;
    }

    public void setCashAdvanceEligible(boolean cashAdvanceEligible) {

        this.cashAdvanceEligible = cashAdvanceEligible;
    }

    public boolean isInstallmentWithdrawalEligible() {

        return installmentWithdrawalEligible;
    }

    public void setInstallmentWithdrawalEligible(
        boolean installmentWithdrawalEligible) {

        this.installmentWithdrawalEligible = installmentWithdrawalEligible;
    }

    @Override
    public String toString() {

        return "MenuOptionsRepresentation [personalLoanEligible=" +
            personalLoanEligible + ", statementInstallmentEligible=" +
            statementInstallmentEligible + ", cashAdvanceEligible=" +
            cashAdvanceEligible + ", installmentWithdrawalEligible=" +
            installmentWithdrawalEligible + "]";
    }

}
