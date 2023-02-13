import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api/positions';

export const getListPosition = () => {
  return axios.get(API_PATH);
};

export const addPosition = (obj) => {
  return axios.post(API_PATH, obj);
};

export const editPosition = (obj) => {
  return axios.put(API_PATH + '/' + obj.id, obj);
};

export const deletePosition = (id) => {
  return axios.delete(API_PATH + '/' + id);
};
