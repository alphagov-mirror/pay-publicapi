package uk.gov.pay.api.service;

import au.com.dius.pact.consumer.PactVerification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.pay.api.app.RestClientFactory;
import uk.gov.pay.api.app.config.PublicApiConfig;
import uk.gov.pay.api.app.config.RestClientConfig;
import uk.gov.pay.api.auth.Account;
import uk.gov.pay.commons.testing.pact.consumers.PactProviderRule;
import uk.gov.pay.commons.testing.pact.consumers.Pacts;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static uk.gov.pay.commons.model.TokenPaymentType.CARD;

@RunWith(MockitoJUnitRunner.class)
public class CapturePaymentServiceTest {

    private static final String ACCOUNT_ID = "123456";
    private static final String CHARGE_ID = "ch_e36c168c41a0";

    private CapturePaymentService capturePaymentService;

    @Rule
    public PactProviderRule connectorRule = new PactProviderRule("connector", this);

    @Mock
    private PublicApiConfig mockConfiguration;

    @Before
    public void setup() {
        when(mockConfiguration.getConnectorUrl()).thenReturn(connectorRule.getUrl());

        ConnectorUriGenerator connectorUriGenerator = new ConnectorUriGenerator(mockConfiguration);
        Client client = RestClientFactory.buildClient(new RestClientConfig(false));
        capturePaymentService = new CapturePaymentService(client, connectorUriGenerator);
    }

    @Test
    @PactVerification({"connector"})
    @Pacts(pacts = {"publicapi-connector-capture-payment-with-delayed-capture-true-and-awaiting-capture-request-status"})
    public void testCapturePayment() {
        Account account = new Account(ACCOUNT_ID, CARD);

        Response capturePaymentResponse = capturePaymentService.capture(account, CHARGE_ID);

        assertThat(capturePaymentResponse.getStatus(), is(204));
    }

}
