package jp.ecn.sesweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CustomerManager.
 */
@Entity
@Table(name = "customer_manager")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomerManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 請求先
     */
    @Schema(description = "請求先", required = true)
    @NotNull
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    /**
     * 顧客担当者
     */
    @Schema(description = "顧客担当者", required = true)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_name")
    private String phoneName;

    @OneToMany(mappedBy = "customerManager")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contracts", "customerManager" }, allowSetters = true)
    private Set<Projects> projects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CustomerManager id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public CustomerManager customerName(String customerName) {
        this.setCustomerName(customerName);
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getName() {
        return this.name;
    }

    public CustomerManager name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public CustomerManager email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneName() {
        return this.phoneName;
    }

    public CustomerManager phoneName(String phoneName) {
        this.setPhoneName(phoneName);
        return this;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public Set<Projects> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Projects> projects) {
        if (this.projects != null) {
            this.projects.forEach(i -> i.setCustomerManager(null));
        }
        if (projects != null) {
            projects.forEach(i -> i.setCustomerManager(this));
        }
        this.projects = projects;
    }

    public CustomerManager projects(Set<Projects> projects) {
        this.setProjects(projects);
        return this;
    }

    public CustomerManager addProjects(Projects projects) {
        this.projects.add(projects);
        projects.setCustomerManager(this);
        return this;
    }

    public CustomerManager removeProjects(Projects projects) {
        this.projects.remove(projects);
        projects.setCustomerManager(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerManager)) {
            return false;
        }
        return id != null && id.equals(((CustomerManager) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerManager{" +
            "id=" + getId() +
            ", customerName='" + getCustomerName() + "'" +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneName='" + getPhoneName() + "'" +
            "}";
    }
}
