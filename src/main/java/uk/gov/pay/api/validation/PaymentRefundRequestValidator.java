package uk.gov.pay.api.validation;

import uk.gov.pay.api.exception.PaymentValidationException;
import uk.gov.pay.api.model.CreatePaymentRefundRequest;
import uk.gov.pay.api.model.PaymentError;

import static java.lang.String.format;
import static uk.gov.pay.api.model.CreatePaymentRefundRequest.REFUND_MIN_VALUE;
import static uk.gov.pay.api.model.request.CreatePaymentRequest.AMOUNT_FIELD_NAME;
import static uk.gov.pay.api.model.request.CreatePaymentRequest.AMOUNT_MAX_VALUE;
import static uk.gov.pay.api.model.PaymentError.Code.CREATE_PAYMENT_REFUND_VALIDATION_ERROR;
import static uk.gov.pay.api.model.PaymentError.aPaymentError;

public class PaymentRefundRequestValidator {

    public void validate(CreatePaymentRefundRequest paymentRefundRequest) {
        validateAmount(paymentRefundRequest.getAmount());
    }

    private void validateAmount(int amount) {
        validate(amount >= REFUND_MIN_VALUE,
                aPaymentError(AMOUNT_FIELD_NAME, CREATE_PAYMENT_REFUND_VALIDATION_ERROR, format("Must be greater than or equal to %d", REFUND_MIN_VALUE)));

        validate(amount <= AMOUNT_MAX_VALUE,
                aPaymentError(AMOUNT_FIELD_NAME, CREATE_PAYMENT_REFUND_VALIDATION_ERROR, format("Must be less than or equal to %d", AMOUNT_MAX_VALUE)));
    }

    private static void validate(boolean condition, PaymentError error) {
        if (!condition) {
            throw new PaymentValidationException(error);
        }
    }
}
