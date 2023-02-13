import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const CommendationAndDiscipline = Loadable(lazy(() => import('./CommendationAndDiscipline')));

const commendationAndDisciplineRoute = [
  {
    path: '/leader/commendation-and-discipline',
    element: <CommendationAndDiscipline />,
    auth: authRoles.leader,
  },
];

export default commendationAndDisciplineRoute;
