package uk.gov.pay.api.model.directdebit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import uk.gov.pay.api.model.DirectDebitPaymentState;

import java.util.Objects;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectDebitConnectorPaymentResponse {
    @JsonProperty
    @Schema(example = "1200", accessMode = READ_ONLY)
    private Long amount;
    
    @JsonProperty
    private DirectDebitPaymentState state;
    
    @JsonProperty("mandate_id")
    @Schema(example = "33890b55-b9ea-4e2f-90fd-77ae0e9009e2", accessMode = READ_ONLY)
    private String mandateId;
    
    @JsonProperty
    @Schema(example = "Your Service Description", accessMode = READ_ONLY)
    private String description;

    @JsonProperty
    @Schema(example = "your-reference", accessMode = READ_ONLY)
    private String reference;
    
    @JsonProperty("payment_id")
    @Schema(example = "payment id", accessMode = READ_ONLY)
    private String paymentExternalId;

    @JsonProperty("payment_provider")
    @Schema(example = "worldpay", accessMode = READ_ONLY)
    private String paymentProvider;

    @Schema(example = "2016-01-21T17:15:000Z", accessMode = READ_ONLY)
    @JsonProperty("created_date")
    private String createdDate;

    @JsonProperty("provider_id")
    @Schema(example = "provider id", accessMode = READ_ONLY)
    private String providerId;

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public String getPaymentExternalId() {
        return paymentExternalId;
    }

    public Long getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getReference() {
        return reference;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getMandateId() {
        return mandateId;
    }

    public String getProviderId() {
        return providerId;
    }

    public DirectDebitPaymentState getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectDebitConnectorPaymentResponse that = (DirectDebitConnectorPaymentResponse) o;
        return Objects.equals(paymentExternalId, that.paymentExternalId) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(paymentProvider, that.paymentProvider) &&
                Objects.equals(description, that.description) &&
                Objects.equals(reference, that.reference) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(providerId, that.providerId) &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentExternalId, amount, paymentProvider, description, reference, createdDate, 
                providerId, state);
    }

    @Override
    public String toString() {
        return "DirectDebitConnectorPaymentResponse{" +
                "paymentExternalId='" + paymentExternalId + '\'' +
                ", amount=" + amount +
                ", paymentProvider='" + paymentProvider + '\'' +
                ", reference='" + reference + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", state=" + state +
                ", provider_id=" + providerId +
                ", mandate_id=" + mandateId +
                ", description=" + description+
                '}';
    }

    public static final class DirectDebitConnectorCreatePaymentResponseBuilder {
        private String paymentExternalId;
        private Long amount;
        private String paymentProvider;
        private String description;
        private String reference;
        private String createdDate;
        private String mandateId;
        private String providerId;
        private DirectDebitPaymentState state;

        private DirectDebitConnectorCreatePaymentResponseBuilder() {
        }

        public static DirectDebitConnectorCreatePaymentResponseBuilder aDirectDebitConnectorPaymentResponse() {
            return new DirectDebitConnectorCreatePaymentResponseBuilder();
        }

        public DirectDebitConnectorCreatePaymentResponseBuilder withPaymentExternalId(String paymentExternalId) {
            this.paymentExternalId = paymentExternalId;
            return this;
        }

        public DirectDebitConnectorCreatePaymentResponseBuilder withAmount(Long amount) {
            this.amount = amount;
            return this;
        }

        public DirectDebitConnectorCreatePaymentResponseBuilder withPaymentProvider(String paymentProvider) {
            this.paymentProvider = paymentProvider;
            return this;
        }

        public DirectDebitConnectorCreatePaymentResponseBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public DirectDebitConnectorCreatePaymentResponseBuilder withReference(String reference) {
            this.reference = reference;
            return this;
        }

        public DirectDebitConnectorCreatePaymentResponseBuilder withCreatedDate(String createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public DirectDebitConnectorCreatePaymentResponseBuilder withState(DirectDebitPaymentState state) {
            this.state = state;
            return this;
        }
        
        public DirectDebitConnectorCreatePaymentResponseBuilder withMandateId(String mandateId) {
            this.mandateId = mandateId;
            return this;
        }
        
        public DirectDebitConnectorCreatePaymentResponseBuilder withProviderId(String providerId) {
            this.providerId = providerId;
            return this;
        }
 
        public DirectDebitConnectorPaymentResponse build() {
            DirectDebitConnectorPaymentResponse directDebitConnectorCreatePaymentResponse = new DirectDebitConnectorPaymentResponse();
            directDebitConnectorCreatePaymentResponse.createdDate = this.createdDate;
            directDebitConnectorCreatePaymentResponse.state = this.state;
            directDebitConnectorCreatePaymentResponse.amount = this.amount;
            directDebitConnectorCreatePaymentResponse.paymentExternalId = this.paymentExternalId;
            directDebitConnectorCreatePaymentResponse.description = this.description;
            directDebitConnectorCreatePaymentResponse.reference = this.reference;
            directDebitConnectorCreatePaymentResponse.paymentProvider = this.paymentProvider;
            directDebitConnectorCreatePaymentResponse.mandateId = this.mandateId;
            directDebitConnectorCreatePaymentResponse.providerId = this.providerId;
            return directDebitConnectorCreatePaymentResponse;
        }
    }
}
