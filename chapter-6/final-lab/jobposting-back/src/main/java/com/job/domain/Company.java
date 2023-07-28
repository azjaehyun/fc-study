package com.job.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "applications", "company" }, allowSetters = true)
    private Set<JobPosting> jobPostings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getCompanyId() {
        return this.companyId;
    }

    public Company companyId(Long companyId) {
        this.setCompanyId(companyId);
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return this.name;
    }

    public Company name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public Company address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<JobPosting> getJobPostings() {
        return this.jobPostings;
    }

    public void setJobPostings(Set<JobPosting> jobPostings) {
        if (this.jobPostings != null) {
            this.jobPostings.forEach(i -> i.setCompany(null));
        }
        if (jobPostings != null) {
            jobPostings.forEach(i -> i.setCompany(this));
        }
        this.jobPostings = jobPostings;
    }

    public Company jobPostings(Set<JobPosting> jobPostings) {
        this.setJobPostings(jobPostings);
        return this;
    }

    public Company addJobPosting(JobPosting jobPosting) {
        this.jobPostings.add(jobPosting);
        jobPosting.setCompany(this);
        return this;
    }

    public Company removeJobPosting(JobPosting jobPosting) {
        this.jobPostings.remove(jobPosting);
        jobPosting.setCompany(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return companyId != null && companyId.equals(((Company) o).companyId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "companyId=" + getCompanyId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
