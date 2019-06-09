package uk.gov.pay.api.model.directdebit.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.gov.pay.api.model.response.HalLink;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectDebitTransactionFromResponse {
    private Long amount;
    @JsonProperty("transaction_id")
    private String transactionId;
    private DirectDebitPaymentState state;
    private String description;
    private String reference;
    private String email;
    private String name;
    @JsonProperty(value = "created_date")
    private String createdDate;
    @JsonProperty("agreement_id")
    private String agreementId;
    @JsonProperty("_links")
    private List<HalLink> links = new ArrayList<>();

    public Long getAmount() { return amount; }

    public String getTransactionId() { return transactionId; }

    public DirectDebitPaymentState getState() { return state; }

    public String getDescription() { return description; }

    public String getReference() { return reference; }

    public String getEmail() { return email; }

    public String getName() { return name; }

    public String getCreatedDate() { return createdDate; }

    public String getAgreementId() { return agreementId; }

    public List<HalLink> getLinks() { return links; }
}
