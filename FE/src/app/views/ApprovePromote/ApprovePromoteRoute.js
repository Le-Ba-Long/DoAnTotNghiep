import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const ApprovePromote = Loadable(lazy(() => import('./ApprovePromote')));

const approvePromoteRoute = [
  { path: '/leader/approve-promote', element: <ApprovePromote />, auth: authRoles.leader },
];

export default approvePromoteRoute;
