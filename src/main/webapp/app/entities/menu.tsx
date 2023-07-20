import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/contracts">
        <Translate contentKey="global.menu.entities.contracts" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/customer-manager">
        <Translate contentKey="global.menu.entities.customerManager" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/projects">
        <Translate contentKey="global.menu.entities.projects" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/sales-amount">
        <Translate contentKey="global.menu.entities.salesAmount" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/partner-manager">
        <Translate contentKey="global.menu.entities.partnerManager" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/contract-price-update-history">
        <Translate contentKey="global.menu.entities.contractPriceUpdateHistory" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
