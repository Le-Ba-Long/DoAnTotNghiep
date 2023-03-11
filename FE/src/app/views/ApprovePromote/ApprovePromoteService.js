import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api/commendation-and-disciplines';
const API_PATH2 = API + '/api/employees';
const API_PATH3 = API + '/api/contracts';

export const getListPromote = () => {
  return axios.get(API_PATH);
};

export const getListEmployee = () => {
  return axios.get(API_PATH2);
};

export const addPromote = (obj) => {
  return axios.post(API_PATH, obj);
};

export const editPromote = (obj) => {
  return axios.put(API_PATH + '/' + obj.id, obj);
};

export const deletePromote = (id) => {
  return axios.delete(API_PATH + '/' + id);
};

export const getContract = (id) => {
  return axios.get(API_PATH3 + '/get-contract-by-employeeId/' + id);
};
