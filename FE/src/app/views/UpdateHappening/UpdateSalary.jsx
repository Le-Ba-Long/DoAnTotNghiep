import React, { useState, useEffect } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import { getContract, editContract } from './UpdateHappeningService';
import { toast } from 'react-toastify';
import Button from '@mui/material/Button';
import InputAdornment from '@mui/material/InputAdornment';

export default function UpdateSalary(props) {
  const { item } = props;

  const [contract, setContract] = useState({});

  useEffect(() => {
    getContract(item?.id)
      .then((res) => {
        if (res.data.statusCode === 200) {
          setContract(res.data.data);
        } else {
          toast.warning('Lỗi xác thực');
        }
      })
      .catch((err) => toast.error('Có lỗi xảy ra'));
  }, []);

  const formik = useFormik({
    initialValues: {
      id: contract?.employee ? contract.id : '',
      code: contract?.employee ? contract.code : '',
      nameLeader: contract?.employee ? contract.nameLeader : '',
      postionLeader: contract?.employee ? contract.postionLeader : '',
      signingDate: contract?.employee ? contract.signingDate : null,
      contractEffect: contract?.employee ? contract.contractEffect : null,
      basicSalary: contract?.employee ? contract.basicSalary : '',
      hourlyRate: contract?.employee ? contract.hourlyRate : '',
      coefficientSalary: contract?.employee ? contract.coefficientSalary : '',
      status: contract?.employee ? contract.status : '',
      employee: item,
    },
    enableReinitialize: true,
    validateOnChange: false,
    validateOnBlur: false,
    validationSchema: Yup.object({
      coefficientSalary: Yup.number()
        .typeError('Số lương phải là số!')
        .required('Vui lòng nhập trường này'),
      basicSalary: Yup.number()
        .typeError('Số lương phải là số!')
        .required('Vui lòng nhập trường này'),
      hourlyRate: Yup.number()
        .typeError('Số lương phải là số!')
        .required('Vui lòng nhập trường này'),
    }),
    onSubmit: (values) => {
      handleSave(values);
    },
  });
  const handleSave = (values) => {
    editContract(values)
      .then((res) => {
        if (res.data.statusCode === 200) {
          toast.success('Cập nhật lương thành công');
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
        <Grid item xs={4} md={4}>
          <TextField
            label="Hệ số lương"
            variant="outlined"
            fullWidth
            name="coefficientSalary"
            value={formik.values?.coefficientSalary}
            onChange={formik.handleChange}
            error={formik.errors.coefficientSalary && formik.touched.coefficientSalary}
            helperText={formik.errors.coefficientSalary}
          />
        </Grid>
        <Grid item xs={4} md={4}>
          <TextField
            label="Lương cơ bản"
            variant="outlined"
            fullWidth
            name="basicSalary"
            value={
              formik.values?.basicSalary
                ? formik.values?.basicSalary
                    .toString()
                    .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',')
                : ''
            }
            onChange={(event) => {
              formik.setFieldValue('basicSalary', event.target.value.replace(/,/g, ''));
            }}
            InputProps={{
              endAdornment: <InputAdornment position="end">VND</InputAdornment>,
            }}
            error={formik.errors.basicSalary && formik.touched.basicSalary}
            helperText={formik.errors.basicSalary}
          />
        </Grid>
        <Grid item xs={4} md={4}>
          <TextField
            label="Số tiền tính cho 1 giờ làm thêm"
            variant="outlined"
            fullWidth
            name="hourlyRate"
            value={
              formik.values?.hourlyRate
                ? formik.values?.hourlyRate
                    .toString()
                    .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',')
                : ''
            }
            onChange={(event) => {
              formik.setFieldValue('hourlyRate', event.target.value.replace(/,/g, ''));
            }}
            InputProps={{
              endAdornment: <InputAdornment position="end">VND</InputAdornment>,
            }}
            error={formik.errors.hourlyRate && formik.touched.hourlyRate}
            helperText={formik.errors.hourlyRate}
          />
        </Grid>
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
