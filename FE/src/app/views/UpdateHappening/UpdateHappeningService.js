import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api/employees';
const API_PATH2 = API + '/api/contracts';
const API_PATH3 = API + '/api/certificates';
const API_PATH4 = API + '/api/languages';
const API_PATH5 = API + '/api/departments';
const API_PATH6 = API + '/api/positions';
const API_PATH7 = API + '/api/employee-history/get-employee-history-by-id';

export const getListEmployee = () => {
  return axios.get(API_PATH);
};

export const getListCertificate = () => {
  return axios.get(API_PATH3);
};

export const getListLanguage = () => {
  return axios.get(API_PATH4);
};

export const editEmployee = (obj) => {
  return axios.put(API_PATH + '/' + obj.id, obj);
};

export const getListDepartment = () => {
  return axios.get(API_PATH5);
};

export const getListPosition = () => {
  return axios.get(API_PATH6);
};

export const getContract = (id) => {
  return axios.get(API_PATH2 + '/get-contract-by-employeeId/' + id);
};

export const editContract = (obj) => {
  return axios.put(API_PATH2 + '/' + obj?.id, obj);
};

export const getListHistoryEmployee = (id) => {
  return axios.get(API_PATH7 + '/' + id);
};
