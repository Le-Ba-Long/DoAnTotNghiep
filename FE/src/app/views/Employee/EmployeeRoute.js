import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const Employee = Loadable(lazy(() => import('./Employee')));

const employeeRoute = [{ path: '/manage/employee', element: <Employee />, auth: authRoles.hr }];

export default employeeRoute;
