package uk.gov.pay.api.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.gov.pay.api.model.CreatePaymentRefundRequest;
import uk.gov.pay.api.model.CreatePaymentRequest;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static uk.gov.pay.api.json.RequestJsonParser.paymentRefundRequestValueOf;
import static uk.gov.pay.api.json.RequestJsonParser.paymentRequestValueOf;
import static uk.gov.pay.api.matcher.BadRequestExceptionMatcher.aBadRequestExceptionWithError;

public class RequestJsonParserTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void parsePaymentRequest_shouldParseSuccessfully() throws Exception {

        String payload = "{" +
                "  \"amount\" : -1000," +
                "  \"reference\" : \"Some reference\"," +
                "  \"description\" : \"Some description\"," +
                "  \"return_url\" : \"http://somewhere.gov.uk/rainbow/1\"" +
                "}";

        JsonNode jsonNode = objectMapper.readTree(payload);

        CreatePaymentRequest createPaymentRequest = paymentRequestValueOf(jsonNode);

        assertThat(createPaymentRequest, is(notNullValue()));
        assertThat(createPaymentRequest.getAmount(), is(-1000));
        assertThat(createPaymentRequest.getReference(), is("Some reference"));
        assertThat(createPaymentRequest.getDescription(), is("Some description"));
        assertThat(createPaymentRequest.getReturnUrl(), is("http://somewhere.gov.uk/rainbow/1"));
    }

    @Test
    public void parsePaymentRequest_whenDynamic3dsIsSupplied() throws IOException {
        String payload = "{" +
                "  \"amount\" : -1000," +
                "  \"reference\" : \"Some reference\"," +
                "  \"description\" : \"Some description\"," +
                "  \"return_url\" : \"http://somewhere.gov.uk/rainbow/1\"," +
                "  \"dynamic_3ds\" : true" +
                "}";

        JsonNode jsonNode = objectMapper.readTree(payload);

        CreatePaymentRequest createPaymentRequest = paymentRequestValueOf(jsonNode);

        assertThat(createPaymentRequest, is(notNullValue()));
        assertThat(createPaymentRequest.getAmount(), is(-1000));
        assertThat(createPaymentRequest.getReference(), is("Some reference"));
        assertThat(createPaymentRequest.getDescription(), is("Some description"));
        assertThat(createPaymentRequest.getReturnUrl(), is("http://somewhere.gov.uk/rainbow/1"));
        assertThat(createPaymentRequest.getDynamic3ds(), is("true"));

    }

    @Test
    public void parsePaymentRefundRequest_shouldParseSuccessfully() throws Exception {

        String payload = "{" +
                "  \"amount\" : 1000" +
                "}";

        JsonNode jsonNode = objectMapper.readTree(payload);

        CreatePaymentRefundRequest createPaymentRefundRequest = paymentRefundRequestValueOf(jsonNode);

        assertThat(createPaymentRefundRequest, is(notNullValue()));
        assertThat(createPaymentRefundRequest.getAmount(), is(1000));
    }

    @Test
    public void parsePaymentRequest_whenExpectedStringFieldIsNotAString() throws Exception {

        String payload = "{" +
                "  \"amount\" : -1000," +
                "  \"reference\" : 1234," +
                "  \"description\" : \"Some description\"," +
                "  \"return_url\" : \"http://somewhere.gov.uk/rainbow/1\"" +
                "}";

        JsonNode jsonNode = objectMapper.readTree(payload);

        expectedException.expect(aBadRequestExceptionWithError("P0102", "Invalid attribute value: reference. Must be a valid string format"));

        paymentRequestValueOf(jsonNode);
    }

    @Test
    public void parsePaymentRequest_whenIsNotAStringReturnUrl_shouldOverrideFormattingErrorMessage() throws Exception {

        String payload = "{" +
                "  \"amount\" : -1000," +
                "  \"reference\" : \"Some reference\"," +
                "  \"description\" : \"Some description\"," +
                "  \"return_url\" : 1234" +
                "}";

        JsonNode jsonNode = objectMapper.readTree(payload);

        expectedException.expect(aBadRequestExceptionWithError("P0102", "Invalid attribute value: return_url. Must be a valid URL format"));

        paymentRequestValueOf(jsonNode);
    }

    @Test
    public void parsePaymentRequest_whenAFieldIsNullValue() throws Exception {

        String payload = "{" +
                "  \"amount\" : -1000," +
                "  \"reference\" : null," +
                "  \"description\" : \"Some description\"," +
                "  \"return_url\" : 1234" +
                "}";

        JsonNode jsonNode = objectMapper.readTree(payload);

        expectedException.expect(aBadRequestExceptionWithError("P0101", "Missing mandatory attribute: reference"));

        paymentRequestValueOf(jsonNode);
    }

    @Test
    public void parsePaymentRefundRequest_whenAFieldIsNullValue() throws Exception {

        String payload = "{" +
                "  \"amount\" : null" +
                "}";

        JsonNode jsonNode = objectMapper.readTree(payload);

        expectedException.expect(aBadRequestExceptionWithError("P0601", "Missing mandatory attribute: amount"));

        paymentRefundRequestValueOf(jsonNode);
    }

    @Test
    public void parsePaymentRequest_whenAFieldIsMissing() throws Exception {

        String payload = "{" +
                "  \"amount\" : -1000," +
                "  \"description\" : \"Some description\"," +
                "  \"return_url\" : 1234" +
                "}";

        JsonNode jsonNode = objectMapper.readTree(payload);

        expectedException.expect(aBadRequestExceptionWithError("P0101", "Missing mandatory attribute: reference"));

        paymentRequestValueOf(jsonNode);
    }

    @Test
    public void parsePaymentRefundRequest_whenAFieldIsMissing() throws Exception {

        String payload = "{}";

        JsonNode jsonNode = objectMapper.readTree(payload);

        expectedException.expect(aBadRequestExceptionWithError("P0601", "Missing mandatory attribute: amount"));

        paymentRefundRequestValueOf(jsonNode);
    }

}
