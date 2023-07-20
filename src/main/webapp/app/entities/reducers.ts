import contracts from 'app/entities/contracts/contracts.reducer';
import customerManager from 'app/entities/customer-manager/customer-manager.reducer';
import projects from 'app/entities/projects/projects.reducer';
import salesAmount from 'app/entities/sales-amount/sales-amount.reducer';
import partnerManager from 'app/entities/partner-manager/partner-manager.reducer';
import contractPriceUpdateHistory from 'app/entities/contract-price-update-history/contract-price-update-history.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  contracts,
  customerManager,
  projects,
  salesAmount,
  partnerManager,
  contractPriceUpdateHistory,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
