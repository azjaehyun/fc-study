import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/applicant">
        <Translate contentKey="global.menu.entities.applicant" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/resume">
        <Translate contentKey="global.menu.entities.resume" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/application">
        <Translate contentKey="global.menu.entities.application" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
