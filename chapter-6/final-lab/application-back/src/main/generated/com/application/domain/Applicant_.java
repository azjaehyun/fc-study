package com.application.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Applicant.class)
public abstract class Applicant_ {

    public static volatile SingularAttribute<Applicant, String> phone;
    public static volatile SingularAttribute<Applicant, String> name;
    public static volatile SingularAttribute<Applicant, Long> applicantId;
    public static volatile SetAttribute<Applicant, Resume> resumes;
    public static volatile SingularAttribute<Applicant, String> email;

    public static final String PHONE = "phone";
    public static final String NAME = "name";
    public static final String APPLICANT_ID = "applicantId";
    public static final String RESUMES = "resumes";
    public static final String EMAIL = "email";
}
