import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const LayOffOrQuitJob = Loadable(lazy(() => import('./LayOffOrQuitJob')));

const layOffOrQuitJobRoute = [
  { path: '/leader/lay-off-or-quit-job', element: <LayOffOrQuitJob />, auth: authRoles.leader },
];

export default layOffOrQuitJobRoute;
