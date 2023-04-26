package com.co.kr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RecruitMatch.
 */
@Entity
@Table(name = "recruit_match")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RecruitMatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "recurit_id")
    private Long recuritId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "recruitMatches" }, allowSetters = true)
    private JobUser jobUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "recruitMatches", "corp" }, allowSetters = true)
    private Recruit recruit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RecruitMatch id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public RecruitMatch userId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRecuritId() {
        return this.recuritId;
    }

    public RecruitMatch recuritId(Long recuritId) {
        this.setRecuritId(recuritId);
        return this;
    }

    public void setRecuritId(Long recuritId) {
        this.recuritId = recuritId;
    }

    public JobUser getJobUser() {
        return this.jobUser;
    }

    public void setJobUser(JobUser jobUser) {
        this.jobUser = jobUser;
    }

    public RecruitMatch jobUser(JobUser jobUser) {
        this.setJobUser(jobUser);
        return this;
    }

    public Recruit getRecruit() {
        return this.recruit;
    }

    public void setRecruit(Recruit recruit) {
        this.recruit = recruit;
    }

    public RecruitMatch recruit(Recruit recruit) {
        this.setRecruit(recruit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecruitMatch)) {
            return false;
        }
        return id != null && id.equals(((RecruitMatch) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecruitMatch{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", recuritId=" + getRecuritId() +
            "}";
    }
}
