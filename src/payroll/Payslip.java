package payroll;

public class Payslip {
    private final long grossSalary;

    public Payslip(long grossSalary) {
        this.grossSalary = grossSalary;
    }

    public long netSalary() {
        return grossSalary - new TaxCalculator().taxFor(grossSalary);
    }

    private class TaxCalculator {

        private long taxFor(long grossSalary) {
            final TaxBand lowerTaxBand = new TaxBand.ChainedTaxBand(0.1, 5000, 1500, TaxBand.NIL_TAX_BAND);
            final TaxBand middleTaxBand = new TaxBand.ChainedTaxBand(0.2, 20000, 4000, lowerTaxBand);
            final TaxBand upperTaxBand = new TaxBand.ChainedTaxBand(0.4, 40000, 0, middleTaxBand);


            long tax = 0;

            if (lowerTaxBand.appliesTo(grossSalary)) {
                tax = lowerTaxBand.taxFor(grossSalary);
            }

            if (middleTaxBand.appliesTo(grossSalary)) {
                tax = middleTaxBand.taxFor(grossSalary);
            }

            if (upperTaxBand.appliesTo(grossSalary)) {
                tax =  upperTaxBand.taxFor(grossSalary);
            }
            return tax;
        }
    }
}
