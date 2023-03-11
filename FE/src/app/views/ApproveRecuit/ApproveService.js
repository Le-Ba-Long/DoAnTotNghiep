import axios from 'axios.js';
import { API } from 'app/constant';

const API_PATH = API + '/api/recruits';
const API_PATH2 = API + '/api/planApprovalAdmin/approval-recruit';

export const getListRecruit = () => {
  return axios.get(API_PATH);
};

export const approveRecruit = (item) => {
  return axios.put(API_PATH2 + '/' + item.id, item);
};

export const editRecruit = (obj) => {
  return axios.put(API_PATH + '/' + obj.id, obj);
};

export const deleteRecruit = (id) => {
  return axios.delete(API_PATH + '/' + id);
};
