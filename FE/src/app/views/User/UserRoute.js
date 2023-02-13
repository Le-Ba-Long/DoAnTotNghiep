import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const User = Loadable(lazy(() => import('./User')));

const userRoute = [{ path: '/manage/user', element: <User />, auth: authRoles.leader }];

export default userRoute;
