package com.application.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Application.class)
public abstract class Application_ {

    public static volatile SingularAttribute<Application, Resume> resume;
    public static volatile SingularAttribute<Application, Long> applicationId;
    public static volatile SingularAttribute<Application, Instant> applicationDate;

    public static final String RESUME = "resume";
    public static final String APPLICATION_ID = "applicationId";
    public static final String APPLICATION_DATE = "applicationDate";
}
