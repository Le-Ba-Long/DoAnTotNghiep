import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const StatisticReport = Loadable(lazy(() => import('./StatisticReport')));

const statisticReportRoute = [
  { path: '/manage/statistic-report', element: <StatisticReport />, auth: authRoles.hr },
];

export default statisticReportRoute;
