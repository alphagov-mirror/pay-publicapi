package uk.gov.pay.api.validation;

import org.junit.Test;
import uk.gov.pay.api.exception.RefundsValidationException;
import uk.gov.pay.api.service.RefundsParams;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static uk.gov.pay.api.matcher.RefundValidationExceptionMatcher.aValidationExceptionContaining;

public class RefundSearchValidatorTest {

    @Test
    public void validateSearchParameters_shouldSuccessValidation() {
        RefundSearchValidator.validateSearchParameters(
                new RefundsParams("2016-01-25T13:23:55Z", "2016-01-25T13:23:55Z", "1", "1"));
    }

    @Test
    public void validateParams_shouldNotGiveAnErrorValidation_ForMissingPageDisplaySize() {
        RefundSearchValidator.validateSearchParameters(new RefundsParams("2016-01-25T13:23:55Z", "2016-01-25T13:23:55Z", null, null));
    }

    @Test
    public void validateSearchParameters_shouldGiveAValidationError_ForNonValidFromDate() {
        RefundsValidationException validationException = assertThrows(RefundsValidationException.class,
                () -> RefundSearchValidator.validateSearchParameters(
                        new RefundsParams("nope", "2016-01-25T13:23:55Z", "1", "1")));

        assertThat(validationException, aValidationExceptionContaining("P1101",
                "Invalid parameters: from_date. See Public API documentation for the correct data formats"));
    }

    @Test
    public void validateSearchParameters_shouldGiveAValidationError_ForNonValidToDate() {
        RefundsValidationException validationException = assertThrows(RefundsValidationException.class,
                () -> RefundSearchValidator.validateSearchParameters(
                        new RefundsParams("2016-01-25T13:23:55Z", "nope", "1", "1")));

        assertThat(validationException, aValidationExceptionContaining("P1101",
                "Invalid parameters: to_date. See Public API documentation for the correct data formats"));
    }

    @Test
    public void validateSearchParameters_shouldGiveAValidationError_ForNonNumericPageAndSize() {
        String NON_NUMERIC_STRING = "non-numeric-string";
        RefundsValidationException validationException = assertThrows(RefundsValidationException.class,
                () -> RefundSearchValidator.validateSearchParameters(new RefundsParams("2016-01-25T13:23:55Z",
                        "2016-01-25T13:23:55Z", NON_NUMERIC_STRING, NON_NUMERIC_STRING)));

        assertThat(validationException, aValidationExceptionContaining("P1101",
                "Invalid parameters: page, display_size. See Public API documentation for the correct data formats"));
    }

    @Test
    public void validateParams_shouldGiveAnErrorValidation_forZeroPageDisplay() {
        RefundsValidationException validationException = assertThrows(RefundsValidationException.class,
                () -> RefundSearchValidator.validateSearchParameters(
                        new RefundsParams("2016-01-25T13:23:55Z", "2016-01-25T13:23:55Z", "0", "0")));

        assertThat(validationException, aValidationExceptionContaining("P1101",
                "Invalid parameters: page, display_size. See Public API documentation for the correct data formats"));
    }

    @Test
    public void validateParams_shouldGiveAnErrorValidation_forMaxedOutValuesPageDisplaySize() {
        RefundsValidationException validationException = assertThrows(RefundsValidationException.class,
                () -> RefundSearchValidator.validateSearchParameters(new RefundsParams("2016-01-25T13:23:55Z",
                        "2016-01-25T13:23:55Z", String.valueOf(Integer.MAX_VALUE + 1), String.valueOf(Integer.MAX_VALUE + 1))));

        assertThat(validationException, aValidationExceptionContaining("P1101",
                "Invalid parameters: page, display_size. See Public API documentation for the correct data formats"));
    }

    @Test
    public void validateParams_shouldGiveAnErrorValidation_forTooLargePageDisplay() {
        RefundsValidationException validationException = assertThrows(RefundsValidationException.class,
                () -> RefundSearchValidator.validateSearchParameters(
                        new RefundsParams("2016-01-25T13:23:55Z", "2016-01-25T13:23:55Z", "0", "501")));

        assertThat(validationException, aValidationExceptionContaining("P1101",
                "Invalid parameters: page, display_size. See Public API documentation for the correct data formats"));
    }
}
