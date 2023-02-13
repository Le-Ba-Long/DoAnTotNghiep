import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api/languages';

export const getListLanguage = () => {
  return axios.get(API_PATH);
};

export const addLanguage = (obj) => {
  return axios.post(API_PATH, obj);
};

export const editLanguage = (obj) => {
  return axios.put(API_PATH + '/' + obj.id, obj);
};

export const deleteLanguage = (id) => {
  return axios.delete(API_PATH + '/' + id);
};
