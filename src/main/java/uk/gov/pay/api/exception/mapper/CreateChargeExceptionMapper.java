package uk.gov.pay.api.exception.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.pay.api.exception.CreateChargeException;
import uk.gov.pay.api.model.generated.PaymentError;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.PRECONDITION_FAILED;
import static uk.gov.pay.api.model.PaymentErrorBuilder.aPaymentError;
import static uk.gov.pay.api.model.PaymentErrorCodes.CREATE_PAYMENT_ACCOUNT_ERROR;
import static uk.gov.pay.api.model.PaymentErrorCodes.CREATE_PAYMENT_AGREEMENT_TYPE_ERROR;
import static uk.gov.pay.api.model.PaymentErrorCodes.CREATE_PAYMENT_CONNECTOR_ERROR;

public class CreateChargeExceptionMapper implements ExceptionMapper<CreateChargeException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateChargeExceptionMapper.class);

    @Override
    public Response toResponse(CreateChargeException exception) {

        PaymentError paymentError;

        if (exception.getErrorStatus() == NOT_FOUND.getStatusCode()) {
            paymentError = aPaymentError(CREATE_PAYMENT_ACCOUNT_ERROR);
        } else if (exception.getErrorStatus() == PRECONDITION_FAILED.getStatusCode()) {
            paymentError = aPaymentError(CREATE_PAYMENT_AGREEMENT_TYPE_ERROR);
        } else {
            paymentError = aPaymentError(CREATE_PAYMENT_CONNECTOR_ERROR);
        }

        LOGGER.error("Connector invalid response was {}.\n Returning http status {} with error body {}", exception.getMessage(), INTERNAL_SERVER_ERROR, paymentError);

        return Response
                .status(INTERNAL_SERVER_ERROR)
                .entity(paymentError)
                .build();
    }
}
