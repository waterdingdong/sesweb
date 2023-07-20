import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Contracts from './contracts';
import ContractsDetail from './contracts-detail';
import ContractsUpdate from './contracts-update';
import ContractsDeleteDialog from './contracts-delete-dialog';

const ContractsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Contracts />} />
    <Route path="new" element={<ContractsUpdate />} />
    <Route path=":id">
      <Route index element={<ContractsDetail />} />
      <Route path="edit" element={<ContractsUpdate />} />
      <Route path="delete" element={<ContractsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ContractsRoutes;
