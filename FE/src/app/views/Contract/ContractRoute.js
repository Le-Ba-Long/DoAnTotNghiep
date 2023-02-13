import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const Contract = Loadable(lazy(() => import('./Contract')));

const contractRoute = [{ path: '/manage/contract', element: <Contract />, auth: authRoles.hr }];

export default contractRoute;
