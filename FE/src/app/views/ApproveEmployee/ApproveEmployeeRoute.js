import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const ApproveEmployee = Loadable(lazy(() => import('./ApproveEmployee')));

const approveEmployeeRoute = [
  { path: '/leader/approval-employee', element: <ApproveEmployee />, auth: authRoles.leader },
];

export default approveEmployeeRoute;
