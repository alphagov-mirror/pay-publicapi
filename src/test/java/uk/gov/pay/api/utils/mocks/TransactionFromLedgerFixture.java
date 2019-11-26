package uk.gov.pay.api.utils.mocks;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import uk.gov.pay.api.model.CardDetails;
import uk.gov.pay.api.model.PaymentConnectorResponseLink;
import uk.gov.pay.api.model.PaymentState;
import uk.gov.pay.api.model.RefundSummary;
import uk.gov.pay.api.model.SettlementSummary;
import uk.gov.pay.commons.model.SupportedLanguage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TransactionFromLedgerFixture {
    private String transactionId;
    private String returnUrl;
    private String paymentProvider;
    private List<PaymentConnectorResponseLink> links = new ArrayList<>();
    private RefundSummary refundSummary;
    private SettlementSummary settlementSummary;
    private CardDetails cardDetails;
    private Long amount;
    private PaymentState state;
    private String description;
    private String reference;
    private String email;
    private String language;
    private boolean delayedCapture;
    private Long corporateCardSurcharge;
    private Long totalAmount;
    private Long fee;
    private Long netAmount;
    private String createdDate;
    private String gatewayTransactionId;
    private Map<String, Object> metadata;

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public List<PaymentConnectorResponseLink> getLinks() {
        return links;
    }

    public RefundSummary getRefundSummary() {
        return refundSummary;
    }

    public SettlementSummary getSettlementSummary() {
        return settlementSummary;
    }

    public CardDetails getCardDetails() {
        return cardDetails;
    }

    public String getDescription() {
        return description;
    }

    public String getReference() {
        return reference;
    }

    public String getEmail() {
        return email;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isDelayedCapture() {
        return delayedCapture;
    }

    public Long getCorporateCardSurcharge() {
        return corporateCardSurcharge;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public Long getFee() {
        return fee;
    }

    public Long getNetAmount() {
        return netAmount;
    }

    public String getGatewayTransactionId() {
        return gatewayTransactionId;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public TransactionFromLedgerFixture(TransactionFromLedgerBuilder builder) {
        this.amount = builder.amount;
        this.state = builder.state;
        this.createdDate = builder.createdDate;
        this.transactionId = builder.transactionId;
        this.returnUrl = builder.returnUrl;
        this.paymentProvider = builder.paymentProvider;
        this.links = builder.links;
        this.refundSummary = builder.refundSummary;
        this.settlementSummary = builder.settlementSummary;
        this.cardDetails = builder.cardDetails;
        this.description = builder.description;
        this.reference = builder.description;
        this.email = builder.email;
        this.language = builder.language;
        this.delayedCapture = builder.delayedCapture;
        this.corporateCardSurcharge = builder.corporateCardSurcharge;
        this.totalAmount = builder.totalAmount;
        this.fee = builder.fee;
        this.netAmount = builder.netAmount;
        this.gatewayTransactionId = builder.gatewayTransactionId;
        this.metadata = builder.metadata;
    }

    public Long getAmount() {
        return amount;
    }

    public PaymentState getState() {
        return state;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getTransactionId() {
        return transactionId;
    }


    public static final class TransactionFromLedgerBuilder {
        private String transactionId;
        private String returnUrl;
        private String paymentProvider;
        private List<PaymentConnectorResponseLink> links = new ArrayList<>();
        private RefundSummary refundSummary;
        private SettlementSummary settlementSummary;
        private CardDetails cardDetails;
        private Long amount;
        private PaymentState state;
        private String description;
        private String reference;
        private String email;
        private String language;
        private boolean delayedCapture;
        private Long corporateCardSurcharge;
        private Long totalAmount;
        private Long fee;
        private Long netAmount;
        private String createdDate;
        private String gatewayTransactionId;
        private Map<String, Object> metadata;

        private TransactionFromLedgerBuilder() {
        }

        public static TransactionFromLedgerBuilder aTransactionFromLedgerFixture() {
            return new TransactionFromLedgerBuilder();
        }

        public TransactionFromLedgerBuilder withAmount(Long amount) {
            this.amount = amount;
            return this;
        }

        public TransactionFromLedgerBuilder withState(PaymentState state) {
            this.state = state;
            return this;
        }

        public TransactionFromLedgerBuilder withCreatedDate(String createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public TransactionFromLedgerBuilder withTransactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public TransactionFromLedgerBuilder withReturnUrl(String returnUrl) {
            this.returnUrl = returnUrl;
            return this;
        }

        public TransactionFromLedgerBuilder withPaymentProvider(String paymentProvider) {
            this.paymentProvider = paymentProvider;
            return this;
        }

        public TransactionFromLedgerBuilder withLinks(List<PaymentConnectorResponseLink> links) {
            this.links = links;
            return this;
        }

        public TransactionFromLedgerBuilder withRefundSummary(RefundSummary refundSummary) {
            this.refundSummary = refundSummary;
            return this;
        }

        public TransactionFromLedgerBuilder withSettlementSummary(SettlementSummary settlementSummary) {
            this.settlementSummary = settlementSummary;
            return this;
        }

        public TransactionFromLedgerBuilder withCardDetails(CardDetails cardDetails) {
            this.cardDetails = cardDetails;
            return this;
        }

        public TransactionFromLedgerBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public TransactionFromLedgerBuilder withReference(String reference) {
            this.reference = reference;
            return this;
        }

        public TransactionFromLedgerBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public TransactionFromLedgerBuilder withLanguage(SupportedLanguage language) {
            this.language = language.toString();
            return this;
        }

        public TransactionFromLedgerBuilder withDelayedCapture(boolean delayedCapture) {
            this.delayedCapture = delayedCapture;
            return this;
        }

        public TransactionFromLedgerBuilder withCorporateCardSurcharge(Long corporateCardSurcharge) {
            this.corporateCardSurcharge = corporateCardSurcharge;
            return this;
        }

        public TransactionFromLedgerBuilder withTotalAmount(Long totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public TransactionFromLedgerBuilder withFee(Long fee) {
            this.fee = fee;
            return this;
        }

        public TransactionFromLedgerBuilder withNetAmount(Long netAmount) {
            this.netAmount = netAmount;
            return this;
        }

        public TransactionFromLedgerBuilder withGatewayTransactionId(String gatewayTransactionId) {
            this.gatewayTransactionId = gatewayTransactionId;
            return this;
        }

        public TransactionFromLedgerBuilder withMetadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public TransactionFromLedgerFixture build() {
            return new TransactionFromLedgerFixture(this);
        }
    }
}
