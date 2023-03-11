import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const ListPromote = Loadable(lazy(() => import('./ListPromote')));

const listPromoteRoute = [
  {
    path: '/manage/promote',
    element: <ListPromote />,
    auth: authRoles.hr,
  },
];

export default listPromoteRoute;
