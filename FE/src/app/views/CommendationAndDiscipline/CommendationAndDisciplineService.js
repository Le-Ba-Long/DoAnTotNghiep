import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api/commendation-and-disciplines';
const API_PATH2 = API + '/api/employees';

export const getListCommendationAndDiscipline = () => {
  return axios.get(API_PATH);
};

export const getListEmployee = () => {
  return axios.get(API_PATH2);
};

export const addCommendationAndDiscipline = (obj) => {
  return axios.post(API_PATH, obj);
};

export const editCommendationAndDiscipline = (obj) => {
  return axios.put(API_PATH + '/' + obj.id, obj);
};

export const deleteCommendationAndDiscipline = (id) => {
  return axios.delete(API_PATH + '/' + id);
};
