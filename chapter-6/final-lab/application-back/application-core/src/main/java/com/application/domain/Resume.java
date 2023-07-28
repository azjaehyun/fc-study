package com.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Resume.
 */
@Entity
@Table(name = "resume")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Resume implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Long resumeId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "submitted_date")
    private Instant submittedDate;

    @OneToMany(mappedBy = "resume")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resume" }, allowSetters = true)
    private Set<Application> applications = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "resumes" }, allowSetters = true)
    private Applicant applicant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getResumeId() {
        return this.resumeId;
    }

    public Resume resumeId(Long resumeId) {
        this.setResumeId(resumeId);
        return this;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public String getTitle() {
        return this.title;
    }

    public Resume title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public Resume content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getSubmittedDate() {
        return this.submittedDate;
    }

    public Resume submittedDate(Instant submittedDate) {
        this.setSubmittedDate(submittedDate);
        return this;
    }

    public void setSubmittedDate(Instant submittedDate) {
        this.submittedDate = submittedDate;
    }

    public Set<Application> getApplications() {
        return this.applications;
    }

    public void setApplications(Set<Application> applications) {
        if (this.applications != null) {
            this.applications.forEach(i -> i.setResume(null));
        }
        if (applications != null) {
            applications.forEach(i -> i.setResume(this));
        }
        this.applications = applications;
    }

    public Resume applications(Set<Application> applications) {
        this.setApplications(applications);
        return this;
    }

    public Resume addApplication(Application application) {
        this.applications.add(application);
        application.setResume(this);
        return this;
    }

    public Resume removeApplication(Application application) {
        this.applications.remove(application);
        application.setResume(null);
        return this;
    }

    public Applicant getApplicant() {
        return this.applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public Resume applicant(Applicant applicant) {
        this.setApplicant(applicant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resume)) {
            return false;
        }
        return resumeId != null && resumeId.equals(((Resume) o).resumeId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Resume{" +
            "resumeId=" + getResumeId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", submittedDate='" + getSubmittedDate() + "'" +
            "}";
    }
}
