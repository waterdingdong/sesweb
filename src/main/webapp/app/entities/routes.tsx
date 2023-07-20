import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Contracts from './contracts';
import CustomerManager from './customer-manager';
import Projects from './projects';
import SalesAmount from './sales-amount';
import PartnerManager from './partner-manager';
import ContractPriceUpdateHistory from './contract-price-update-history';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="contracts/*" element={<Contracts />} />
        <Route path="customer-manager/*" element={<CustomerManager />} />
        <Route path="projects/*" element={<Projects />} />
        <Route path="sales-amount/*" element={<SalesAmount />} />
        <Route path="partner-manager/*" element={<PartnerManager />} />
        <Route path="contract-price-update-history/*" element={<ContractPriceUpdateHistory />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
