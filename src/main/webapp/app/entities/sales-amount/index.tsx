import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SalesAmount from './sales-amount';
import SalesAmountDetail from './sales-amount-detail';
import SalesAmountUpdate from './sales-amount-update';
import SalesAmountDeleteDialog from './sales-amount-delete-dialog';

const SalesAmountRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SalesAmount />} />
    <Route path="new" element={<SalesAmountUpdate />} />
    <Route path=":id">
      <Route index element={<SalesAmountDetail />} />
      <Route path="edit" element={<SalesAmountUpdate />} />
      <Route path="delete" element={<SalesAmountDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SalesAmountRoutes;
