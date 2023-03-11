import React, { useState, useEffect } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import { getContract, editContract } from './UpdateHappeningService';
import { toast } from 'react-toastify';
import Button from '@mui/material/Button';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';

export default function ContractExtensionDialog(props) {
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
      //   employee: item,
    },
    enableReinitialize: true,
    validateOnChange: false,
    validateOnBlur: false,
    validationSchema: Yup.object({
      contractEffect: Yup.date()
        .nullable()
        .typeError('Sai định dạng ngày!')
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
          toast.success('Gia hạn hợp đồng thành công!');
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
      <Grid container spacing={1} style={{ marginTop: 5 }}>
        <Grid item xs={3} md={3}>
          <LocalizationProvider dateAdapter={AdapterDayjs} fullWidth>
            <DatePicker
              name="signingDate"
              inputFormat="DD/MM/YYYY"
              value={formik.values?.signingDate || null}
              readOnly
              renderInput={(params) => {
                return (
                  <TextField
                    {...params}
                    fullWidth
                    label="Ngày ký hợp hồng"
                    format="DD/MM/YYYY"
                    type="date"
                  />
                );
              }}
            />
          </LocalizationProvider>
        </Grid>
        <Grid item xs={0.5} md={0.5}></Grid>
        <Grid item xs={3} md={3}>
          <LocalizationProvider dateAdapter={AdapterDayjs} fullWidth>
            <DatePicker
              name="contractEffect"
              inputFormat="DD/MM/YYYY"
              value={formik.values?.contractEffect || null}
              fullWidth
              onChange={(value) => {
                if (value) {
                  formik.setFieldValue('contractEffect', new Date(value));
                } else {
                  formik.setFieldValue('contractEffect', null);
                }
              }}
              renderInput={(params) => {
                return (
                  <TextField
                    {...params}
                    fullWidth
                    label="Ngày hết hạn hợp đồng"
                    format="DD/MM/YYYY"
                    type="date"
                    error={formik.errors.contractEffect && formik.touched.contractEffect}
                    helperText={formik.errors.contractEffect}
                  />
                );
              }}
            />
          </LocalizationProvider>
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
