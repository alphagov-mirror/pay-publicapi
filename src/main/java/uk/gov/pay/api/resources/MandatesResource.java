package uk.gov.pay.api.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.pay.api.app.config.PublicApiConfig;
import uk.gov.pay.api.auth.Account;
import uk.gov.pay.api.model.PaymentError;
import uk.gov.pay.api.model.directdebit.mandates.CreateMandateRequest;
import uk.gov.pay.api.model.directdebit.mandates.MandateError;
import uk.gov.pay.api.model.directdebit.mandates.MandateResponse;
import uk.gov.pay.api.model.search.directdebit.DirectDebitSearchMandatesParams;
import uk.gov.pay.api.model.search.directdebit.SearchMandateResponse;
import uk.gov.pay.api.resources.error.ApiErrorResponse;
import uk.gov.pay.api.service.MandatesService;
import uk.gov.pay.api.service.directdebit.DirectDebitMandateSearchService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/")
@Tag(name = "Direct Debit")
@Produces({"application/json"})
public class MandatesResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MandatesResource.class);

    private final String baseUrl;
    private final MandatesService mandateService;
    private final DirectDebitMandateSearchService mandateSearchService;

    @Inject
    public MandatesResource(PublicApiConfig configuration, MandatesService mandateService, DirectDebitMandateSearchService mandateSearchService) {
        this.baseUrl = configuration.getBaseUrl();
        this.mandateService = mandateService;
        this.mandateSearchService = mandateSearchService;
    }

    @GET
    @Timed
    @Path("/v1/directdebit/mandates/{mandateId}")
    @Produces(APPLICATION_JSON)
    @Operation(security = {@SecurityRequirement(name = "BearerAuth")},
            operationId = "Get a mandate",
            summary = "Find mandate by ID",
            description = "Return information about the mandate. " +
                    "The Authorisation token needs to be specified in the 'Authorization' header " +
                    "as 'Authorization: Bearer YOUR_API_KEY_HERE'",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = MandateResponse.class))),
                    @ApiResponse(responseCode = "401",
                            description = "Credentials are required to access this resource"),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(schema = @Schema(implementation = MandateError.class))),
                    @ApiResponse(responseCode = "429", description = "Too many requests",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Downstream system error",
                            content = @Content(schema = @Schema(implementation = MandateError.class)))
            }
    )
    public Response getMandate(@Parameter(hidden = true) @Auth Account account,
                               @Parameter(required = true, description = "mandateId") @PathParam("mandateId") String mandateId) {
        LOGGER.info("Mandate get request - [ {} ]", mandateId);
        MandateResponse getMandateResponse = mandateService.get(account, mandateId);
        LOGGER.info("Mandate returned: [ {} ]", getMandateResponse);
        return Response.ok().entity(getMandateResponse).build();
    }

    @GET
    @Timed
    @Path("/v1/directdebit/mandates/")
    @Produces(APPLICATION_JSON)
    @Operation(security = {@SecurityRequirement(name = "BearerAuth")},
            operationId = "Search mandates",
            summary = "Search mandates",
            description = "Searches for mandates with the parameters provided. " +
                    "The Authorisation token needs to be specified in the 'Authorization' header " +
                    "as 'Authorization: Bearer YOUR_API_KEY_HERE'",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = SearchMandateResponse.class))),
                    @ApiResponse(responseCode = "401",
                            description = "Credentials are required to access this resource"),
                    @ApiResponse(responseCode = "404", description = "Not found",
                            content = @Content(schema = @Schema(implementation = MandateError.class))),
                    @ApiResponse(responseCode = "422", 
                            description = "Invalid parameters: from_date, to_date, state, page, display_size. See Public API documentation for the correct data formats",
                            content = @Content(schema = @Schema(implementation = PaymentError.class))),
                    @ApiResponse(responseCode = "429", description = "Too many requests",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Downstream system error",
                            content = @Content(schema = @Schema(implementation = MandateError.class)))
            }
    )
    public Response searchMandates(@Parameter(hidden = true) @Auth Account account,
                                   @Valid @BeanParam DirectDebitSearchMandatesParams mandateSearchParams) {
        LOGGER.info("Mandate search request - [ {} ]", mandateSearchParams.toString());
        SearchMandateResponse searchMandatesResponse = mandateSearchService.search(account, mandateSearchParams);

        LOGGER.info("Mandate search returned: [ {} ]", searchMandatesResponse);
        return Response.ok().entity(searchMandatesResponse).build();
    }

    @POST
    @Timed
    @Path("/v1/directdebit/mandates")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Operation(security = {@SecurityRequirement(name = "BearerAuth")},
            operationId = "Create a mandate",
            summary = "Create a new mandate",
            description = "Create a new mandate for the account associated to the Authorisation token. " +
                    "The Authorisation token needs to be specified in the 'Authorization' header " +
                    "as 'Authorization: Bearer YOUR_API_KEY_HERE'",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created",
                            content = @Content(schema = @Schema(implementation = MandateResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = PaymentError.class))),
                    @ApiResponse(responseCode = "401",
                            description = "Credentials are required to access this resource"),
                    @ApiResponse(responseCode = "429", description = "Too many requests",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Downstream system error",
                            content = @Content(schema = @Schema(implementation = PaymentError.class)))
            }
    )
    public Response createMandate(@Parameter(hidden = true) @Auth Account account,
                                  @Parameter(required = true, description = "requestPayload")
                                  @Valid CreateMandateRequest createMandateRequest) {
        LOGGER.info("Mandate create request - [ {} ]", createMandateRequest);
        MandateResponse createMandateResponse = mandateService.create(account, createMandateRequest);
        URI mandateUri = UriBuilder.fromUri(baseUrl)
                .path("/v1/directdebit/mandates/{mandateId}")
                .build(createMandateResponse.getMandateId());
        LOGGER.info("Mandate returned (created): [ {} ]", createMandateResponse);
        return Response.created(mandateUri).entity(createMandateResponse).build();
    }
}
