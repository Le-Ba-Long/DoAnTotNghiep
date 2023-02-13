import React, { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import CloseIcon from '@mui/icons-material/Close';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { addCertificate, editCertificate } from './CertificateService';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { toast } from 'react-toastify';

export default function CertificateDialog(props) {
  const { open, handleClose, item, readOnly } = props;
  const [certificate, setCertificate] = useState({});

  const formik = useFormik({
    initialValues: {
      code: item.id ? item?.code : '',
      name: item.id ? item?.name : '',
      majors: item.id ? item?.majors : '',
      grantedBy: item.id ? item?.grantedBy : '',
      issuedDate: item.id ? item?.issuedDate : null,
    },
    enableReinitialize: true,
    validateOnChange: false,
    validateOnBlur: false,
    validationSchema: Yup.object({
      code: Yup.string()
        .matches(/^MaBC[0-9]{4}$/, 'Mã bằng cấp chưa đúng format VD:(MaBC9999)')
        .required('Vui lòng nhập trường này'),
      name: Yup.string()
        .matches(/^[0-9a-zA-Z].{5,100}$/, 'Tên bằng cấp phải lớn hơn 5 kí tự và nhỏ hơn 100 kí tự')
        .required('Vui lòng nhập trường này'),
      majors: Yup.string().required('Vui lòng nhập trường này'),
      grantedBy: Yup.string().required('Vui lòng nhập trường này'),
      issuedDate: Yup.date()
        .nullable()
        .typeError('Sai định dạng ngày!')
        .required('Vui lòng nhập trường này'),
    }),
    onSubmit: (values) => {
      handleAdd(values);
    },
  });

  useEffect(() => {
    setCertificate(item);
  }, []);

  const handleAdd = (values) => {
    if (values.id) {
      editCertificate(values)
        .then((res) => {
          toast.success('Sửa thông tin bằng cấp thành công');
          handleClose();
        })
        .catch((err) => toast.error('Có lỗi xảy ra!'));
    } else {
      addCertificate(values)
        .then((res) => {
          if (res.data.statusCode === 200) {
            toast.success('Thêm bằng cấp thành công');
            handleClose();
          } else {
            toast.warning(res.data.message);
          }
        })
        .catch((err) => toast.error('Có lỗi xảy ra!'));
    }
  };

  return (
    <Dialog open={open} fullWidth maxWidth={'md'}>
      <DialogTitle style={{ marginBlockEnd: 0, padding: '16px 24px 0' }}>
        {certificate.id && readOnly
          ? 'Thông tin bằng cấp'
          : certificate.id
          ? 'Sửa bằng cấp'
          : 'Thêm bằng cấp'}
        <Box className="icon-close" onClick={handleClose}>
          <IconButton color="error">
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>
      <form onSubmit={formik.handleSubmit} onError={(errors) => console.log(errors)}>
        <DialogContent style={{ padding: '0 20px' }}>
          <Grid container spacing={2} style={{ marginTop: 5 }}>
            <Grid container item xs={12} md={12} spacing={2}>
              <Grid item xs={4} md={4}>
                <TextField
                  label="Mã bằng cấp"
                  variant="outlined"
                  fullWidth
                  InputProps={{
                    readOnly: readOnly,
                  }}
                  name="code"
                  value={formik.values?.code}
                  onChange={formik.handleChange}
                  error={formik.errors.code && formik.touched.code}
                  helperText={formik.errors.code}
                />
              </Grid>
              <Grid item xs={4} md={4}>
                <TextField
                  label="Tên bằng cấp"
                  variant="outlined"
                  fullWidth
                  InputProps={{
                    readOnly: readOnly,
                  }}
                  name="name"
                  value={formik.values?.name}
                  onChange={formik.handleChange}
                  error={formik.errors.name && formik.touched.name}
                  helperText={formik.errors.name}
                />
              </Grid>
              <Grid item xs={4} md={4}>
                <TextField
                  label="Chuyên ngành"
                  variant="outlined"
                  fullWidth
                  InputProps={{
                    readOnly: readOnly,
                  }}
                  name="majors"
                  value={formik.values?.majors}
                  onChange={formik.handleChange}
                  error={formik.errors.majors && formik.touched.majors}
                  helperText={formik.errors.majors}
                />
              </Grid>
            </Grid>
            <Grid container item xs={12} md={12} spacing={2}>
              <Grid item xs={6} md={6}>
                <TextField
                  label="Được cấp bởi"
                  variant="outlined"
                  fullWidth
                  InputProps={{
                    readOnly: readOnly,
                  }}
                  name="grantedBy"
                  value={formik.values?.grantedBy}
                  onChange={formik.handleChange}
                  error={formik.errors.majors && formik.touched.majors}
                  helperText={formik.errors.majors}
                />
              </Grid>
              <Grid item xs={6} md={6}>
                <LocalizationProvider dateAdapter={AdapterDayjs} fullWidth>
                  <DatePicker
                    label="Ngày cấp"
                    name="issuedDate"
                    inputFormat="DD/MM/YYYY"
                    value={formik.values?.issuedDate}
                    onChange={(value) => {
                      if (value) {
                        formik.setFieldValue('issuedDate', new Date(value));
                      }
                    }}
                    renderInput={(params) => {
                      return (
                        <TextField
                          {...params}
                          format="DD/MM/YYYY"
                          type="date"
                          fullWidth
                          variant="outlined"
                          error={formik.errors.issuedDate && formik.touched.issuedDate}
                          helperText={formik.errors.issuedDate}
                        />
                      );
                    }}
                  />
                </LocalizationProvider>
              </Grid>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          {readOnly ? (
            <Button variant="contained" color="secondary" onClick={handleClose}>
              Đóng
            </Button>
          ) : (
            <>
              <Button variant="contained" color="secondary" onClick={handleClose}>
                Hủy
              </Button>
              <Button type="submit" variant="contained" color="primary">
                {certificate.id ? 'Lưu' : 'Thêm'}
              </Button>
            </>
          )}
        </DialogActions>
      </form>
    </Dialog>
  );
}
