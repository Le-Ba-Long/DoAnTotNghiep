import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const Approve = Loadable(lazy(() => import('./Approve')));

const approveRoute = [
  { path: '/leader/approval-recruit', element: <Approve />, auth: authRoles.leader },
];

export default approveRoute;
