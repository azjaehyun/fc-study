import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Company from './company';
import CompanyDetail from './company-detail';
import CompanyUpdate from './company-update';
import CompanyDeleteDialog from './company-delete-dialog';

const CompanyRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Company />} />
    <Route path="new" element={<CompanyUpdate />} />
    <Route path=":id">
      <Route index element={<CompanyDetail />} />
      <Route path="edit" element={<CompanyUpdate />} />
      <Route path="delete" element={<CompanyDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CompanyRoutes;
