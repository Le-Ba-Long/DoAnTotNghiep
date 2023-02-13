import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const ListRecruitApproved = Loadable(lazy(() => import('./ListRecruitApproved')));

const listRecruitApprovedRoute = [
  { path: '/leader/approved', element: <ListRecruitApproved />, auth: authRoles.leader },
];

export default listRecruitApprovedRoute;
