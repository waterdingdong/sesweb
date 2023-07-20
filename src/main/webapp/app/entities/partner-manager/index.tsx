import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PartnerManager from './partner-manager';
import PartnerManagerDetail from './partner-manager-detail';
import PartnerManagerUpdate from './partner-manager-update';
import PartnerManagerDeleteDialog from './partner-manager-delete-dialog';

const PartnerManagerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PartnerManager />} />
    <Route path="new" element={<PartnerManagerUpdate />} />
    <Route path=":id">
      <Route index element={<PartnerManagerDetail />} />
      <Route path="edit" element={<PartnerManagerUpdate />} />
      <Route path="delete" element={<PartnerManagerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PartnerManagerRoutes;
