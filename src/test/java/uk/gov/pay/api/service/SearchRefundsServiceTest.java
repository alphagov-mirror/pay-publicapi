package uk.gov.pay.api.service;

import au.com.dius.pact.consumer.PactVerification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonassert.JsonAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.pay.api.app.RestClientFactory;
import uk.gov.pay.api.app.config.PublicApiConfig;
import uk.gov.pay.api.app.config.RestClientConfig;
import uk.gov.pay.api.auth.Account;
import uk.gov.pay.api.exception.BadRefundsRequestException;
import uk.gov.pay.api.exception.RefundsValidationException;
import uk.gov.pay.commons.testing.pact.consumers.PactProviderRule;
import uk.gov.pay.commons.testing.pact.consumers.Pacts;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static uk.gov.pay.api.matcher.BadRefundsRequestExceptionMatcher.aBadRefundsRequestExceptionWithError;
import static uk.gov.pay.api.matcher.RefundValidationExceptionMatcher.aValidationExceptionContaining;
import static uk.gov.pay.commons.model.TokenPaymentType.CARD;
import static uk.gov.pay.commons.model.TokenPaymentType.DIRECT_DEBIT;

@RunWith(MockitoJUnitRunner.class)
public class SearchRefundsServiceTest {

    @Rule
    public PactProviderRule connectorRule = new PactProviderRule("connector", this);

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Mock
    private PublicApiConfig mockConfiguration;

    private SearchRefundsService searchRefundsService;
    private final static String PAGE = "1";
    private final static String DISPLAY_SIZE = "2";
    private String ACCOUNT_ID = "888";
    private RefundsParams params = new RefundsParams(PAGE, DISPLAY_SIZE);

    @Before
    public void setUp() {
        when(mockConfiguration.getConnectorUrl()).thenReturn(connectorRule.getUrl());
        when(mockConfiguration.getBaseUrl()).thenReturn("http://publicapi.test.localhost/");

        ConnectorUriGenerator uriGenerator = new ConnectorUriGenerator(mockConfiguration);
        PublicApiUriGenerator publicApiUriGenerator = new PublicApiUriGenerator(mockConfiguration);
        Client client = RestClientFactory.buildClient(new RestClientConfig(false));
        ObjectMapper objectMapper = new ObjectMapper();
        searchRefundsService = new SearchRefundsService(client, mockConfiguration, uriGenerator, objectMapper, publicApiUriGenerator);
    }

    @Test
    @PactVerification({"connector"})
    @Pacts(pacts = {"publicapi-connector-search-refunds-with-page-and-display"})
    public void getAllRefundsShouldReturnCorrectTotalAndPageAndDisplaySize() {
        String accountId = "777";
        String refundId1 = "111111";
        String refundId2 = "222222";
        String extChargeId = "someExternalId";
        Account account = new Account(accountId, CARD);
        Response response = searchRefundsService.getAllRefunds(account, params);

        JsonAssert.with(response.getEntity().toString())
                .assertThat("$.results.*", hasSize(2))
                .assertThat("count", is(2))
                .assertThat("total", is(2))
                .assertThat("page", is(1))
                .assertThat("$.results[0].status", is("available"))
                .assertThat("$.results[0].created_date", is("2017-10-01T01:41:01Z"))
                .assertThat("$.results[0].refund_id", is(refundId1))
                .assertThat("$.results[0].charge_id", is(extChargeId))
                .assertThat("$.results[0].amount_submitted", is(98))
                .assertThat("$.results[0].status", is("available"))
                .assertThat("$.results[0].links.self.href", is(format("http://publicapi.test.localhost/v1/payments/%s/refunds/%s", extChargeId, refundId1)))
                .assertThat("$.results[0].links.payment_url.href", is(format("http://publicapi.test.localhost/v1/payments/%s", extChargeId)))
                .assertThat("$.results[1].status", is("available"))
                .assertThat("$.results[1].created_date", is("2017-09-02T02:42:02Z"))
                .assertThat("$.results[1].refund_id", is(refundId2))
                .assertThat("$.results[1].charge_id", is(extChargeId))
                .assertThat("$.results[1].amount_submitted", is(100))
                .assertThat("$.results[1].status", is("available"))
                .assertThat("$.results[1].links.self.href", is(format("http://publicapi.test.localhost/v1/payments/%s/refunds/%s", extChargeId, refundId2)))
                .assertThat("$.results[1].links.payment_url.href", is(format("http://publicapi.test.localhost/v1/payments/%s", extChargeId)))
                .assertThat("$._links.self.href", is("http://publicapi.test.localhost/v1/refunds?page=" + PAGE + "&display_size=" + DISPLAY_SIZE));
    }

    @Test
    @PactVerification({"connector"})
    @Pacts(pacts = {"publicapi-connector-search-refunds-with-page-and-display-when-no-refunds-exist"})
    public void getAllRefundsShouldReturnNoRefundsWhenThereAreNone() {
        Account account = new Account(ACCOUNT_ID, CARD);
        RefundsParams params = new RefundsParams(PAGE, "1");
        Response response = searchRefundsService.getAllRefunds(account, params);
        JsonAssert.with(response.getEntity().toString())
                .assertThat("count", is(0))
                .assertThat("total", is(0))
                .assertThat("page", is(1));
    }

    @Test
    public void getSearchResponse_shouldThrowBadRefundsRequestExceptionWhenAccountIsDD() {
        Account account = new Account(ACCOUNT_ID, DIRECT_DEBIT);

        expectedException.expect(BadRefundsRequestException.class);
        expectedException.expect(
                aBadRefundsRequestExceptionWithError(
                        "P1102",
                        "Searching all refunds are not currently supported for direct debit accounts."));
        searchRefundsService.getAllRefunds(account, params);
    }

    @Test
    public void getSearchResponse_shouldThrowRefundsValidationExceptionWhenParamsAreInvalid() {
        Account account = new Account(ACCOUNT_ID, CARD);
        String invalid = "invalid_param";
        RefundsParams params = new RefundsParams(invalid, invalid);

        expectedException.expect(RefundsValidationException.class);
        expectedException.expect(aValidationExceptionContaining(
                "P1101",
                format("Invalid parameters: %s. See Public API documentation for the correct data formats",
                        "page, display_size")));
        searchRefundsService.getAllRefunds(account, params);
    }
}
