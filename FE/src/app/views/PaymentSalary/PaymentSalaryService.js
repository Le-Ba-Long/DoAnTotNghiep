import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api/payment-salarys';
const API_PATH2 = API + '/api/employees';

export const getListSalary = (searchObject, employeeId) => {
  return axios.post(API_PATH + '/searchByDto', searchObject);
};

export const getListEmployee = () => {
  return axios.get(API_PATH2);
};
