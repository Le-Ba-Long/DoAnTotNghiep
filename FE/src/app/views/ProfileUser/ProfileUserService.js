import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api/users/';
const API_PATH2 = API + '/api/auth/change-password/';

export const getProfileUser = (userName) => {
  return axios.get(API_PATH + userName);
};

export const editUser = (obj) => {
  return axios.put(API_PATH + obj.id, obj);
};

export const changePass = (obj, id) => {
  return axios.post(API_PATH2, obj);
};
