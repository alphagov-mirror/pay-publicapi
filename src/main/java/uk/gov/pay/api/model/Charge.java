package uk.gov.pay.api.model;

import uk.gov.pay.commons.model.SupportedLanguage;
import uk.gov.pay.commons.model.charge.ExternalMetadata;

import java.util.List;
import java.util.Optional;

public class Charge {

    private String chargeId;

    private String returnUrl;

    private String paymentProvider;

    private List<PaymentConnectorResponseLink> links;

    private RefundSummary refundSummary;

    private SettlementSummary settlementSummary;

    private CardDetails cardDetails;

    private Long amount;

    private PaymentState state;

    private String description;

    private String reference;

    private String email;

    private SupportedLanguage language;

    private boolean delayedCapture;

    private Long corporateCardSurcharge;

    private Long totalAmount;

    private Long fee;

    private Long netAmount;

    private String createdDate;

    private String gatewayTransactionId;

    private ExternalMetadata metadata;

    public Charge(String chargeId, Long amount, PaymentState state, String returnUrl, String description,
                  String reference, String email, String paymentProvider, String createdDate,
                  SupportedLanguage language, boolean delayedCapture, RefundSummary refundSummary,
                  SettlementSummary settlementSummary, CardDetails cardDetails,
                  List<PaymentConnectorResponseLink> links, Long corporateCardSurcharge, Long totalAmount,
                  String gatewayTransactionId, ExternalMetadata metadata, Long fee, Long netAmount) {
        this.chargeId = chargeId;
        this.amount = amount;
        this.state = state;
        this.returnUrl = returnUrl;
        this.description = description;
        this.reference = reference;
        this.email = email;
        this.paymentProvider = paymentProvider;
        this.createdDate = createdDate;
        this.language = language;
        this.delayedCapture = delayedCapture;
        this.refundSummary = refundSummary;
        this.settlementSummary = settlementSummary;
        this.cardDetails = cardDetails;
        this.links = links;
        this.corporateCardSurcharge = corporateCardSurcharge;
        this.totalAmount = totalAmount;
        this.gatewayTransactionId = gatewayTransactionId;
        this.metadata = metadata;
        this.fee = fee;
        this.netAmount = netAmount;
    }

    public static Charge from(ChargeFromResponse chargeFromResponse) {
        return new Charge(
                chargeFromResponse.getChargeId(),
                chargeFromResponse.getAmount(),
                chargeFromResponse.getState(),
                chargeFromResponse.getReturnUrl(),
                chargeFromResponse.getDescription(),
                chargeFromResponse.getReference(),
                chargeFromResponse.getEmail(),
                chargeFromResponse.getPaymentProvider(),
                chargeFromResponse.getCreatedDate(),
                chargeFromResponse.getLanguage(),
                chargeFromResponse.getDelayedCapture(),
                chargeFromResponse.getRefundSummary(),
                chargeFromResponse.getSettlementSummary(),
                chargeFromResponse.getCardDetails(),
                chargeFromResponse.getLinks(),
                chargeFromResponse.getCorporateCardSurcharge(),
                chargeFromResponse.getTotalAmount(),
                chargeFromResponse.getGatewayTransactionId(),
                chargeFromResponse.getMetadata().orElse(null),
                chargeFromResponse.getFee(),
                chargeFromResponse.getNetAmount()
        );
    }

    public static Charge from(TransactionResponse transactionResponse) {
        return new Charge(
                transactionResponse.getChargeId(),
                transactionResponse.getAmount(),
                transactionResponse.getState(),
                transactionResponse.getReturnUrl(),
                transactionResponse.getDescription(),
                transactionResponse.getReference(),
                transactionResponse.getEmail(),
                transactionResponse.getPaymentProvider(),
                transactionResponse.getCreatedDate(),
                transactionResponse.getLanguage(),
                transactionResponse.getDelayedCapture(),
                transactionResponse.getRefundSummary(),
                transactionResponse.getSettlementSummary(),
                transactionResponse.getCardDetails(),
                transactionResponse.getLinks(),
                transactionResponse.getCorporateCardSurcharge(),
                transactionResponse.getTotalAmount(),
                transactionResponse.getGatewayTransactionId(),
                transactionResponse.getMetadata().orElse(null),
                transactionResponse.getFee(),
                transactionResponse.getNetAmount()
        );
    }

    public Optional<ExternalMetadata> getMetadata() {
        return Optional.ofNullable(metadata);
    }

    public String getChargeId() {
        return chargeId;
    }

    public Long getAmount() {
        return amount;
    }

    public PaymentState getState() {
        return state;
    }

    public String getReturnUrl() {
        return returnUrl;
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

    public SupportedLanguage getLanguage() {
        return language;
    }

    public boolean getDelayedCapture() {
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

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public String getCardBrand() {
        return cardDetails != null ? cardDetails.getCardBrand() : "";
    }

    public String getCreatedDate() {
        return createdDate;
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

    public String getGatewayTransactionId() {
        return gatewayTransactionId;
    }
}
