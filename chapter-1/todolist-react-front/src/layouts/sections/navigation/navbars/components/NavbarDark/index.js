/* eslint-disable no-param-reassign */
/**
=========================================================
* Material Kit 2 React - v2.1.0
=========================================================

* Product Page: https://www.creative-tim.com/product/material-kit-react
* Copyright 2023 Creative Tim (https://www.creative-tim.com)

Coded by www.creative-tim.com

 =========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
*/

// Material Kit 2 React components
import MKBox from "pages/LandingPages/backlog/node_modules/components/MKBox";

// Material Kit 2 React examples
import DefaultNavbar from "pages/LandingPages/backlog/node_modules/examples/Navbars/DefaultNavbar";

// Routes
import routes from "pages/LandingPages/backlog/node_modules/routes";

function NavbarDark() {
  return (
    <MKBox variant="gradient" bgColor="dark" shadow="sm" py={0.25}>
      <DefaultNavbar
        routes={routes}
        action={{
          type: "internal",
          route: "/authentication/sign-in",
          label: "login",
          color: "info",
        }}
        transparent
        relative
        light
        center
      />
    </MKBox>
  );
}

export default NavbarDark;
