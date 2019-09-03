package uk.gov.pay.api.service.telephone;

import uk.gov.pay.api.auth.Account;
import uk.gov.pay.api.model.telephone.CreateTelephonePaymentRequest;
import uk.gov.pay.api.model.telephone.TelephonePaymentResponse;
import uk.gov.pay.api.service.ConnectorUriGenerator;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.client.Entity.json;

public class CreateTelephonePaymentService {
    
    private final Client client;
    private final ConnectorUriGenerator connectorUriGenerator;

    @Inject
    public CreateTelephonePaymentService(Client client, ConnectorUriGenerator connectorUriGenerator) {
        this.client = client;
        this.connectorUriGenerator = connectorUriGenerator;
    }

    public TelephonePaymentResponse create(CreateTelephonePaymentRequest createTelephonePaymentRequest){
        return null;
    }
    
    private Response createTelephoneCharge(Account account, CreateTelephonePaymentRequest createTelephonePaymentRequest) {
        return client
                .target(connectorUriGenerator.telephoneChargesURI(account))
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .post(buildTelephoneChargeRequestPayload(createTelephonePaymentRequest));
    }
    
    private Entity buildTelephoneChargeRequestPayload(CreateTelephonePaymentRequest requestPayload) {
        return json(requestPayload.toConnectorPayload());
    }
}

/*
        State state = new State(
                "success",
                true,
                "Created",
                "P0010"
        );
        
        return new TelephonePaymentResponse(
                createTelephonePaymentRequest.getAmount(), - DONE
                createTelephonePaymentRequest.getReference(), - DONE
                createTelephonePaymentRequest.getDescription(), - DONE
                createTelephonePaymentRequest.getCreatedDate(), - DONE
                createTelephonePaymentRequest.getAuthorisedDate(), - DONE
                createTelephonePaymentRequest.getProcessorId(), - DONE
                createTelephonePaymentRequest.getProviderId(), - DONE
                createTelephonePaymentRequest.getAuthCode(), - DONE
                createTelephonePaymentRequest.getPaymentOutcome(), - DONE
                createTelephonePaymentRequest.getCardType(), - in card details
                createTelephonePaymentRequest.getNameOnCard(), - in card details
                createTelephonePaymentRequest.getEmailAddress(), - in email
                createTelephonePaymentRequest.getCardExpiry(), - in card details
                createTelephonePaymentRequest.getLastFourDigits(), - in card details
                createTelephonePaymentRequest.getFirstSixDigits(), - in card details
                createTelephonePaymentRequest.getTelephoneNumber(), - DONE
                "dummypaymentid123notpersisted", - in chargeID
                state - in PaymentState
        );
         */
