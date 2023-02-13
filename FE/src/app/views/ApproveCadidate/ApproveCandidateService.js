import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api/candidate-profiles';

export const getListCandidate = () => {
  return axios.get(API_PATH);
};

export const addCandidate = (obj) => {
  return axios.post(API_PATH, obj);
};

export const editCandidate = (obj) => {
  return axios.put(API_PATH + '/' + obj.id, obj);
};

export const deleteCandidate = (id) => {
  return axios.delete(API_PATH + '/' + id);
};
