import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const ListCommendationAndDiscipline = Loadable(
  lazy(() => import('./ListCommendationAndDiscipline'))
);

const listCommendationAndDisciplineRoute = [
  {
    path: '/commendation-and-discipline',
    element: <ListCommendationAndDiscipline />,
    auth: authRoles.accountancy,
  },
];

export default listCommendationAndDisciplineRoute;
