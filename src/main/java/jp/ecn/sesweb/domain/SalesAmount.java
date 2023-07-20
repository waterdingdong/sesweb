package jp.ecn.sesweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SalesAmount.
 */
@Entity
@Table(name = "sales_amount")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalesAmount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "sales_ym")
    private String salesYm;

    @Column(name = "work_time")
    private Integer workTime;

    @Column(name = "billing_amount")
    private Integer billingAmount;

    @ManyToOne
    @JsonIgnoreProperties(value = { "salesAmounts", "priceUpdateHistories", "project", "partnerManager" }, allowSetters = true)
    private Contracts contract;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SalesAmount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalesYm() {
        return this.salesYm;
    }

    public SalesAmount salesYm(String salesYm) {
        this.setSalesYm(salesYm);
        return this;
    }

    public void setSalesYm(String salesYm) {
        this.salesYm = salesYm;
    }

    public Integer getWorkTime() {
        return this.workTime;
    }

    public SalesAmount workTime(Integer workTime) {
        this.setWorkTime(workTime);
        return this;
    }

    public void setWorkTime(Integer workTime) {
        this.workTime = workTime;
    }

    public Integer getBillingAmount() {
        return this.billingAmount;
    }

    public SalesAmount billingAmount(Integer billingAmount) {
        this.setBillingAmount(billingAmount);
        return this;
    }

    public void setBillingAmount(Integer billingAmount) {
        this.billingAmount = billingAmount;
    }

    public Contracts getContract() {
        return this.contract;
    }

    public void setContract(Contracts contracts) {
        this.contract = contracts;
    }

    public SalesAmount contract(Contracts contracts) {
        this.setContract(contracts);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalesAmount)) {
            return false;
        }
        return id != null && id.equals(((SalesAmount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesAmount{" +
            "id=" + getId() +
            ", salesYm='" + getSalesYm() + "'" +
            ", workTime=" + getWorkTime() +
            ", billingAmount=" + getBillingAmount() +
            "}";
    }
}
