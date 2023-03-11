import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api/employees/get-personnel-change-report';
const API_PATH2 = API + '/api/employees/get-monthly-employee-count-report';
const API_PATH3 = API + '/api/employees/get-employee-allocation-ratio-by-department';

export const getPersonnelChangeReport = () => {
  return axios.get(API_PATH);
};

export const getMonthlyEmployeeCountReport = () => {
  return axios.get(API_PATH2);
};

export const getEmployeeAllocationRatioByDepartment = () => {
  return axios.get(API_PATH3);
};
