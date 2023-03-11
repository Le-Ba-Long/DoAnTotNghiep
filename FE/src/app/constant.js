import axios from 'axios';

export const API = 'http://14.225.205.11:8888';

//export const API = 'http://localhost:8888';
export const checkStatus = (value) => {
  let message;
  let color;
  switch (value) {
    case -1:
      message = 'Không được phê duyệt';
      color = 'status-rejected';
      break;
    case 1:
      message = 'Bản nháp';
      color = 'status-new';
      break;
    case 2:
      message = 'Chờ duyệt';
      color = 'status-pending';
      break;
    case 3:
      message = 'Đã duyệt';
      color = 'status-approved';
      break;
    case 4:
      message = 'Đang thực hiện';
      color = 'status-processing';
      break;
    case 5:
      message = 'Đã kết thúc';
      color = 'status-finsished';
      break;
    case 6:
      message = 'Từ chối';
      color = 'status-rejected';
      break;
    case 7:
      message = 'Tiếp nhận';
      break;
    case 8:
      message = 'Đạt';
      color = 'status-approved';
      break;
    case 9:
      message = 'Không đạt';
      color = 'status-not-pass';
      break;
    case 10:
      message = 'Yêu cầu chỉnh sửa';
      color = 'status-pending';
      break;
    case 11:
      message = 'Chờ đợi';
      break;
    case 12:
      message = 'Thử việc';
      color = 'status-processing';
      break;
    case 13:
      message = 'Kết thúc thử việc';
      break;
    case 14:
      message = 'Chính thức';
      color = 'status-approved';
      break;
    case 15:
      message = 'Nghỉ việc';
      color = 'status-not-pass';
      break;
    case 16:
      message = 'Sa thải';
      break;
    case 17:
      message = 'Chờ xử lý';
      color = 'status-new';
      break;
    case 18:
      message = 'Chờ phỏng vấn';
      color = 'status-pending';
      break;
    case 19:
      message = 'Chuyển đổi hồ sơ ứng viên';
      color = 'status-transfer';
      break;
    default:
      message = 'Không trạng thái';
  }
  return {
    message,
    color,
  };
};

export const sex = [{ name: 'Nam' }, { name: 'Nữ' }, { name: 'Khác' }];

export const months = [
  { name: 'Tháng 1', value: 1 },
  { name: 'Tháng 2', value: 2 },
  { name: 'Tháng 3', value: 3 },
  { name: 'Tháng 4', value: 4 },
  { name: 'Tháng 5', value: 5 },
  { name: 'Tháng 6', value: 6 },
  { name: 'Tháng 7', value: 7 },
  { name: 'Tháng 8', value: 8 },
  { name: 'Tháng 9', value: 9 },
  { name: 'Tháng 10', value: 10 },
  { name: 'Tháng 11', value: 11 },
  { name: 'Tháng 12', value: 12 },
];

export const uploadImage = (image) => {
  let bodyFormData = new FormData();
  bodyFormData.append('file', image);
  return axios.post(API + '/api/files/uploadFile', bodyFormData);
};

export const trangThaiNhanVien = [
  { name: 'Bản nháp', value: 1 },
  { name: 'Đã duyệt', value: 3 },
  { name: 'Từ chối', value: 6 },
  { name: 'Yêu cầu chỉnh sửa', value: 10 },
  { name: 'Thử việc', value: 12 },
  { name: 'Chính thức', value: 14 },
];

export const trangThaiNhanVienCNDB = [
  { name: 'Thử việc', value: 12 },
  { name: 'Chính thức', value: 14 },
  { name: 'Nghỉ việc', value: 15 },
  { name: 'Sa thải', value: 16 },
];

export const trangThaiKeHoanhTD = [
  { name: 'Bản nháp', value: 1 },
  { name: 'Đang thực hiện', value: 4 },
  { name: 'Kết thúc', value: 5 },
  { name: 'Từ chối', value: 6 },
  { name: 'Yêu cầu chỉnh sửa', value: 10 },
];

export const quyetDinh = [
  { name: 'Khen thưởng', value: 2 },
  { name: 'Kỷ luật', value: 1 },
];

export const colorTable = {
  HEADER: '#539165',
  ROW: '#F9F9F9',
  TEXTHEADER: '#FFF',
  TEXTROW: '#000',
};
