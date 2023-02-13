import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api/certificates';

export const getListCertificate = () => {
  return axios.get(API_PATH);
};

export const addCertificate = (obj) => {
  return axios.post(API_PATH, obj);
};

export const editCertificate = (obj) => {
  return axios.put(API_PATH + '/' + obj.id, obj);
};

export const deleteCertificate = (id) => {
  return axios.delete(API_PATH + '/' + id);
};
