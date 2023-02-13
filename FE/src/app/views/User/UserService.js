import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api';

export const getListUser = () => {
  return axios.get(API_PATH + '/users');
};

export const getListRole = () => {
  return axios.get(API_PATH + '/roles');
};

export const addUser = (obj) => {
  return axios.post(API_PATH + '/auth/signup', obj);
};

export const editUser = (obj) => {
  return axios.put(API_PATH + '/users' + '/' + obj.id, obj);
};

export const deleteUser = (id) => {
  return axios.delete(API_PATH + '/users' + '/' + id);
};
