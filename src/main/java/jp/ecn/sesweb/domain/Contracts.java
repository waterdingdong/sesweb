package jp.ecn.sesweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Contracts.
 */
@Entity
@Table(name = "contracts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contracts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 苗字
     */
    @Schema(description = "苗字", required = true)
    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * 名前
     */
    @Schema(description = "名前", required = true)
    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * ID
     */
    @Schema(description = "ID")
    @Column(name = "ecn_id")
    private String ecnId;

    /**
     * 担当者
     */
    @Schema(description = "担当者")
    @Column(name = "manager")
    private String manager;

    /**
     * 担当者
     */
    @Schema(description = "担当者")
    @Column(name = "manager_ecn_id")
    private String managerEcnId;

    /**
     * 契約開始日付
     */
    @Schema(description = "契約開始日付")
    @Column(name = "contract_start_date")
    private Instant contractStartDate;

    /**
     * 契約終了日付
     */
    @Schema(description = "契約終了日付")
    @Column(name = "contract_end_date")
    private Instant contractEndDate;

    /**
     * 受注単金
     */
    @Schema(description = "受注単金", required = true)
    @NotNull
    @Column(name = "order_unit_price", nullable = false)
    private Integer orderUnitPrice;

    /**
     * 受注精算条件上限
     */
    @Schema(description = "受注精算条件上限")
    @Column(name = "maximum_order_settlement_condition")
    private Integer maximumOrderSettlementCondition;

    /**
     * 受注精算条件下限
     */
    @Schema(description = "受注精算条件下限")
    @Column(name = "minimum_order_settlement_condition")
    private Integer minimumOrderSettlementCondition;

    /**
     * 受注控除単価
     */
    @Schema(description = "受注控除単価")
    @Column(name = "order_deduction_unit_price")
    private Integer orderDeductionUnitPrice;

    /**
     * 受注超過単価
     */
    @Schema(description = "受注超過単価")
    @Column(name = "order_surcharge_unit_price")
    private Integer orderSurchargeUnitPrice;

    /**
     * 仕入れ単金
     */
    @Schema(description = "仕入れ単金")
    @Column(name = "purchase_unit_price")
    private Integer purchaseUnitPrice;

    /**
     * 仕入れ精算条件上限
     */
    @Schema(description = "仕入れ精算条件上限")
    @Column(name = "maximum_purchase_settlement_condition")
    private Integer maximumPurchaseSettlementCondition;

    /**
     * 仕入れ精算条件下限
     */
    @Schema(description = "仕入れ精算条件下限")
    @Column(name = "minimum_purchase_settlement_condition")
    private Integer minimumPurchaseSettlementCondition;

    /**
     * 仕入れ控除単価
     */
    @Schema(description = "仕入れ控除単価")
    @Column(name = "purchase_deduction_unit_price")
    private Integer purchaseDeductionUnitPrice;

    /**
     * 仕入れ超過単価
     */
    @Schema(description = "仕入れ超過単価")
    @Column(name = "purchase_surcharge_unit_price")
    private Integer purchaseSurchargeUnitPrice;

    @OneToMany(mappedBy = "contract")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contract" }, allowSetters = true)
    private Set<SalesAmount> salesAmounts = new HashSet<>();

    @OneToMany(mappedBy = "contract")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contract" }, allowSetters = true)
    private Set<ContractPriceUpdateHistory> priceUpdateHistories = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "contracts", "customerManager" }, allowSetters = true)
    private Projects project;

    @ManyToOne
    @JsonIgnoreProperties(value = { "contracts" }, allowSetters = true)
    private PartnerManager partnerManager;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Contracts id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Contracts lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Contracts firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEcnId() {
        return this.ecnId;
    }

    public Contracts ecnId(String ecnId) {
        this.setEcnId(ecnId);
        return this;
    }

    public void setEcnId(String ecnId) {
        this.ecnId = ecnId;
    }

    public String getManager() {
        return this.manager;
    }

    public Contracts manager(String manager) {
        this.setManager(manager);
        return this;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagerEcnId() {
        return this.managerEcnId;
    }

    public Contracts managerEcnId(String managerEcnId) {
        this.setManagerEcnId(managerEcnId);
        return this;
    }

    public void setManagerEcnId(String managerEcnId) {
        this.managerEcnId = managerEcnId;
    }

    public Instant getContractStartDate() {
        return this.contractStartDate;
    }

    public Contracts contractStartDate(Instant contractStartDate) {
        this.setContractStartDate(contractStartDate);
        return this;
    }

    public void setContractStartDate(Instant contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public Instant getContractEndDate() {
        return this.contractEndDate;
    }

    public Contracts contractEndDate(Instant contractEndDate) {
        this.setContractEndDate(contractEndDate);
        return this;
    }

    public void setContractEndDate(Instant contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public Integer getOrderUnitPrice() {
        return this.orderUnitPrice;
    }

    public Contracts orderUnitPrice(Integer orderUnitPrice) {
        this.setOrderUnitPrice(orderUnitPrice);
        return this;
    }

    public void setOrderUnitPrice(Integer orderUnitPrice) {
        this.orderUnitPrice = orderUnitPrice;
    }

    public Integer getMaximumOrderSettlementCondition() {
        return this.maximumOrderSettlementCondition;
    }

    public Contracts maximumOrderSettlementCondition(Integer maximumOrderSettlementCondition) {
        this.setMaximumOrderSettlementCondition(maximumOrderSettlementCondition);
        return this;
    }

    public void setMaximumOrderSettlementCondition(Integer maximumOrderSettlementCondition) {
        this.maximumOrderSettlementCondition = maximumOrderSettlementCondition;
    }

    public Integer getMinimumOrderSettlementCondition() {
        return this.minimumOrderSettlementCondition;
    }

    public Contracts minimumOrderSettlementCondition(Integer minimumOrderSettlementCondition) {
        this.setMinimumOrderSettlementCondition(minimumOrderSettlementCondition);
        return this;
    }

    public void setMinimumOrderSettlementCondition(Integer minimumOrderSettlementCondition) {
        this.minimumOrderSettlementCondition = minimumOrderSettlementCondition;
    }

    public Integer getOrderDeductionUnitPrice() {
        return this.orderDeductionUnitPrice;
    }

    public Contracts orderDeductionUnitPrice(Integer orderDeductionUnitPrice) {
        this.setOrderDeductionUnitPrice(orderDeductionUnitPrice);
        return this;
    }

    public void setOrderDeductionUnitPrice(Integer orderDeductionUnitPrice) {
        this.orderDeductionUnitPrice = orderDeductionUnitPrice;
    }

    public Integer getOrderSurchargeUnitPrice() {
        return this.orderSurchargeUnitPrice;
    }

    public Contracts orderSurchargeUnitPrice(Integer orderSurchargeUnitPrice) {
        this.setOrderSurchargeUnitPrice(orderSurchargeUnitPrice);
        return this;
    }

    public void setOrderSurchargeUnitPrice(Integer orderSurchargeUnitPrice) {
        this.orderSurchargeUnitPrice = orderSurchargeUnitPrice;
    }

    public Integer getPurchaseUnitPrice() {
        return this.purchaseUnitPrice;
    }

    public Contracts purchaseUnitPrice(Integer purchaseUnitPrice) {
        this.setPurchaseUnitPrice(purchaseUnitPrice);
        return this;
    }

    public void setPurchaseUnitPrice(Integer purchaseUnitPrice) {
        this.purchaseUnitPrice = purchaseUnitPrice;
    }

    public Integer getMaximumPurchaseSettlementCondition() {
        return this.maximumPurchaseSettlementCondition;
    }

    public Contracts maximumPurchaseSettlementCondition(Integer maximumPurchaseSettlementCondition) {
        this.setMaximumPurchaseSettlementCondition(maximumPurchaseSettlementCondition);
        return this;
    }

    public void setMaximumPurchaseSettlementCondition(Integer maximumPurchaseSettlementCondition) {
        this.maximumPurchaseSettlementCondition = maximumPurchaseSettlementCondition;
    }

    public Integer getMinimumPurchaseSettlementCondition() {
        return this.minimumPurchaseSettlementCondition;
    }

    public Contracts minimumPurchaseSettlementCondition(Integer minimumPurchaseSettlementCondition) {
        this.setMinimumPurchaseSettlementCondition(minimumPurchaseSettlementCondition);
        return this;
    }

    public void setMinimumPurchaseSettlementCondition(Integer minimumPurchaseSettlementCondition) {
        this.minimumPurchaseSettlementCondition = minimumPurchaseSettlementCondition;
    }

    public Integer getPurchaseDeductionUnitPrice() {
        return this.purchaseDeductionUnitPrice;
    }

    public Contracts purchaseDeductionUnitPrice(Integer purchaseDeductionUnitPrice) {
        this.setPurchaseDeductionUnitPrice(purchaseDeductionUnitPrice);
        return this;
    }

    public void setPurchaseDeductionUnitPrice(Integer purchaseDeductionUnitPrice) {
        this.purchaseDeductionUnitPrice = purchaseDeductionUnitPrice;
    }

    public Integer getPurchaseSurchargeUnitPrice() {
        return this.purchaseSurchargeUnitPrice;
    }

    public Contracts purchaseSurchargeUnitPrice(Integer purchaseSurchargeUnitPrice) {
        this.setPurchaseSurchargeUnitPrice(purchaseSurchargeUnitPrice);
        return this;
    }

    public void setPurchaseSurchargeUnitPrice(Integer purchaseSurchargeUnitPrice) {
        this.purchaseSurchargeUnitPrice = purchaseSurchargeUnitPrice;
    }

    public Set<SalesAmount> getSalesAmounts() {
        return this.salesAmounts;
    }

    public void setSalesAmounts(Set<SalesAmount> salesAmounts) {
        if (this.salesAmounts != null) {
            this.salesAmounts.forEach(i -> i.setContract(null));
        }
        if (salesAmounts != null) {
            salesAmounts.forEach(i -> i.setContract(this));
        }
        this.salesAmounts = salesAmounts;
    }

    public Contracts salesAmounts(Set<SalesAmount> salesAmounts) {
        this.setSalesAmounts(salesAmounts);
        return this;
    }

    public Contracts addSalesAmount(SalesAmount salesAmount) {
        this.salesAmounts.add(salesAmount);
        salesAmount.setContract(this);
        return this;
    }

    public Contracts removeSalesAmount(SalesAmount salesAmount) {
        this.salesAmounts.remove(salesAmount);
        salesAmount.setContract(null);
        return this;
    }

    public Set<ContractPriceUpdateHistory> getPriceUpdateHistories() {
        return this.priceUpdateHistories;
    }

    public void setPriceUpdateHistories(Set<ContractPriceUpdateHistory> contractPriceUpdateHistories) {
        if (this.priceUpdateHistories != null) {
            this.priceUpdateHistories.forEach(i -> i.setContract(null));
        }
        if (contractPriceUpdateHistories != null) {
            contractPriceUpdateHistories.forEach(i -> i.setContract(this));
        }
        this.priceUpdateHistories = contractPriceUpdateHistories;
    }

    public Contracts priceUpdateHistories(Set<ContractPriceUpdateHistory> contractPriceUpdateHistories) {
        this.setPriceUpdateHistories(contractPriceUpdateHistories);
        return this;
    }

    public Contracts addPriceUpdateHistory(ContractPriceUpdateHistory contractPriceUpdateHistory) {
        this.priceUpdateHistories.add(contractPriceUpdateHistory);
        contractPriceUpdateHistory.setContract(this);
        return this;
    }

    public Contracts removePriceUpdateHistory(ContractPriceUpdateHistory contractPriceUpdateHistory) {
        this.priceUpdateHistories.remove(contractPriceUpdateHistory);
        contractPriceUpdateHistory.setContract(null);
        return this;
    }

    public Projects getProject() {
        return this.project;
    }

    public void setProject(Projects projects) {
        this.project = projects;
    }

    public Contracts project(Projects projects) {
        this.setProject(projects);
        return this;
    }

    public PartnerManager getPartnerManager() {
        return this.partnerManager;
    }

    public void setPartnerManager(PartnerManager partnerManager) {
        this.partnerManager = partnerManager;
    }

    public Contracts partnerManager(PartnerManager partnerManager) {
        this.setPartnerManager(partnerManager);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contracts)) {
            return false;
        }
        return id != null && id.equals(((Contracts) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contracts{" +
            "id=" + getId() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", ecnId='" + getEcnId() + "'" +
            ", manager='" + getManager() + "'" +
            ", managerEcnId='" + getManagerEcnId() + "'" +
            ", contractStartDate='" + getContractStartDate() + "'" +
            ", contractEndDate='" + getContractEndDate() + "'" +
            ", orderUnitPrice=" + getOrderUnitPrice() +
            ", maximumOrderSettlementCondition=" + getMaximumOrderSettlementCondition() +
            ", minimumOrderSettlementCondition=" + getMinimumOrderSettlementCondition() +
            ", orderDeductionUnitPrice=" + getOrderDeductionUnitPrice() +
            ", orderSurchargeUnitPrice=" + getOrderSurchargeUnitPrice() +
            ", purchaseUnitPrice=" + getPurchaseUnitPrice() +
            ", maximumPurchaseSettlementCondition=" + getMaximumPurchaseSettlementCondition() +
            ", minimumPurchaseSettlementCondition=" + getMinimumPurchaseSettlementCondition() +
            ", purchaseDeductionUnitPrice=" + getPurchaseDeductionUnitPrice() +
            ", purchaseSurchargeUnitPrice=" + getPurchaseSurchargeUnitPrice() +
            "}";
    }
}
