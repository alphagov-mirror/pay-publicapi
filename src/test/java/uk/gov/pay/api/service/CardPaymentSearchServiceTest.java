package uk.gov.pay.api.service;

import au.com.dius.pact.consumer.PactVerification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonassert.JsonAssert;
import com.jayway.jsonassert.JsonAsserter;
import io.dropwizard.jackson.Jackson;
import org.jetbrains.annotations.NotNull;
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
import uk.gov.pay.api.model.TokenPaymentType;
import uk.gov.pay.commons.testing.pact.consumers.PactProviderRule;
import uk.gov.pay.commons.testing.pact.consumers.Pacts;

import javax.ws.rs.core.Response;

import static com.jayway.jsonassert.JsonAssert.collectionWithSize;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CardPaymentSearchServiceTest {

    @Rule
    public PactProviderRule connectorRule = new PactProviderRule("connector", this);

    @Mock
    private PublicApiConfig configuration;
    private PaymentSearchService paymentSearchService;
    private ObjectMapper objectMapper = Jackson.newObjectMapper();

    @Before
    public void setUp() {
        when(configuration.getConnectorUrl()).thenReturn(connectorRule.getUrl());

        when(configuration.getBaseUrl()).thenReturn("http://publicapi.test.localhost/");

        paymentSearchService = new PaymentSearchService(
                RestClientFactory.buildClient(new RestClientConfig(false)),
                configuration,
                new ConnectorUriGenerator(configuration),
                new PaymentUriGenerator(),
                objectMapper);
    }
    
    @Test
    @PactVerification({"connector"})
    @Pacts(pacts = {"publicapi-connector-search-payment-by-last-digits-card-number"})
    public void searchShouldReturnAResponseWithOneTransaction_whenFilteringByLastDigitsCardNumber() {
        Account account = new Account("123456", TokenPaymentType.CARD);
        Response response =
                paymentSearchService.doSearch(account, null, null,
                        null, null, null,
                        null, null, null,
                        null, null, null, "1234");
        jsonAssert(response)
                .assertThat("count", is(1))
                .assertThat("total", is(1))
                .assertThat("page", is(1))
                .assertThat("results", is(collectionWithSize(equalTo(1))))
                .assertThat("results[0]", hasKey("amount"))
                .assertThat("results[0]", hasKey("state"))
                .assertThat("results[0]", hasKey("reference"))
                .assertThat("results[0]", hasKey("email"))
                .assertThat("results[0].card_details", hasKey("cardholder_name"))
                .assertThat("results[0].card_details", hasKey("first_digits_card_number"))
                .assertThat("results[0].card_details.last_digits_card_number", is("1234"))
                .assertThat("results[0].state", hasKey("status"))
                .assertThat("results[0].state", hasKey("finished"))
                .assertThat("results[0]", hasKey("_links"))
                .assertThat("_links", hasKey("self"))
                .assertThat("_links", hasKey("first_page"))
                .assertThat("_links", hasKey("last_page"))
                .assertNotDefined("_links.next_page")
                .assertNotDefined("_links.prev_page");
    }

    @Test
    @PactVerification({"connector"})
    @Pacts(pacts = {"publicapi-connector-search-payment-by-first-digits-card-number"})
    public void searchShouldReturnAResponseWithOneTransaction_whenFilteringByFirstDigitsCardNumber() {
        Account account = new Account("123456", TokenPaymentType.CARD);
        Response response =
                paymentSearchService.doSearch(account, null, null,
                        null, null, null,
                        null, null, null,
                        null, null, "123456", null);
        jsonAssert(response)
                .assertThat("count", is(1))
                .assertThat("total", is(1))
                .assertThat("page", is(1))
                .assertThat("results", is(collectionWithSize(equalTo(1))))
                .assertThat("results[0]", hasKey("amount"))
                .assertThat("results[0]", hasKey("state"))
                .assertThat("results[0]", hasKey("reference"))
                .assertThat("results[0]", hasKey("email"))
                .assertThat("results[0].card_details", hasKey("cardholder_name"))
                .assertThat("results[0].card_details.first_digits_card_number", is("123456"))
                .assertThat("results[0].card_details", hasKey("last_digits_card_number"))
                .assertThat("results[0].state", hasKey("status"))
                .assertThat("results[0].state", hasKey("finished"))
                .assertThat("results[0]", hasKey("_links"))
                .assertThat("_links", hasKey("self"))
                .assertThat("_links", hasKey("first_page"))
                .assertThat("_links", hasKey("last_page"))
                .assertNotDefined("_links.next_page")
                .assertNotDefined("_links.prev_page");
    }

    @Test
    @PactVerification({"connector"})
    @Pacts(pacts = {"publicapi-connector-search-payment-by-cardholder-name"})
    public void searchShouldReturnAResponseWithOneTransaction_whenFilteringByCardHolderName() {
        Account account = new Account("123456", TokenPaymentType.CARD);
        Response response =
                paymentSearchService.doSearch(account, null, null,
                        null, null, null,
                        null, null, null,
                        null, "pay", null, null);
        jsonAssert(response)
                .assertThat("count", is(1))
                .assertThat("total", is(1))
                .assertThat("page", is(1))
                .assertThat("results", is(collectionWithSize(equalTo(1))))
                .assertThat("results[0]", hasKey("amount"))
                .assertThat("results[0]", hasKey("state"))
                .assertThat("results[0]", hasKey("reference"))
                .assertThat("results[0]", hasKey("email"))
                .assertThat("results[0].card_details.cardholder_name", is("Mr Payment"))
                .assertThat("results[0].card_details", hasKey("first_digits_card_number"))
                .assertThat("results[0].card_details", hasKey("last_digits_card_number"))
                .assertThat("results[0].state", hasKey("status"))
                .assertThat("results[0].state", hasKey("finished"))
                .assertThat("results[0]", hasKey("_links"))
                .assertThat("_links", hasKey("self"))
                .assertThat("_links", hasKey("first_page"))
                .assertThat("_links", hasKey("last_page"))
                .assertThat("results[0]._links.capture", is(nullValue()))
                .assertNotDefined("_links.next_page")
                .assertNotDefined("_links.prev_page");
    }

    @Test
    @PactVerification({"connector"})
    @Pacts(pacts = {"publicapi-connector-search-payment-with-charge-in-awaiting-capture-state"})
    public void searchShouldReturnAResponseWithCaptureLinkPresent() {
        Account account = new Account("123456", TokenPaymentType.CARD);
        Response response =
                paymentSearchService.doSearch(account, null, null,
                        null, null, null,
                        null, null, null,
                        null, null, null, null);
        jsonAssert(response)
                .assertThat("results[0]._links", hasKey("capture"))
                .assertThat("results[0]._links.capture.method", is("POST"));
    }

    
    @NotNull
    private JsonAsserter jsonAssert(Response response) {
        try {
            final String json = objectMapper.writeValueAsString(response.getEntity());
            return JsonAssert.with(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
