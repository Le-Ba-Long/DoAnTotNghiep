import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const UpdateHappening = Loadable(lazy(() => import('./UpdateHappening')));

const updateHappeningRoute = [
  { path: '/manage/update-happening', element: <UpdateHappening />, auth: authRoles.hr },
];

export default updateHappeningRoute;
