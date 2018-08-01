package uk.gov.pay.api.validation;


import org.junit.Test;
import uk.gov.pay.api.exception.ValidationException;

import java.time.ZonedDateTime;

import static org.junit.Assert.assertTrue;

public class DirectDebitEventSearchValidatorTest {
    
    
    @Test
    public void shouldPassValidation() {
        DirectDebitEventSearchValidator.validateSearchParameters(ZonedDateTime.now().toString(), ZonedDateTime.now().toString(), 1);
    }

    @Test(expected = ValidationException.class)
    public void shouldFailWithInvalidDisplaySize() {
        try {
            DirectDebitEventSearchValidator.validateSearchParameters(ZonedDateTime.now().toString(), ZonedDateTime.now().toString(), 501);
        } catch (ValidationException ex) {
            assertTrue(ex.getPaymentError().getDescription().contains("display_size"));
            throw ex;
        }
    }

    @Test(expected = ValidationException.class)
    public void shouldFailWithInvalidToDate() {
        try {
            DirectDebitEventSearchValidator.validateSearchParameters("Invalid date string", ZonedDateTime.now().toString(), 1);
        } catch (ValidationException ex) {
            assertTrue(ex.getPaymentError().getDescription().contains("to_date"));
            throw ex;
        }
    }

    @Test(expected = ValidationException.class)
    public void shouldFailWithInvalidFromDate() {
        try {
            DirectDebitEventSearchValidator.validateSearchParameters(ZonedDateTime.now().toString(), "Invalid date string", 1);
        } catch (ValidationException ex) {
            assertTrue(ex.getPaymentError().getDescription().contains("from_date"));
            throw ex;
        }
    }
}
