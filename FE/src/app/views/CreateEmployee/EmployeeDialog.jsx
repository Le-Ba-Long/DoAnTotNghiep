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
        .matches(/^MaNV[0-9]{4}$/, 'M?? b???ng c???p ch??a ????ng format VD:(MaNV9999)')
        .required('Vui l??ng nh???p tr?????ng n??y'),
      fullName: Yup.string().required('Vui l??ng nh???p tr?????ng n??y'),
      dateOfBirth: Yup.date()
        .nullable()
        .typeError('Sai ?????nh d???ng ng??y!')
        .required('Vui l??ng nh???p tr?????ng n??y'),
      sex: Yup.string().required('Vui l??ng nh???p tr?????ng n??y!'),
      email: Yup.string()
        .matches(/^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[A-Za-z]+$/, '????y kh??ng ph???i l?? email!')
        .required('Vui l??ng nh???p Email!'),
      phone: Yup.string()
        .matches(/(84|0[3|5|7|8|9])+([0-9]{8})\b/, 'S??? ??i???n tho???i kh??ng h???p l???')
        .required('Vui l??ng nh???p s??? ??i???n tho???i!'),
      education: Yup.string().required('Vui l??ng nh???p tr?????ng n??y'),
      major: Yup.string().required('Vui l??ng nh???p tr?????ng n??y'),
      nation: Yup.string().required('Vui l??ng nh???p tr?????ng n??y'),
      religion: Yup.string().required('Vui l??ng nh???p tr?????ng n??y'),
      address: Yup.string().required('Vui l??ng nh???p tr?????ng n??y'),
      numberIdentityCard: Yup.number()
        .typeError('S??? CCCD ph???i l?? s???!')
        .required('Vui l??ng nh???p tr?????ng n??y'),
      issuedDateIdentityCard: Yup.date()
        .nullable()
        .typeError('Sai ?????nh d???ng ng??y!')
        .required('Vui l??ng nh???p tr?????ng n??y'),
      placeOfGrantIdentityCard: Yup.string().required('Vui l??ng nh???p tr?????ng n??y'),
      numberMedicalInsurance: Yup.number().nullable().typeError('S??? b???o hi???m y t??? ph???i l?? s???!'),
      // .required('Vui l??ng nh???p tr?????ng n??y'),
      issuedDateMedicalInsurance: Yup.date().nullable().typeError('Sai ?????nh d???ng ng??y!'),
      // .required('Vui l??ng nh???p tr?????ng n??y'),
      placeOfIssueMedicalInsurance: Yup.string().nullable(),
      // .required('Vui l??ng nh???p tr?????ng n??y'),
      numberSocialInsurance: Yup.number().nullable().typeError('S??? b???o hi???m x?? h???i ph???i l?? s???!'),
      // .required('Vui l??ng nh???p tr?????ng n??y'),
      issuedDateSocialInsurance: Yup.date().nullable().typeError('Sai ?????nh d???ng ng??y!'),
      // .required('Vui l??ng nh???p tr?????ng n??y'),
      placeOfIssueSocialInsurance: Yup.string().nullable(),
      // .required('Vui l??ng nh???p tr?????ng n??y'),
      certificate: Yup.object().nullable().required('Vui l??ng ch???n tr?????ng n??y'),
      languages: Yup.array().nullable().required('Vui l??ng ch???n tr?????ng n??y'),
      department: Yup.object().nullable().required('Vui l??ng ch???n tr?????ng n??y'),
      positions: Yup.array().nullable().required('Vui l??ng ch???n tr?????ng n??y'),
      titleRecruit: Yup.string().nullable().required('Vui l??ng nh???p tr?????ng n??y'),
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
      toast.warning('Vui l??ng nh???p ????? tr?????ng');
    }
  }, [formik.errors]);

  useEffect(() => {
    if (employee.id) {
      if (employee.status === 1) {
        editEmployee(employee)
          .then((res) => {
            if (res.data.statusCode === 200) {
              toast.success('S???a h??? s?? th??nh c??ng');
              setEmployee({});
              handleClose();
            } else {
              toast.warning(res.data.message);
            }
          })
          .catch((err) => toast.error('C?? l???i x???y ra!'));
        // } else if (employee.status === 10) {
        //   editEmployee(employee)
        //     .then((res) => {
        //       if (res.data.statusCode === 200) {
        //         toast.success('Ch???nh s???a th??nh c??ng');
        //         setEmployee({});
        //         handleClose();
        //       } else {
        //         toast.warning(res.data.message);
        //       }
        //     })
        //     .catch((err) => toast.error('C?? l???i x???y ra!'));
      }
    } else {
      if (employee.status === 1) {
        addEmployee(employee)
          .then((res) => {
            if (res.data.statusCode === 200) {
              toast.success('Th??m m???i h??? s?? th??nh c??ng');
              setEmployee({});
              handleClose();
            } else {
              toast.warning(res.data.message);
            }
          })
          .catch((err) => toast.error('C?? l???i x???y ra!'));
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
          <Tab label="Th??ng tin c?? b???n" {...a11yProps(0)} />
          <Tab label="Th??ng tin li??n quan" {...a11yProps(1)} />
          <Tab label="Th??ng tin v??? tr?? l??m vi???c" {...a11yProps(2)} />
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
                H???y
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
                {item?.id ? 'L??u' : 'Th??m'}
              </Button>
            </div>
          </DialogActions>
        </form>
      </Dialog>
    </>
  );
}
