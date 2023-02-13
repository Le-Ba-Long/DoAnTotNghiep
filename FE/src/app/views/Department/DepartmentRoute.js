import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const Department = Loadable(lazy(() => import('./Department')));

const departmentRoute = [
  { path: '/manage/department', element: <Department />, auth: authRoles.hr },
];

export default departmentRoute;
