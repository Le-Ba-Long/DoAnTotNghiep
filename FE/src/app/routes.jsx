import AuthGuard from 'app/auth/AuthGuard';
import chartsRoute from 'app/views/charts/ChartsRoute';
import departmentRoute from 'app/views/Department/DepartmentRoute';
import positionRoute from 'app/views/Position/PositionRoute';
import certificateRoute from 'app/views/Certificate/CertificateRoute';
import languageRoute from 'app/views/Language/LanguageRoute';
import recruitRoute from 'app/views/Recruit/RecruitRoute';
import userRoute from 'app/views/User/UserRoute';
import approveRoute from 'app/views/Approve/ApproveRoute';
import listRecruitApprovedRoute from 'app/views/ListRecruitApproved/ListRecruitApprovedRoute';
import candidateProfileRoute from 'app/views/CandidateProfile/CandidateProfileRoute';
import approveCandidateRoute from 'app/views/ApproveCadidate/ApproveCadidateRoute';
import createEmployeeRoute from 'app/views/CreateEmployee/EmployeeRoute';
import approveEmployeeRoute from 'app/views/ApproveEmployee/ApproveEmployeeRoute';
import contractRoute from 'app/views/Contract/ContractRoute';
import timeKeepingRoute from 'app/views/TimeKeeping/TimeKeepingRoute';
import paymentSalaryRoute from 'app/views/PaymentSalary/PaymentSalaryRoute';
import employeeRoute from 'app/views/Employee/EmployeeRoute';
import commendationAndDisciplineRoute from 'app/views/CommendationAndDiscipline/CommendationAndDisciplineRoute';
import listCommendationAndDisciplineRoute from 'app/views/ListCommendationAndDiscipline/ListCommendationAndDisciplineRoute';
import updateHappeningRoute from 'app/views/UpdateHappening/UpdateHappeningRoute';
import dashboardRoutes from 'app/views/dashboard/DashboardRoutes';
import materialRoutes from 'app/views/material-kit/MaterialRoutes';
import NotFound from 'app/views/sessions/NotFound';
import sessionRoutes from 'app/views/sessions/SessionRoutes';
import { Navigate } from 'react-router-dom';
import MatxLayout from './components/MatxLayout/MatxLayout';

const routes = [
  {
    element: (
      <AuthGuard>
        <MatxLayout />
      </AuthGuard>
    ),
    children: [
      ...dashboardRoutes,
      ...chartsRoute,
      ...materialRoutes,
      ...departmentRoute,
      ...positionRoute,
      ...certificateRoute,
      ...languageRoute,
      ...recruitRoute,
      ...userRoute,
      ...approveRoute,
      ...listRecruitApprovedRoute,
      ...candidateProfileRoute,
      ...approveCandidateRoute,
      ...createEmployeeRoute,
      ...approveEmployeeRoute,
      ...contractRoute,
      ...timeKeepingRoute,
      ...paymentSalaryRoute,
      ...employeeRoute,
      ...commendationAndDisciplineRoute,
      ...listCommendationAndDisciplineRoute,
      ...updateHappeningRoute,
    ],
  },
  ...sessionRoutes,
  { path: '/', element: <Navigate to="dashboard/default" /> },
  { path: '*', element: <NotFound /> },
];

export default routes;
