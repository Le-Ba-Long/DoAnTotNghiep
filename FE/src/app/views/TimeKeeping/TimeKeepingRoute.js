import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const TimeKeeping = Loadable(lazy(() => import('./TimeKeeping')));

const timeKeepingRoute = [
  { path: '/manage/time-keeping', element: <TimeKeeping />, auth: authRoles.hr },
];

export default timeKeepingRoute;
