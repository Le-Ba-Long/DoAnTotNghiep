import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api/employees/get-personnel-change-report';
const API_PATH2 = API + '/api/employees/get-monthly-employee-count-report';
const API_PATH3 = API + '/api/employees/get-employee-allocation-ratio-by-department';
const API_PATH4 = API + '/api/employees/get-employee-birthday-in-range';
const API_PATH5 = API + '/api/employees/get-employees-about-to-expire-contract';
const API_PATH6 = API + '/api/employees/export-employee-birth-day-in-range';
const API_PATH7 = API + '/api/employees/export-employees-about-to-expire-contract';

export const getPersonnelChangeReport = () => {
  return axios.get(API_PATH);
};

export const getMonthlyEmployeeCountReport = () => {
  return axios.get(API_PATH2);
};

export const getEmployeeAllocationRatioByDepartment = () => {
  return axios.get(API_PATH3);
};

export const getEmployeeBirthdayInRange = (obj) => {
  return axios.get(API_PATH4 + `?startMonth=${obj.startMonth}&endMonth=${obj.endMonth}`);
};

export const getEmployeesAboutToExpireContract = (arr) => {
  return axios.post(API_PATH5, arr);
};

export const exportEmployeeBirthdayInRange = (obj) => {
  return axios({
    method: 'GET',
    url: API_PATH6 + `?startMonth=${obj.startMonth}&endMonth=${obj.endMonth}`,
    responseType: 'blob',
  });
};

export const exportEmployeesAboutToExpireContract = (arr) => {
  // return axios.post(API_PATH7, arr);
  return axios({
    method: 'POST',
    url: API_PATH7,
    data: arr,
    responseType: 'blob',
  });
};
