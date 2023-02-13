import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api/departments';

export const getListDepartment = () => {
  return axios.get(API_PATH);
};

export const addDepartment = (obj) => {
  return axios.post(API_PATH, obj);
};

export const editDepartment = (obj) => {
  return axios.put(API_PATH + '/' + obj.id, obj);
};

export const deleteDepartment = (id) => {
  return axios.delete(API_PATH + '/' + id);
};
