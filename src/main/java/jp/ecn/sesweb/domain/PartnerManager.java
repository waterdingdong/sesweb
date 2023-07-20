package jp.ecn.sesweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PartnerManager.
 */
@Entity
@Table(name = "partner_manager")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PartnerManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "wechat_id")
    private String wechatId;

    @OneToMany(mappedBy = "partnerManager")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "salesAmounts", "priceUpdateHistories", "project", "partnerManager" }, allowSetters = true)
    private Set<Contracts> contracts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PartnerManager id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public PartnerManager name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public PartnerManager companyName(String companyName) {
        this.setCompanyName(companyName);
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return this.email;
    }

    public PartnerManager email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public PartnerManager phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWechatId() {
        return this.wechatId;
    }

    public PartnerManager wechatId(String wechatId) {
        this.setWechatId(wechatId);
        return this;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public Set<Contracts> getContracts() {
        return this.contracts;
    }

    public void setContracts(Set<Contracts> contracts) {
        if (this.contracts != null) {
            this.contracts.forEach(i -> i.setPartnerManager(null));
        }
        if (contracts != null) {
            contracts.forEach(i -> i.setPartnerManager(this));
        }
        this.contracts = contracts;
    }

    public PartnerManager contracts(Set<Contracts> contracts) {
        this.setContracts(contracts);
        return this;
    }

    public PartnerManager addContracts(Contracts contracts) {
        this.contracts.add(contracts);
        contracts.setPartnerManager(this);
        return this;
    }

    public PartnerManager removeContracts(Contracts contracts) {
        this.contracts.remove(contracts);
        contracts.setPartnerManager(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartnerManager)) {
            return false;
        }
        return id != null && id.equals(((PartnerManager) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartnerManager{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", wechatId='" + getWechatId() + "'" +
            "}";
    }
}
