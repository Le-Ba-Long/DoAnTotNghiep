import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const Recruit = Loadable(lazy(() => import('./Recruit')));

const recruitRoute = [{ path: '/plan/recruit', element: <Recruit />, auth: authRoles.hr }];

export default recruitRoute;
