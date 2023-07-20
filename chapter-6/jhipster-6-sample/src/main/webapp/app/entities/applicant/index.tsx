import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Applicant from './applicant';
import ApplicantDetail from './applicant-detail';
import ApplicantUpdate from './applicant-update';
import ApplicantDeleteDialog from './applicant-delete-dialog';

const ApplicantRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Applicant />} />
    <Route path="new" element={<ApplicantUpdate />} />
    <Route path=":id">
      <Route index element={<ApplicantDetail />} />
      <Route path="edit" element={<ApplicantUpdate />} />
      <Route path="delete" element={<ApplicantDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ApplicantRoutes;
