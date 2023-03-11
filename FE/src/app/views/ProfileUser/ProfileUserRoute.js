import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const ProfileUser = Loadable(lazy(() => import('./ProfileUser')));

const profileUserRoute = [
  { path: '/user-profile', element: <ProfileUser />, auth: authRoles.guest },
];

export default profileUserRoute;
