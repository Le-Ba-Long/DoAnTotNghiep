import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const ApproveCandidate = Loadable(lazy(() => import('./ApproveCandidate')));

const approveCandidateRoute = [
  {
    path: '/leader/approval-candidate',
    element: <ApproveCandidate />,
    auth: authRoles.hr,
  },
];

export default approveCandidateRoute;
