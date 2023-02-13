import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api/time-keepings';
const API_PATH2 = API + '/api/employees';

export const getListTimeKeeping = (searchObject) => {
  return axios.post(API_PATH + '/searchByDto', searchObject);
};

export const getListEmployee = () => {
  return axios.get(API_PATH2);
};

export const editTimeKeeping = (obj) => {
  return axios.put(API_PATH + '/' + obj?.id, obj);
};

export const addTimeKeeping = (obj) => {
  return axios.post(API_PATH, obj);
};

export const deleteListTimeKeeping = (id) => {
  return axios.delete(API_PATH + '/' + id);
};
