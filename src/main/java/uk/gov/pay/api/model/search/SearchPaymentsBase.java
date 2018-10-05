package uk.gov.pay.api.model.search;

import black.door.hate.HalRepresentation;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.pay.api.app.config.PublicApiConfig;
import uk.gov.pay.api.auth.Account;
import uk.gov.pay.api.exception.BadRequestException;
import uk.gov.pay.api.exception.SearchChargesException;
import uk.gov.pay.api.model.PaymentErrorCodes;
import uk.gov.pay.api.model.generated.Link;
import uk.gov.pay.api.service.ConnectorUriGenerator;
import uk.gov.pay.api.service.PaymentUriGenerator;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static uk.gov.pay.api.model.PaymentErrorBuilder.aPaymentError;

public abstract class SearchPaymentsBase {

    private static final String PAYMENTS_PATH = "/v1/payments";
    protected final Client client;
    protected final PublicApiConfig configuration;
    protected final ConnectorUriGenerator connectorUriGenerator;
    protected final PaymentUriGenerator paymentUriGenerator;
    protected final ObjectMapper objectMapper;
    protected final String baseUrl;

    public SearchPaymentsBase(Client client,
                              PublicApiConfig configuration,
                              ConnectorUriGenerator connectorUriGenerator,
                              PaymentUriGenerator paymentUriGenerator,
                              ObjectMapper objectMapper) {
        this.client = client;
        this.configuration = configuration;
        this.connectorUriGenerator = connectorUriGenerator;
        this.paymentUriGenerator = paymentUriGenerator;
        this.objectMapper = objectMapper;
        this.baseUrl = configuration.getBaseUrl();
    }
    
    public abstract Response getSearchResponse(Account account, Map<String, String> queryParams);
    protected abstract Set<String> getSupportedSearchParams();
    
    protected void validateSupportedSearchParams(Map<String, String> queryParams) {
        queryParams.entrySet().stream()
                .filter(this::isUnsupportedParamWithNonBlankValue)
                .findFirst()
                .ifPresent(invalidParam -> {
                    throw new BadRequestException(aPaymentError(PaymentErrorCodes.SEARCH_PAYMENTS_VALIDATION_ERROR, invalidParam.getKey()));
                });
    }
    
    private boolean isUnsupportedParamWithNonBlankValue(Map.Entry<String, String> queryParam) {
        return !getSupportedSearchParams().contains(queryParam.getKey()) && isNotBlank(queryParam.getValue());
    }
    
    protected HalRepresentation.HalRepresentationBuilder decoratePagination(HalRepresentation.HalRepresentationBuilder halRepresentationBuilder, IPaymentSearchPagination pagination) {
        try {
            halRepresentationBuilder
                    .addProperty("count", pagination.getCount())
                    .addProperty("total", pagination.getTotal())
                    .addProperty("page", pagination.getPage());
            addLink(halRepresentationBuilder, "self", transformIntoPublicUri(baseUrl, pagination.getLinks().getSelf()));
            addLink(halRepresentationBuilder, "first_page", transformIntoPublicUri(baseUrl, pagination.getLinks().getFirstPage()));
            addLink(halRepresentationBuilder, "last_page", transformIntoPublicUri(baseUrl, pagination.getLinks().getLastPage()));
            addLink(halRepresentationBuilder, "prev_page", transformIntoPublicUri(baseUrl, pagination.getLinks().getPrevPage()));
            addLink(halRepresentationBuilder, "next_page", transformIntoPublicUri(baseUrl, pagination.getLinks().getNextPage()));
        } catch (URISyntaxException ex) {
            throw new SearchChargesException(ex);
        }
        return halRepresentationBuilder;
    }

    private void addLink(HalRepresentation.HalRepresentationBuilder halRepresentationBuilder, String name, URI uri) {
        if (uri != null) {
            halRepresentationBuilder.addLink(name, uri);
        }
    }

    private URI transformIntoPublicUri(String baseUrl, Link link) throws URISyntaxException {
        if (link == null)
            return null;

        return UriBuilder.fromUri(baseUrl)
                .path(PAYMENTS_PATH)
                .replaceQuery(new URI(link.getHref()).getQuery())
                .build();
    }
}
