import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api/recruits';

export const getListRecruit = () => {
  return axios.get(API_PATH);
};

export const addRecruit = (obj) => {
  return axios.post(API_PATH, obj);
};

export const editRecruit = (obj) => {
  return axios.put(API_PATH + '/' + obj.id, obj);
};

export const deleteRecruit = (id) => {
  return axios.delete(API_PATH + '/' + id);
};
