package com.co.kr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Corp.
 */
@Entity
@Table(name = "corp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Corp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "corp_name")
    private String corpName;

    @OneToMany(mappedBy = "corp")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "recruitMatches", "corp" }, allowSetters = true)
    private Set<Recruit> recruits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Corp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorpName() {
        return this.corpName;
    }

    public Corp corpName(String corpName) {
        this.setCorpName(corpName);
        return this;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public Set<Recruit> getRecruits() {
        return this.recruits;
    }

    public void setRecruits(Set<Recruit> recruits) {
        if (this.recruits != null) {
            this.recruits.forEach(i -> i.setCorp(null));
        }
        if (recruits != null) {
            recruits.forEach(i -> i.setCorp(this));
        }
        this.recruits = recruits;
    }

    public Corp recruits(Set<Recruit> recruits) {
        this.setRecruits(recruits);
        return this;
    }

    public Corp addRecruit(Recruit recruit) {
        this.recruits.add(recruit);
        recruit.setCorp(this);
        return this;
    }

    public Corp removeRecruit(Recruit recruit) {
        this.recruits.remove(recruit);
        recruit.setCorp(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Corp)) {
            return false;
        }
        return id != null && id.equals(((Corp) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Corp{" +
            "id=" + getId() +
            ", corpName='" + getCorpName() + "'" +
            "}";
    }
}
