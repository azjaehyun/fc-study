package com.co.kr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JobUser.
 */
@Entity
@Table(name = "job_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JobUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "age")
    private Integer age;

    @OneToMany(mappedBy = "jobUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "jobUser", "recruit" }, allowSetters = true)
    private Set<RecruitMatch> recruitMatches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JobUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public JobUser userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return this.age;
    }

    public JobUser age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<RecruitMatch> getRecruitMatches() {
        return this.recruitMatches;
    }

    public void setRecruitMatches(Set<RecruitMatch> recruitMatches) {
        if (this.recruitMatches != null) {
            this.recruitMatches.forEach(i -> i.setJobUser(null));
        }
        if (recruitMatches != null) {
            recruitMatches.forEach(i -> i.setJobUser(this));
        }
        this.recruitMatches = recruitMatches;
    }

    public JobUser recruitMatches(Set<RecruitMatch> recruitMatches) {
        this.setRecruitMatches(recruitMatches);
        return this;
    }

    public JobUser addRecruitMatch(RecruitMatch recruitMatch) {
        this.recruitMatches.add(recruitMatch);
        recruitMatch.setJobUser(this);
        return this;
    }

    public JobUser removeRecruitMatch(RecruitMatch recruitMatch) {
        this.recruitMatches.remove(recruitMatch);
        recruitMatch.setJobUser(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobUser)) {
            return false;
        }
        return id != null && id.equals(((JobUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobUser{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", age=" + getAge() +
            "}";
    }
}
