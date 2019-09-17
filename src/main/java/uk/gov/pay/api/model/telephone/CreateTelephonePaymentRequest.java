package uk.gov.pay.api.model.telephone;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import uk.gov.pay.api.utils.JsonStringBuilder;
import uk.gov.pay.api.validation.ValidCardExpiryDate;
import uk.gov.pay.api.validation.ValidCardFirstSixDigits;
import uk.gov.pay.api.validation.ValidCardLastFourDigits;
import uk.gov.pay.api.validation.ValidCardType;
import uk.gov.pay.api.validation.ValidZonedDateTime;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateTelephonePaymentRequest {

    public static final int REFERENCE_MAX_LENGTH = 255;
    public static final int AMOUNT_MAX_VALUE = 10000000;
    public static final int AMOUNT_MIN_VALUE = 0;
    public static final int DESCRIPTION_MAX_LENGTH = 255;
    
    @Min(value = AMOUNT_MIN_VALUE, message = "Must be greater than or equal to 1")
    @Max(value = AMOUNT_MAX_VALUE, message = "Must be less than or equal to {value}")
    @NotNull(message = "Field [amount] cannot be null")
    private int amount;
    
    @Size(max = REFERENCE_MAX_LENGTH, message = "Must be less than or equal to {max} characters length")
    @NotNull(message = "Field [reference] cannot be null")
    private String reference;
    
    @Size(max = DESCRIPTION_MAX_LENGTH, message = "Must be less than or equal to {max} characters length")
    @NotNull(message = "Field [description] cannot be null")
    private String description;

    @JsonProperty("created_date")
    @ValidZonedDateTime(message = "Field [created_date] must be a valid ISO-8601 time and date format")
    private String createdDate;

    @JsonProperty("authorised_date")
    @ValidZonedDateTime(message = "Field [authorised_date] must be a valid ISO-8601 time and date format")
    private String authorisedDate;
    
    @NotNull(message = "Field [processor_id] cannot be null")
    private String processorId;
    
    @NotNull(message = "Field [provider_id] cannot be null")
    private String providerId;

    @JsonProperty("auth_code")
    private String authCode;
    
    @Valid
    @NotNull(message = "Field [payment_outcome] cannot be null")
    private PaymentOutcome paymentOutcome;
    
    @NotNull(message = "Field [card_type] cannot be null")
    @ValidCardType(message = "Field [card_type] must be either master-card, visa, maestro, diners-club or american-express")
    private String cardType;

    @JsonProperty("name_on_card")
    private String nameOnCard;

    @JsonProperty("email_address")
    private String emailAddress;

    @NotNull(message = "Field [card_expiry] cannot be null")
    @ValidCardExpiryDate(message = "Field [card_expiry] must have valid MM/YY")
    private String cardExpiry;
    
    @NotNull(message = "Field [last_four_digits] cannot be null")
    @ValidCardLastFourDigits(message = "Field [last_four_digits] must be exactly 4 digits")
    private String lastFourDigits;
    
    @NotNull(message = "Field [first_six_digits] cannot be null")
    @ValidCardFirstSixDigits(message = "Field [first_six_digits] must be exactly 6 digits")
    private String firstSixDigits;

    @JsonProperty("telephone_number")
    private String telephoneNumber;

    public String toConnectorPayload() {
        JsonStringBuilder request = new JsonStringBuilder()
                .add("amount", this.getAmount())
                .add("reference", this.getReference())
                .add("description", this.getDescription())
                .add("processor_id", this.getProcessorId())
                .add("provider_id", this.getProviderId())
                .add("card_type", this.getCardType())
                .add("card_expiry", this.getCardExpiry())
                .add("last_four_digits", this.getLastFourDigits())
                .add("first_six_digits", this.getFirstSixDigits())
                .addToMap("payment_outcome", "status", this.getPaymentOutcome().getStatus());
        this.getPaymentOutcome().getCode().ifPresent(code -> request.addToMap("payment_outcome", "code", code));
        this.getPaymentOutcome().getSupplemental().ifPresent(supplemental -> {
                    supplemental.getErrorCode().ifPresent(errorCode -> request.addToNestedMap("error_code", errorCode, "payment_outcome", "supplemental"));
                    supplemental.getErrorMessage().ifPresent(errorMessage -> request.addToNestedMap("error_message", errorMessage, "payment_outcome", "supplemental"));
                }
        );

        this.getCreatedDate().ifPresent(createdDate -> request.add("created_date", createdDate));
        this.getAuthorisedDate().ifPresent(authorisedDate -> request.add("authorised_date", authorisedDate));
        this.getAuthCode().ifPresent(authCode -> request.add("auth_code", authCode));
        this.getNameOnCard().ifPresent(nameOnCard -> request.add("name_on_card", nameOnCard));
        this.getEmailAddress().ifPresent(emailAddress -> request.add("email_address", emailAddress));
        this.getTelephoneNumber().ifPresent(telephoneNumber -> request.add("telephone_number", telephoneNumber));

        return request.build();
    }

    public CreateTelephonePaymentRequest() {
        // To enable Jackson serialisation we need a default constructor
    }

    public CreateTelephonePaymentRequest(int amount, String reference, String description, String createdDate, String authorisedDate, String processorId, String providerId, String authCode, PaymentOutcome paymentOutcome, String cardType, String nameOnCard, String emailAddress, String cardExpiry, String lastFourDigits, String firstSixDigits, String telephoneNumber) {
        // For testing deserialization
        this.amount = amount;
        this.reference = reference;
        this.description = description;
        this.createdDate = createdDate;
        this.authorisedDate = authorisedDate;
        this.processorId = processorId;
        this.providerId = providerId;
        this.authCode = authCode;
        this.paymentOutcome = paymentOutcome;
        this.cardType = cardType;
        this.nameOnCard = nameOnCard;
        this.emailAddress = emailAddress;
        this.cardExpiry = cardExpiry;
        this.lastFourDigits = lastFourDigits;
        this.firstSixDigits = firstSixDigits;
        this.telephoneNumber = telephoneNumber;
    }
    
    public CreateTelephonePaymentRequest(Builder builder) {
        this.amount = builder.amount;
        this.reference = builder.reference;
        this.description = builder.description;
        this.createdDate = builder.createdDate;
        this.authorisedDate = builder.authorisedDate;
        this.processorId = builder.processorId;
        this.providerId = builder.providerId;
        this.authCode = builder.authCode;
        this.paymentOutcome = builder.paymentOutcome;
        this.cardType = builder.cardType;
        this.nameOnCard = builder.nameOnCard;
        this.emailAddress = builder.emailAddress;
        this.cardExpiry = builder.cardExpiry;
        this.lastFourDigits = builder.lastFourDigits;
        this.firstSixDigits = builder.firstSixDigits;
        this.telephoneNumber = builder.telephoneNumber;
    }

    public int getAmount() {
        return amount;
    }

    public String getReference() {
        return reference;
    }

    public String getDescription() {
        return description;
    }
    
    public Optional<String> getCreatedDate() {
        return Optional.ofNullable(createdDate);
    }
    
    public Optional<String> getAuthorisedDate() {
        return Optional.ofNullable(authorisedDate);
    }

    public String getProcessorId() {
        return processorId;
    }

    public String getProviderId() {
        return providerId;
    }
    
    public Optional<String> getAuthCode() {
        return Optional.ofNullable(authCode);
    }
    
    public PaymentOutcome getPaymentOutcome() {
        return paymentOutcome;
    }

    public String getCardType() {
        return cardType;
    }
    
    public Optional<String> getNameOnCard() {
        return Optional.ofNullable(nameOnCard);
    }
    
    public Optional<String> getEmailAddress() {
        return Optional.ofNullable(emailAddress);
    }

    public String getCardExpiry() {
        return cardExpiry;
    }

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public String getFirstSixDigits() {
        return firstSixDigits;
    }
    
    public Optional<String> getTelephoneNumber() {
        return Optional.ofNullable(telephoneNumber);
    }

    public static class Builder {
        private int amount;

        private String reference;

        private String description;

        private String createdDate;

        private String authorisedDate;

        private String processorId;

        private String providerId;

        private String authCode;

        private PaymentOutcome paymentOutcome;

        private String cardType;

        private String nameOnCard;

        private String emailAddress;

        private String cardExpiry;

        private String lastFourDigits;

        private String firstSixDigits;

        private String telephoneNumber;

        public Builder withAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder withReference(String reference) {
            this.reference = reference;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withCreatedDate(String createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder withAuthorisedDate(String authorisedDate) {
            this.authorisedDate = authorisedDate;
            return this;
        }

        public Builder withProcessorId(String processorId) {
            this.processorId = processorId;
            return this;
        }

        public Builder withProviderId(String providerId) {
            this.providerId = providerId;
            return this;
        }

        public Builder withAuthCode(String authCode) {
            this.authCode = authCode;
            return this;
        }

        public Builder withPaymentOutcome(PaymentOutcome paymentOutcome) {
            this.paymentOutcome = paymentOutcome;
            return this;
        }

        public Builder withCardType(String cardType) {
            this.cardType = cardType;
            return this;
        }

        public Builder withNameOnCard(String nameOnCard) {
            this.nameOnCard = nameOnCard;
            return this;
        }

        public Builder withEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public Builder withCardExpiry(String cardExpiry) {
            this.cardExpiry = cardExpiry;
            return this;
        }

        public Builder withLastFourDigits(String lastFourDigits) {
            this.lastFourDigits = lastFourDigits;
            return this;
        }

        public Builder withFirstSixDigits(String firstSixDigits) {
            this.firstSixDigits = firstSixDigits;
            return this;
        }

        public Builder withTelephoneNumber(String telephoneNumber) {
            this.telephoneNumber = telephoneNumber;
            return this;
        }

        public CreateTelephonePaymentRequest build() {
            return new CreateTelephonePaymentRequest(this);
        }
    }
}
