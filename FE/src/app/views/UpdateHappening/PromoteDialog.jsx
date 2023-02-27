import React, { useState, useEffect } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { MenuItem } from '@mui/material';
import { sex } from 'app/constant';
import Autocomplete, { createFilterOptions } from '@mui/material/Autocomplete';
import moment from 'moment';
import Checkbox from '@mui/material/Checkbox';
import CheckBoxOutlineBlankIcon from '@mui/icons-material/CheckBoxOutlineBlank';
import CheckBoxIcon from '@mui/icons-material/CheckBox';
import Box from '@mui/material/Box';
import { getListDepartment, getListPosition, editEmployee } from './UpdateHappeningService';
import { toast } from 'react-toastify';
import Button from '@mui/material/Button';

const filterOptions = createFilterOptions({
  // matchFrom: 'start',
  stringify: (option) => option.name,
});

export default function PromoteDialog(props) {
  const { item } = props;

  const [listDepartment, setListDepartment] = useState([]);
  const [listPosition, setListPosition] = useState([]);
  const [type, setType] = useState(null);

  useEffect(() => {
    getListDepartment()
      .then((res) => {
        if (res.data.statusCode === 200) {
          setListDepartment(res.data.data);
        } else {
          toast.warning('Lỗi xác thực');
        }
      })
      .catch((err) => toast.error('Có lỗi xảy ra'));

    getListPosition()
      .then((res) => {
        if (res.data.statusCode === 200) {
          setListPosition(res.data.data);
        } else {
          toast.warning('Lỗi xác thực');
        }
      })
      .catch((err) => toast.error('Có lỗi xảy ra'));

    if (item?.status === 12) {
      setType({ name: 'Nhân viên thử việc', value: 12 });
    }
  }, []);

  useEffect(() => {
    if (item?.status === 12) {
      formik.setFieldValue('status', type?.value);
    }
  }, [type]);

  const formik = useFormik({
    initialValues: {
      code: item?.id ? item?.code : '',
      fullName: item?.id ? item?.fullName : '',
      dateOfBirth: item?.id ? item?.dateOfBirth : null,
      sex: item?.id ? item?.sex : '',
      image: item?.id ? item?.image : '',
      phone: item?.id ? item?.phone : '',
      email: item?.id ? item?.email : '',
      education: item?.id ? item?.education : '',
      major: item?.id ? item?.major : '',
      nation: item?.id ? item?.nation : '',
      religion: item?.id ? item?.religion : '',
      address: item?.id ? item?.address : '',
      numberIdentityCard: item?.id ? item?.numberIdentityCard : '',
      issuedDateIdentityCard: item?.id ? item?.issuedDateIdentityCard : null,
      placeOfGrantIdentityCard: item?.id ? item?.placeOfGrantIdentityCard : '',
      numberMedicalInsurance: item?.id ? item?.numberMedicalInsurance : '',
      issuedDateMedicalInsurance: item?.id ? item?.issuedDateMedicalInsurance : null,
      placeOfIssueMedicalInsurance: item?.id ? item?.placeOfIssueMedicalInsurance : '',
      numberSocialInsurance: item?.id ? item?.numberSocialInsurance : '',
      issuedDateSocialInsurance: item?.id ? item?.issuedDateSocialInsurance : null,
      placeOfIssueSocialInsurance: item?.id ? item?.placeOfIssueSocialInsurance : '',
      certificate: item?.id ? item?.certificate : null,
      languages: item?.id ? item?.languages : [],
      department: item?.id ? item?.department : null,
      positions: item?.id ? item?.positions : null,
      status: item?.id ? item?.status : null,
    },
    enableReinitialize: true,
    validateOnChange: false,
    validateOnBlur: false,
    validationSchema: Yup.object({
      department: Yup.object().nullable().required('Vui lòng chọn trường này'),
      positions: Yup.array().nullable().required('Vui lòng chọn trường này'),
    }),
    onSubmit: (values) => {
      values.id = item?.id;
      handleSave(values);
    },
  });
  const handleSave = (values) => {
    editEmployee(values)
      .then((res) => {
        if (res.data.statusCode === 200) {
          toast.success('Đã thao tác thành công');
        } else {
          toast.warning(res.data.message);
        }
      })
      .catch((err) => toast.error('Có lỗi xảy ra!'));
  };

  return (
    <form
      onSubmit={(event) => {
        event.preventDefault();
        formik.handleSubmit();
      }}
      onError={(errors) => console.log(errors)}
    >
      <Grid container spacing={2} style={{ marginTop: 5 }}>
        <Grid item xs={12} md={12}>
          <Autocomplete
            fullWidth
            options={listDepartment}
            getOptionLabel={(option) => option.name}
            value={formik.values?.department}
            onChange={(event, value) => formik.setFieldValue('department', value)}
            filterOptions={filterOptions}
            renderInput={(params) => (
              <TextField
                {...params}
                label="Phòng ban"
                placeholder="Phòng ban"
                error={formik.errors.department && formik.touched.department}
                helperText={formik.errors.department}
              />
            )}
          />
        </Grid>
        <Grid item xs={12} md={12}>
          <Autocomplete
            fullWidth
            options={listPosition}
            getOptionLabel={(option) => option.name}
            value={formik.values?.positions ? formik.values?.positions[0] : null}
            onChange={(event, value) => formik.setFieldValue('positions', [value])}
            filterOptions={filterOptions}
            renderInput={(params) => (
              <TextField
                {...params}
                label="Chức vụ"
                placeholder="Chức vụ"
                error={formik.errors.positions && formik.touched.positions}
                helperText={formik.errors.positions}
              />
            )}
          />
        </Grid>
        {item?.status === 12 && (
          <Grid item xs={6} md={6}>
            <Autocomplete
              fullWidth
              options={[
                { name: 'Nhân viên thử việc', value: 12 },
                { name: 'Nhân viên chính thức', value: 14 },
              ]}
              getOptionLabel={(option) => option?.name}
              value={type || null}
              onChange={(event, value) => setType(value)}
              renderInput={(params) => <TextField {...params} label="Trạng thái" />}
            />
          </Grid>
        )}
      </Grid>
      <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
        <Button
          variant="contained"
          color="primary"
          onClick={() => formik.handleSubmit()}
          style={{ margin: '10px' }}
        >
          Lưu
        </Button>
      </div>
    </form>
  );
}
