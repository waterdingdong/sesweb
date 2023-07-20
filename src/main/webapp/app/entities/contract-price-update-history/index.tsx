import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ContractPriceUpdateHistory from './contract-price-update-history';
import ContractPriceUpdateHistoryDetail from './contract-price-update-history-detail';
import ContractPriceUpdateHistoryUpdate from './contract-price-update-history-update';
import ContractPriceUpdateHistoryDeleteDialog from './contract-price-update-history-delete-dialog';

const ContractPriceUpdateHistoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ContractPriceUpdateHistory />} />
    <Route path="new" element={<ContractPriceUpdateHistoryUpdate />} />
    <Route path=":id">
      <Route index element={<ContractPriceUpdateHistoryDetail />} />
      <Route path="edit" element={<ContractPriceUpdateHistoryUpdate />} />
      <Route path="delete" element={<ContractPriceUpdateHistoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ContractPriceUpdateHistoryRoutes;
