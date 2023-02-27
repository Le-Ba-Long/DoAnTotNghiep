import { Card, Grid, styled, useTheme } from "@mui/material";
import { Fragment, useState, useEffect } from "react";
import Campaigns from "./shared/Campaigns";
import LineChart from "./shared/LineChart";
import ComparisonChart from "./shared/ComparisonChart";
import DoughnutChart from "./shared/Doughnut";
import RowCards from "./shared/RowCards";
import StatCards from "./shared/StatCards";
import StatCards2 from "./shared/StatCards2";
import TopSellingTable from "./shared/TopSellingTable";
import UpgradeCard from "./shared/UpgradeCard";
import {
  getPersonnelChangeReport,
  getMonthlyEmployeeCountReport,
  getEmployeeAllocationRatioByDepartment,
} from "./AnalyticsService";
import { toast } from "react-toastify";

const ContentBox = styled("div")(({ theme }) => ({
  margin: "30px",
  [theme.breakpoints.down("sm")]: { margin: "16px" },
}));

const Title = styled("span")(() => ({
  fontSize: "1rem",
  fontWeight: "500",
  marginRight: ".5rem",
  textTransform: "capitalize",
}));

const SubTitle = styled("span")(({ theme }) => ({
  fontSize: "0.875rem",
  color: theme.palette.text.secondary,
}));

const H4 = styled("h4")(({ theme }) => ({
  fontSize: "1rem",
  fontWeight: "500",
  marginBottom: "16px",
  textTransform: "capitalize",
  color: theme.palette.text.secondary,
}));

const Analytics = () => {
  const { palette } = useTheme();

  const [personnelChangeReport, setPersonnelChangeReport] = useState([]);
  const [monthlyEmployeeCountReport, setMonthlyEmployeeCountReport] = useState(
    []
  );
  const [
    employeeAllocationRatioByDepartment,
    setEmployeeAllocationRatioByDepartment,
  ] = useState([]);

  useEffect(() => {
    getPersonnelChangeReport()
      .then((res) => {
        if (res?.data?.statusCode === 200) {
          setPersonnelChangeReport(res?.data?.data);
        } else {
          toast.warning(res?.data?.message);
        }
      })
      .catch((err) => toast.error("Có lỗi xảy ra"));

    getMonthlyEmployeeCountReport()
      .then((res) => {
        if (res?.data?.statusCode === 200) {
          setMonthlyEmployeeCountReport(res?.data?.data);
        } else {
          toast.warning(res?.data?.message);
        }
      })
      .catch((err) => toast.error("Có lỗi xảy ra"));

    getEmployeeAllocationRatioByDepartment()
      .then((res) => {
        if (res?.data?.statusCode === 200) {
          setEmployeeAllocationRatioByDepartment(res?.data?.data);
        } else {
          toast.warning(res?.data?.message);
        }
      })
      .catch((err) => toast.error("Có lỗi xảy ra"));
  }, []);

  return (
    <Fragment>
      <ContentBox className="analytics">
        <Grid container spacing={3}>
          <Grid item lg={12} md={12} sm={12} xs={12}>
            <Card sx={{ px: 3, py: 2, mb: 3 }}>
              <Title>Tỉ lệ nhân sự phân bổ theo các phòng ban năm 2023</Title>
              <SubTitle>Công ty Oceantech</SubTitle>

              <DoughnutChart
                height="500px"
                color={[
                  "#F5EAEA",
                  "#FFB84C",
                  "#F16767",
                  "#A459D1",
                  "#4D455D",
                  "#E96479",
                  "#F5E9CF",
                  "#7DB9B6",
                  "#F9F54B",
                  "#8BF5FA",
                  "#F2CD5C",
                  "#A7727D",
                ]}
                data={employeeAllocationRatioByDepartment}
              />
            </Card>
          </Grid>
          <Grid item lg={6} md={6} sm={12} xs={12}>
            <Card sx={{ px: 3, py: 2, mb: 3 }}>
              <Title>Biến động nhân sự năm 2023</Title>
              <SubTitle>Công ty Oceantech</SubTitle>
              <LineChart
                height="350px"
                color={[palette.primary.main, palette.secondary.main]}
                data={personnelChangeReport}
              />
            </Card>
          </Grid>

          <Grid item lg={6} md={6} sm={12} xs={12}>
            <Card sx={{ px: 3, py: 2, mb: 3 }}>
              <Title>Số lượng nhân sự năm 2023</Title>
              <SubTitle>Công ty Oceantech</SubTitle>

              <ComparisonChart
                height="350px"
                color={[palette.primary.dark]}
                data={monthlyEmployeeCountReport[0]}
              />
            </Card>
          </Grid>
        </Grid>
      </ContentBox>
    </Fragment>
  );
};

export default Analytics;
