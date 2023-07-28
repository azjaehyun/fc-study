import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import JobPosting from './job-posting';
import JobPostingDetail from './job-posting-detail';
import JobPostingUpdate from './job-posting-update';
import JobPostingDeleteDialog from './job-posting-delete-dialog';

const JobPostingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<JobPosting />} />
    <Route path="new" element={<JobPostingUpdate />} />
    <Route path=":id">
      <Route index element={<JobPostingDetail />} />
      <Route path="edit" element={<JobPostingUpdate />} />
      <Route path="delete" element={<JobPostingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default JobPostingRoutes;
