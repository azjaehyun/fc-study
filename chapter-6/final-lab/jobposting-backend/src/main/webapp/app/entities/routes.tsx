import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Company from './company';
import Applicant from './applicant';
import JobPosting from './job-posting';
import Application from './application';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="company/*" element={<Company />} />
        <Route path="applicant/*" element={<Applicant />} />
        <Route path="job-posting/*" element={<JobPosting />} />
        <Route path="application/*" element={<Application />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
