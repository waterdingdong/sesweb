package jp.ecn.sesweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Projects.
 */
@Entity
@Table(name = "projects")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Projects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "salesAmounts", "priceUpdateHistories", "project", "partnerManager" }, allowSetters = true)
    private Set<Contracts> contracts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "projects" }, allowSetters = true)
    private CustomerManager customerManager;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Projects id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Projects name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Contracts> getContracts() {
        return this.contracts;
    }

    public void setContracts(Set<Contracts> contracts) {
        if (this.contracts != null) {
            this.contracts.forEach(i -> i.setProject(null));
        }
        if (contracts != null) {
            contracts.forEach(i -> i.setProject(this));
        }
        this.contracts = contracts;
    }

    public Projects contracts(Set<Contracts> contracts) {
        this.setContracts(contracts);
        return this;
    }

    public Projects addContracts(Contracts contracts) {
        this.contracts.add(contracts);
        contracts.setProject(this);
        return this;
    }

    public Projects removeContracts(Contracts contracts) {
        this.contracts.remove(contracts);
        contracts.setProject(null);
        return this;
    }

    public CustomerManager getCustomerManager() {
        return this.customerManager;
    }

    public void setCustomerManager(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }

    public Projects customerManager(CustomerManager customerManager) {
        this.setCustomerManager(customerManager);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projects)) {
            return false;
        }
        return id != null && id.equals(((Projects) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Projects{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
