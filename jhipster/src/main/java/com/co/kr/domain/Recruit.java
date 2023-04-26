package com.co.kr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Recruit.
 */
@Entity
@Table(name = "recruit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Recruit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "recruit_text")
    private String recruitText;

    @OneToMany(mappedBy = "recruit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "jobUser", "recruit" }, allowSetters = true)
    private Set<RecruitMatch> recruitMatches = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "recruits" }, allowSetters = true)
    private Corp corp;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Recruit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecruitText() {
        return this.recruitText;
    }

    public Recruit recruitText(String recruitText) {
        this.setRecruitText(recruitText);
        return this;
    }

    public void setRecruitText(String recruitText) {
        this.recruitText = recruitText;
    }

    public Set<RecruitMatch> getRecruitMatches() {
        return this.recruitMatches;
    }

    public void setRecruitMatches(Set<RecruitMatch> recruitMatches) {
        if (this.recruitMatches != null) {
            this.recruitMatches.forEach(i -> i.setRecruit(null));
        }
        if (recruitMatches != null) {
            recruitMatches.forEach(i -> i.setRecruit(this));
        }
        this.recruitMatches = recruitMatches;
    }

    public Recruit recruitMatches(Set<RecruitMatch> recruitMatches) {
        this.setRecruitMatches(recruitMatches);
        return this;
    }

    public Recruit addRecruitMatch(RecruitMatch recruitMatch) {
        this.recruitMatches.add(recruitMatch);
        recruitMatch.setRecruit(this);
        return this;
    }

    public Recruit removeRecruitMatch(RecruitMatch recruitMatch) {
        this.recruitMatches.remove(recruitMatch);
        recruitMatch.setRecruit(null);
        return this;
    }

    public Corp getCorp() {
        return this.corp;
    }

    public void setCorp(Corp corp) {
        this.corp = corp;
    }

    public Recruit corp(Corp corp) {
        this.setCorp(corp);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recruit)) {
            return false;
        }
        return id != null && id.equals(((Recruit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recruit{" +
            "id=" + getId() +
            ", recruitText='" + getRecruitText() + "'" +
            "}";
    }
}
