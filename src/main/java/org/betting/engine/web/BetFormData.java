package org.betting.engine.web;

public class BetFormData {
    private String betOfferId;
    private String customerId;
    private Double amount;

    public String getBetOfferId() {
        return betOfferId;
    }

    public void setBetOfferId(String betOfferId) {
        this.betOfferId = betOfferId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
