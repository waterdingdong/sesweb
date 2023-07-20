package jp.ecn.sesweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContractPriceUpdateHistory.
 */
@Entity
@Table(name = "contract_price_update_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContractPriceUpdateHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "before_price")
    private Integer beforePrice;

    @Column(name = "after_price")
    private Integer afterPrice;

    @ManyToOne
    @JsonIgnoreProperties(value = { "salesAmounts", "priceUpdateHistories", "project", "partnerManager" }, allowSetters = true)
    private Contracts contract;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ContractPriceUpdateHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getUpdateDate() {
        return this.updateDate;
    }

    public ContractPriceUpdateHistory updateDate(Instant updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getBeforePrice() {
        return this.beforePrice;
    }

    public ContractPriceUpdateHistory beforePrice(Integer beforePrice) {
        this.setBeforePrice(beforePrice);
        return this;
    }

    public void setBeforePrice(Integer beforePrice) {
        this.beforePrice = beforePrice;
    }

    public Integer getAfterPrice() {
        return this.afterPrice;
    }

    public ContractPriceUpdateHistory afterPrice(Integer afterPrice) {
        this.setAfterPrice(afterPrice);
        return this;
    }

    public void setAfterPrice(Integer afterPrice) {
        this.afterPrice = afterPrice;
    }

    public Contracts getContract() {
        return this.contract;
    }

    public void setContract(Contracts contracts) {
        this.contract = contracts;
    }

    public ContractPriceUpdateHistory contract(Contracts contracts) {
        this.setContract(contracts);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContractPriceUpdateHistory)) {
            return false;
        }
        return id != null && id.equals(((ContractPriceUpdateHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContractPriceUpdateHistory{" +
            "id=" + getId() +
            ", updateDate='" + getUpdateDate() + "'" +
            ", beforePrice=" + getBeforePrice() +
            ", afterPrice=" + getAfterPrice() +
            "}";
    }
}
