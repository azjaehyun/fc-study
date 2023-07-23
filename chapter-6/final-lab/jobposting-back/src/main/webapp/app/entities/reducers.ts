import applicant from 'app/entities/applicant/applicant.reducer';
import resume from 'app/entities/resume/resume.reducer';
import jobPosting from 'app/entities/job-posting/job-posting.reducer';
import application from 'app/entities/application/application.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  applicant,
  resume,
  jobPosting,
  application,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
