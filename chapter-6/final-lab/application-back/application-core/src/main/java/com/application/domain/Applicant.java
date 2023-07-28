package com.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Applicant.
 */
@Entity
@Table(name = "applicant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Applicant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicant_id")
    private Long applicantId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "applicant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "applications", "applicant" }, allowSetters = true)
    private Set<Resume> resumes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getApplicantId() {
        return this.applicantId;
    }

    public Applicant applicantId(Long applicantId) {
        this.setApplicantId(applicantId);
        return this;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public String getName() {
        return this.name;
    }

    public Applicant name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Applicant email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Applicant phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Resume> getResumes() {
        return this.resumes;
    }

    public void setResumes(Set<Resume> resumes) {
        if (this.resumes != null) {
            this.resumes.forEach(i -> i.setApplicant(null));
        }
        if (resumes != null) {
            resumes.forEach(i -> i.setApplicant(this));
        }
        this.resumes = resumes;
    }

    public Applicant resumes(Set<Resume> resumes) {
        this.setResumes(resumes);
        return this;
    }

    public Applicant addResume(Resume resume) {
        this.resumes.add(resume);
        resume.setApplicant(this);
        return this;
    }

    public Applicant removeResume(Resume resume) {
        this.resumes.remove(resume);
        resume.setApplicant(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Applicant)) {
            return false;
        }
        return applicantId != null && applicantId.equals(((Applicant) o).applicantId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Applicant{" +
            "applicantId=" + getApplicantId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
