import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const Certificate = Loadable(lazy(() => import('./Certificate')));

const certificateRoute = [
  { path: '/manage/certificate', element: <Certificate />, auth: authRoles.hr },
];

export default certificateRoute;
