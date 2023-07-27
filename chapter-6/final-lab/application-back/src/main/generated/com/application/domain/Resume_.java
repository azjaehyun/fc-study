package com.application.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Resume.class)
public abstract class Resume_ {

	public static volatile SingularAttribute<Resume, Long> resumeId;
	public static volatile SingularAttribute<Resume, String> title;
	public static volatile SingularAttribute<Resume, Instant> submittedDate;
	public static volatile SingularAttribute<Resume, String> content;
	public static volatile SetAttribute<Resume, Application> applications;
	public static volatile SingularAttribute<Resume, Applicant> applicant;

	public static final String RESUME_ID = "resumeId";
	public static final String TITLE = "title";
	public static final String SUBMITTED_DATE = "submittedDate";
	public static final String CONTENT = "content";
	public static final String APPLICATIONS = "applications";
	public static final String APPLICANT = "applicant";

}

