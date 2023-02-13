import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api/employees';
const API_PATH2 = API + '/api/candidate-profiles';
const API_PATH3 = API + '/api/certificates';
const API_PATH4 = API + '/api/languages';
const API_PATH5 = API + '/api/departments';
const API_PATH6 = API + '/api/positions';

export const getListEmployee = () => {
  return axios.get(API_PATH);
};

export const getListCandidate = () => {
  return axios.get(API_PATH2);
};

export const getListCertificate = () => {
  return axios.get(API_PATH3);
};

export const getListLanguage = () => {
  return axios.get(API_PATH4);
};

export const getListDepartment = () => {
  return axios.get(API_PATH5);
};

export const getListPosition = () => {
  return axios.get(API_PATH6);
};

export const addEmployee = (obj) => {
  return axios.post(API_PATH, obj);
};

export const editEmployee = (obj) => {
  return axios.put(API_PATH + '/' + obj.id, obj);
};

export const deleteEmployee = (id) => {
  return axios.delete(API_PATH + '/' + id);
};
