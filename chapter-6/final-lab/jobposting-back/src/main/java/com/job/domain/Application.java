package com.job.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Application.
 */
@Entity
@Table(name = "application")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "application_date")
    private Instant applicationDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "applications", "company" }, allowSetters = true)
    private JobPosting jobPosting;

    @ManyToOne
    @JsonIgnoreProperties(value = { "applications" }, allowSetters = true)
    private Applicant applicant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getApplicationId() {
        return this.applicationId;
    }

    public Application applicationId(Long applicationId) {
        this.setApplicationId(applicationId);
        return this;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Instant getApplicationDate() {
        return this.applicationDate;
    }

    public Application applicationDate(Instant applicationDate) {
        this.setApplicationDate(applicationDate);
        return this;
    }

    public void setApplicationDate(Instant applicationDate) {
        this.applicationDate = applicationDate;
    }

    public JobPosting getJobPosting() {
        return this.jobPosting;
    }

    public void setJobPosting(JobPosting jobPosting) {
        this.jobPosting = jobPosting;
    }

    public Application jobPosting(JobPosting jobPosting) {
        this.setJobPosting(jobPosting);
        return this;
    }

    public Applicant getApplicant() {
        return this.applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public Application applicant(Applicant applicant) {
        this.setApplicant(applicant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Application)) {
            return false;
        }
        return applicationId != null && applicationId.equals(((Application) o).applicationId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Application{" +
            "applicationId=" + getApplicationId() +
            ", applicationDate='" + getApplicationDate() + "'" +
            "}";
    }
}
