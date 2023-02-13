import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const CandidateProfile = Loadable(lazy(() => import('./CandidateProfile')));

const candidateProfileRoute = [
  { path: '/plan/candidate', element: <CandidateProfile />, auth: authRoles.hr },
];

export default candidateProfileRoute;
