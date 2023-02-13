import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api/contracts';
const API_PATH2 = API + '/api/employees';
const API_PATH3 = API + '/api/employees/employees-without-contract';

export const getListContract = () => {
  return axios.get(API_PATH);
};

export const getEmployeeWithoutContract = () => {
  return axios.get(API_PATH3);
};

export const editEmployee = (obj) => {
  return axios.put(API_PATH2 + '/' + obj.id, obj);
};
