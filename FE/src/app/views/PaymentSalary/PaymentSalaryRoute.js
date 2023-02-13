import { authRoles } from 'app/auth/authRoles';
import Loadable from 'app/components/Loadable';
import { lazy } from 'react';

const PaymentSalary = Loadable(lazy(() => import('./PaymentSalary')));

const paymentSalaryRoute = [
  { path: '/payment-salary', element: <PaymentSalary />, auth: authRoles.accountancy },
];

export default paymentSalaryRoute;
