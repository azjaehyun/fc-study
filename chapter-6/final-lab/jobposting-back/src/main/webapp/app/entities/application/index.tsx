import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Application from './application';
import ApplicationDetail from './application-detail';
import ApplicationUpdate from './application-update';
import ApplicationDeleteDialog from './application-delete-dialog';

const ApplicationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Application />} />
    <Route path="new" element={<ApplicationUpdate />} />
    <Route path=":id">
      <Route index element={<ApplicationDetail />} />
      <Route path="edit" element={<ApplicationUpdate />} />
      <Route path="delete" element={<ApplicationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ApplicationRoutes;
