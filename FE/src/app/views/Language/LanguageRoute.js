import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const Language = Loadable(lazy(() => import('./Language')));

const languageRoute = [{ path: '/manage/language', element: <Language />, auth: authRoles.hr }];

export default languageRoute;
