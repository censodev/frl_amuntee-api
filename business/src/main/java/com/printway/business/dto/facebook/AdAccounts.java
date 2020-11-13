package com.printway.business.dto.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class AdAccounts {
    List<AdAccount> data;

    @Data
    public static class AdAccount {
        private String id;
        private String name;

        @JsonProperty("account_status")
        private Integer accountStatus;
        private Double age;

        @JsonProperty("amount_spent")
        private Double amountSpent;

        @JsonProperty("spend_cap")
        private Double spendCap;

        private Double balance;
        private String currency;

        @JsonProperty("is_prepay_account")
        private Boolean isPrepayAccount;

        private Business business;

        @JsonProperty("funding_source_details")
        private FundingSourceDetails fundingSourceDetails;

        private Campaigns campaigns;
        private Adsets adsets;
    }

    public String toTable() {
        var header  = "| ID | Name | Status | Age | Amount Spent | Spend Cap | Balance | Currency | Prepay | Business ID | Business | Funding | Camps | Adsets |\n\n";
        var line    = "| -- | ---- | ------ | --- | ------------ | --------- | ------- | -------- | ------ | ----------- | -------- | ------- | ----- | ------ |\n\n";
        var rows = data.stream()
                .map(i -> String.format("| %s | %s | %s | %d | %.2f | %.2f | %.2f | %s | %s | %s | %s | %s | %d | %d |\n",
                        i.id.replace("act_", ""),
                        i.name,
                        i.accountStatus == 1 ? "ACTIVE" : "DISABLED",
                        Math.round(i.age),
                        i.amountSpent,
                        i.spendCap,
                        i.balance,
                        i.currency,
                        i.isPrepayAccount,
                        i.business != null ? i.business.getId() : "none",
                        i.business != null ? i.business.getName() : "none",
                        i.fundingSourceDetails != null ? i.fundingSourceDetails.getDisplayString() : "none",
                        i.campaigns != null ? i.campaigns.getData().size() : 0,
                        i.adsets != null ? i.adsets.getData().size() : 0))
                .reduce("", (acc, cur) -> acc + cur + "\n");
        rows = rows.equals("") ? "No data" : rows;
        return header + line + rows;
    }

    public String toTableHTML() {
        var rows = data.stream()
                .map(i -> String.format(
                        "        <tr>\n" +
                        "            <td>%s</td>\n" +
                        "            <td>%s</td>\n" +
                        "            <td>%s</td>\n" +
                        "            <td>%d</td>\n" +
                        "            <td>%.2f</td>\n" +
                        "            <td>%.2f</td>\n" +
                        "            <td>%.2f</td>\n" +
                        "            <td>%s</td>\n" +
                        "            <td>%s</td>\n" +
                        "            <td>%s</td>\n" +
                        "            <td>%s</td>\n" +
                        "            <td>%s</td>\n" +
                        "            <td>%d</td>\n" +
                        "            <td>%d</td>\n" +
                        "        </tr>\n",
                        i.id.replace("act_", ""),
                        i.name,
                        i.accountStatus == 1 ? "ACTIVE" : "DISABLED",
                        Math.round(i.age),
                        i.amountSpent,
                        i.spendCap,
                        i.balance,
                        i.currency,
                        i.isPrepayAccount,
                        i.business != null ? i.business.getId() : "none",
                        i.business != null ? i.business.getName() : "none",
                        i.fundingSourceDetails != null ? i.fundingSourceDetails.getDisplayString() : "none",
                        i.campaigns != null ? i.campaigns.getData().size() : 0,
                        i.adsets != null ? i.adsets.getData().size() : 0))
                .reduce("", (acc, cur) -> acc + cur + "\n");
        rows = rows.equals("") ? "<tr>No data<tr>" : rows;
        return "<table>\n" +
                "    <thead>\n" +
                "        <tr>\n" +
                "            <th>ID</th>\n" +
                "            <th>Name</th>\n" +
                "            <th>Status</th>\n" +
                "            <th>Age</th>\n" +
                "            <th>Amount Spent</th>\n" +
                "            <th>Spend Cap</th>\n" +
                "            <th>Balance</th>\n" +
                "            <th>Currency</th>\n" +
                "            <th>Prepay</th>\n" +
                "            <th>Business ID</th>\n" +
                "            <th>Business</th>\n" +
                "            <th>Funding</th>\n" +
                "            <th>Camps</th>\n" +
                "            <th>Adsets</th>\n" +
                "        </tr>\n" +
                "    </thead>\n" +
                "    <tbody>\n" +
                rows +
                "    </tbody>\n" +
                "</table>";
    }
}
