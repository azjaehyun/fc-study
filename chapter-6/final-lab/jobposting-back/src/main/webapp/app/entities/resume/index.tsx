import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Resume from './resume';
import ResumeDetail from './resume-detail';
import ResumeUpdate from './resume-update';
import ResumeDeleteDialog from './resume-delete-dialog';

const ResumeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Resume />} />
    <Route path="new" element={<ResumeUpdate />} />
    <Route path=":id">
      <Route index element={<ResumeDetail />} />
      <Route path="edit" element={<ResumeUpdate />} />
      <Route path="delete" element={<ResumeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ResumeRoutes;
