/*
=========================================================
* Material Kit 2 React - v2.1.0
=========================================================

* Product Page: https://www.creative-tim.com/product/material-kit-react
* Copyright 2023 Creative Tim (https://www.creative-tim.com)

Coded by www.creative-tim.com

 =========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
*/

// @mui material components
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";

// Material Kit 2 React components
import MKBox from "components/MKBox";

// Material Kit 2 React examples
import RotatingCard from "examples/Cards/RotatingCard";
import RotatingCardFront from "examples/Cards/RotatingCard/RotatingCardFront";
import RotatingCardBack from "examples/Cards/RotatingCard/RotatingCardBack";
import DefaultInfoCard from "examples/Cards/InfoCards/DefaultInfoCard";

// Images
import bgFront from "assets/images/rotating-card-bg-front.jpeg";
import bgBack from "assets/images/rotating-card-bg-back.jpeg";

function Information() {
  return (
    <MKBox component="section" py={6} my={6}>
      <Container>
        <Grid container item xs={11} spacing={3} alignItems="center" sx={{ mx: "auto" }}>
          <Grid item xs={12} lg={4} sx={{ mx: "auto" }}>
            <RotatingCard>
              <RotatingCardFront
                image={bgFront}
                icon="touch_app"
                title={
                  <>
                    Fast Campus
                    <br />
                    Project
                  </>
                }
                description="패스트 캠퍼스 일정관리 프로젝트"
              />
              <RotatingCardBack
                image={bgBack}
                title="Fast Campus Management Project"
                description="This service provides a calendar for project schedule management. Stay on top of your project while checking your schedule."
                action={{
                  type: "internal",
                  route: "/sections/page-sections/page-headers",
                  label: "Get started.  ",
                }}
              />
            </RotatingCard>
          </Grid>
          <Grid item xs={12} lg={7} sx={{ ml: "auto" }}>
            <Grid container spacing={3}>
              <Grid item xs={12} md={6}>
                <DefaultInfoCard
                  icon="content_copy"
                  title="프로젝트 WBS"
                  description="WBS는 프로젝트를 단계적으로 분해하여 작은 단위로 세분화하고, 각각의 단위에 대한 책임과 역할을 명확히 정의하며, 전체 프로젝트의 일정을 계획하는 데 도움을 줍니다."
                />
              </Grid>
              <Grid item xs={12} md={6}>
                <DefaultInfoCard
                  icon="flip_to_front"
                  title="Todo List(Back log)"
                  description="프로젝트를 진행하면서 필요한 일감 Task를 관리하고 운영합니다."
                />
              </Grid>
            </Grid>
            <Grid container spacing={3} sx={{ mt: { xs: 0, md: 6 } }}>
              <Grid item xs={12} md={6}>
                <DefaultInfoCard
                  icon="price_change"
                  title="인력관리"
                  description="프로젝트를 진행하면서 인력관리를 지원하는 서비스 입니다."
                />
              </Grid>
              <Grid item xs={12} md={6}>
                <DefaultInfoCard
                  icon="devices"
                  title="프로젝트 비용관리"
                  description="프로젝트를 진행하면서 필요한 비용이나 회계 정산을 지원하는 서비스 입니다."
                />
              </Grid>
            </Grid>
          </Grid>
        </Grid>
      </Container>
    </MKBox>
  );
}

export default Information;
