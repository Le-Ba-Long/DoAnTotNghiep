import React, { useState, useEffect, useRef } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import CloseIcon from '@mui/icons-material/Close';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import EmployeeInformation from './EmployeeInformation';
import RelatedInformation from './RelatedInformation';
import WorkingPosition from './WorkingPosition';
import { addEmployee, editEmployee } from './EmployeeService';
import ConfirmationDialog from '../../components/ConfirmationDialog';
import { toast } from 'react-toastify';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import ArrowBackIosNewIcon from '@mui/icons-material/ArrowBackIosNew';

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && <Box style={{ padding: '0 20px' }}>{children}</Box>}
    </div>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.number.isRequired,
  value: PropTypes.number.isRequired,
};

function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

export default function EmployeeDialog(props) {
  const { open, handleClose, handleCloseDialog, candidate, item } = props;
  const [value, setValue] = React.useState(0);
  const [employee, setEmployee] = useState({});
  const [method, setMethod] = useState('');

  console.log(candidate);

  const firstRender = useRef(false);
  const formik = useFormik({
    initialValues: {
      code: item?.id ? item?.code : '',
      fullName: item?.id ? item?.fullName : candidate?.id ? candidate?.fullName : '',
      dateOfBirth: item?.id ? item?.dateOfBirth : candidate?.id ? candidate?.dateOfBirth : null,
      sex: item?.id ? item?.sex : candidate?.id ? candidate?.sex : '',
      image: item?.id ? item?.image : candidate?.id ? candidate?.image : '',
      imageName: item?.id ? item?.image : candidate?.id ? candidate?.imageName : '',
      phone: item?.id ? item?.phone : candidate?.id ? candidate?.phone : '',
      email: item?.id ? item?.email : candidate?.id ? candidate?.email : '',
      education: item?.id ? item?.education : candidate?.id ? candidate?.education : '',
      major: item?.id ? item?.major : candidate?.id ? candidate?.major : '',
      nation: item?.id ? item?.nation : candidate?.id ? candidate?.nation : '',
      religion: item?.id ? item?.religion : candidate?.id ? candidate?.religion : '',
      address: item?.id ? item?.address : candidate?.id ? candidate?.address : '',
      numberIdentityCard: item?.id
        ? item?.numberIdentityCard
        : candidate?.id
        ? candidate?.numberIdentityCard
        : '',
      issuedDateIdentityCard: item?.id
        ? item?.issuedDateIdentityCard
        : candidate?.id
        ? candidate?.issuedDateIdentityCard
        : null,
      placeOfGrantIdentityCard: item?.id
        ? item?.placeOfGrantIdentityCard
        : candidate?.id
        ? candidate?.placeOfGrantIdentityCard
        : '',
      numberMedicalInsurance: item?.id
        ? item?.numberMedicalInsurance
        : candidate?.id
        ? candidate?.numberMedicalInsurance
        : '',
      issuedDateMedicalInsurance: item?.id
        ? item?.issuedDateMedicalInsurance
        : candidate?.id
        ? candidate?.issuedDateMedicalInsurance
        : null,
      placeOfIssueMedicalInsurance: item?.id
        ? item?.placeOfIssueMedicalInsurance
        : candidate?.id
        ? candidate?.placeOfIssueMedicalInsurance
        : '',
      numberSocialInsurance: item?.id
        ? item?.numberSocialInsurance
        : candidate?.id
        ? candidate?.numberSocialInsurance
        : '',
      issuedDateSocialInsurance: item?.id
        ? item?.issuedDateSocialInsurance
        : candidate?.id
        ? candidate?.issuedDateSocialInsurance
        : null,
      placeOfIssueSocialInsurance: item?.id
        ? item?.placeOfIssueSocialInsurance
        : candidate?.id
        ? candidate?.placeOfIssueSocialInsurance
        : '',
      certificate: item?.id ? item?.certificate : candidate?.id ? candidate?.certificate : null,
      languages: item?.id ? item?.languages : candidate?.id ? candidate?.certificate : null,
      department: item?.id ? item?.department : candidate?.id ? candidate?.department : null,
      positions: item?.id ? item?.positions : candidate?.id ? candidate?.positions : null,
      candidateProfileDto: candidate?.id ? candidate : null,
      titleRecruit: item?.id ? item?.titleRecruit : candidate?.id ? candidate?.titleRecruit : null,
    },
    enableReinitialize: true,
    validateOnChange: false,
    validateOnBlur: false,
    validationSchema: Yup.object({
      code: Yup.string()
        .matches(/^MaNV[0-9]{4}$/, 'Mã bằng cấp chưa đúng format VD:(MaNV9999)')
        .required('Vui lòng nhập trường này'),
      fullName: Yup.string().required('Vui lòng nhập trường này'),
      dateOfBirth: Yup.date()
        .nullable()
        .typeError('Sai định dạng ngày!')
        .required('Vui lòng nhập trường này'),
      sex: Yup.string().required('Vui lòng nhập trường này!'),
      email: Yup.string()
        .matches(/^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[A-Za-z]+$/, 'Đây không phải là email!')
        .required('Vui lòng nhập Email!'),
      phone: Yup.string()
        .matches(/(84|0[3|5|7|8|9])+([0-9]{8})\b/, 'Số điện thoại không hợp lệ')
        .required('Vui lòng nhập số điện thoại!'),
      education: Yup.string().required('Vui lòng nhập trường này'),
      major: Yup.string().required('Vui lòng nhập trường này'),
      nation: Yup.string().required('Vui lòng nhập trường này'),
      religion: Yup.string().required('Vui lòng nhập trường này'),
      address: Yup.string().required('Vui lòng nhập trường này'),
      numberIdentityCard: Yup.number()
        .typeError('Số CCCD phải là số!')
        .required('Vui lòng nhập trường này'),
      issuedDateIdentityCard: Yup.date()
        .nullable()
        .typeError('Sai định dạng ngày!')
        .required('Vui lòng nhập trường này'),
      placeOfGrantIdentityCard: Yup.string().required('Vui lòng nhập trường này'),
      numberMedicalInsurance: Yup.number().nullable().typeError('Số bảo hiểm y tế phải là số!'),
      // .required('Vui lòng nhập trường này'),
      issuedDateMedicalInsurance: Yup.date().nullable().typeError('Sai định dạng ngày!'),
      // .required('Vui lòng nhập trường này'),
      placeOfIssueMedicalInsurance: Yup.string().nullable(),
      // .required('Vui lòng nhập trường này'),
      numberSocialInsurance: Yup.number().nullable().typeError('Số bảo hiểm xã hội phải là số!'),
      // .required('Vui lòng nhập trường này'),
      issuedDateSocialInsurance: Yup.date().nullable().typeError('Sai định dạng ngày!'),
      // .required('Vui lòng nhập trường này'),
      placeOfIssueSocialInsurance: Yup.string().nullable(),
      // .required('Vui lòng nhập trường này'),
      certificate: Yup.object().nullable().required('Vui lòng chọn trường này'),
      languages: Yup.array().nullable().required('Vui lòng chọn trường này'),
      department: Yup.object().nullable().required('Vui lòng chọn trường này'),
      positions: Yup.array().nullable().required('Vui lòng chọn trường này'),
      titleRecruit: Yup.string().nullable().required('Vui lòng nhập trường này'),
    }),
    onSubmit: (values) => {
      values.id = item?.id;
      values.status = method;
      setEmployee(values);
    },
  });

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  useEffect(() => {
    if (Object.keys(formik.errors).length !== 0 && formik.errors.constructor === Object) {
      toast.warning('Vui lòng nhập đủ trường');
    }
  }, [formik.errors]);

  useEffect(() => {
    if (employee.id) {
      if (employee.status === 1) {
        editEmployee(employee)
          .then((res) => {
            if (res.data.statusCode === 200) {
              toast.success('Sửa hồ sơ thành công');
              setEmployee({});
              handleClose();
            } else {
              toast.warning(res.data.message);
            }
          })
          .catch((err) => toast.error('Có lỗi xảy ra!'));
        // } else if (employee.status === 10) {
        //   editEmployee(employee)
        //     .then((res) => {
        //       if (res.data.statusCode === 200) {
        //         toast.success('Chỉnh sửa thành công');
        //         setEmployee({});
        //         handleClose();
        //       } else {
        //         toast.warning(res.data.message);
        //       }
        //     })
        //     .catch((err) => toast.error('Có lỗi xảy ra!'));
      }
    } else {
      if (employee.status === 1) {
        addEmployee(employee)
          .then((res) => {
            if (res.data.statusCode === 200) {
              toast.success('Thêm mới hồ sơ thành công');
              setEmployee({});
              handleClose();
            } else {
              toast.warning(res.data.message);
            }
          })
          .catch((err) => toast.error('Có lỗi xảy ra!'));
      }
    }
  }, [employee.status]);

  useEffect(() => {
    if (firstRender.current && method) {
      formik.handleSubmit();
    } else {
      firstRender.current = true;
    }
  }, [method]);

  useEffect(() => {
    console.log(formik.values);
    setMethod('');
    setEmployee({ ...employee, status: '' });
  }, [formik.values]);

  return (
    <>
      <Dialog open={open} fullWidth maxWidth={'lg'}>
        <Tabs
          value={value}
          onChange={handleChange}
          aria-label="basic tabs example"
          style={{ padding: '10px 20px' }}
        >
          <Tab label="Thông tin cơ bản" {...a11yProps(0)} />
          <Tab label="Thông tin liên quan" {...a11yProps(1)} />
          <Tab label="Thông tin vị trí làm việc" {...a11yProps(2)} />
        </Tabs>
        <Box className="icon-close" onClick={handleCloseDialog}>
          <IconButton color="error">
            <CloseIcon />
          </IconButton>
        </Box>
        <form onSubmit={formik.handleSubmit} onError={(errors) => console.log(errors)}>
          <TabPanel value={value} index={0}>
            <EmployeeInformation formik={formik} />
          </TabPanel>
          <TabPanel value={value} index={1}>
            <RelatedInformation formik={formik} />
          </TabPanel>
          <TabPanel value={value} index={2}>
            <WorkingPosition formik={formik} />
          </TabPanel>
          <DialogActions style={{ justifyContent: 'space-between' }}>
            <div>
              <IconButton
                style={{ margin: '0 10px' }}
                onClick={() => setValue(value - 1)}
                disabled={value === 0}
              >
                <ArrowBackIosNewIcon />
              </IconButton>
              <IconButton onClick={() => setValue(value + 1)} disabled={value === 2}>
                <ArrowForwardIosIcon />
              </IconButton>
            </div>
            <div>
              <Button variant="contained" color="secondary" onClick={handleCloseDialog}>
                Hủy
              </Button>
              <Button
                variant="contained"
                color="primary"
                onClick={() => {
                  setMethod(1);
                  setEmployee({ ...employee, additionalRequestContent: '' });
                }}
                // type="submit"
                style={{ margin: '0 10px' }}
              >
                {item?.id ? 'Lưu' : 'Thêm'}
              </Button>
            </div>
          </DialogActions>
        </form>
      </Dialog>
    </>
  );
}
