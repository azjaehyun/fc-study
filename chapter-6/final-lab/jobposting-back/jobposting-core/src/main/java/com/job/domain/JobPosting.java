package com.job.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A JobPosting.
 */
@Entity
@Table(name = "job_posting")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JobPosting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "posted_date")
    private Instant postedDate;

    @OneToMany(mappedBy = "jobPosting")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "jobPosting", "applicant" }, allowSetters = true)
    private Set<Application> applications = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "jobPostings" }, allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getJobId() {
        return this.jobId;
    }

    public JobPosting jobId(Long jobId) {
        this.setJobId(jobId);
        return this;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return this.title;
    }

    public JobPosting title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public JobPosting description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return this.location;
    }

    public JobPosting location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Instant getPostedDate() {
        return this.postedDate;
    }

    public JobPosting postedDate(Instant postedDate) {
        this.setPostedDate(postedDate);
        return this;
    }

    public void setPostedDate(Instant postedDate) {
        this.postedDate = postedDate;
    }

    public Set<Application> getApplications() {
        return this.applications;
    }

    public void setApplications(Set<Application> applications) {
        if (this.applications != null) {
            this.applications.forEach(i -> i.setJobPosting(null));
        }
        if (applications != null) {
            applications.forEach(i -> i.setJobPosting(this));
        }
        this.applications = applications;
    }

    public JobPosting applications(Set<Application> applications) {
        this.setApplications(applications);
        return this;
    }

    public JobPosting addApplication(Application application) {
        this.applications.add(application);
        application.setJobPosting(this);
        return this;
    }

    public JobPosting removeApplication(Application application) {
        this.applications.remove(application);
        application.setJobPosting(null);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public JobPosting company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobPosting)) {
            return false;
        }
        return jobId != null && jobId.equals(((JobPosting) o).jobId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobPosting{" +
            "jobId=" + getJobId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", location='" + getLocation() + "'" +
            ", postedDate='" + getPostedDate() + "'" +
            "}";
    }
}
