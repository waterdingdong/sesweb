import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CustomerManager from './customer-manager';
import CustomerManagerDetail from './customer-manager-detail';
import CustomerManagerUpdate from './customer-manager-update';
import CustomerManagerDeleteDialog from './customer-manager-delete-dialog';

const CustomerManagerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CustomerManager />} />
    <Route path="new" element={<CustomerManagerUpdate />} />
    <Route path=":id">
      <Route index element={<CustomerManagerDetail />} />
      <Route path="edit" element={<CustomerManagerUpdate />} />
      <Route path="delete" element={<CustomerManagerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CustomerManagerRoutes;
