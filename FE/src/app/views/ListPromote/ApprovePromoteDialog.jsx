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
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { toast } from 'react-toastify';
import './ApprovePromote.scss';
import InputAdornment from '@mui/material/InputAdornment';
import { editPromote } from './ListPromoteService';

export default function ApprovePromoteDialog(props) {
  const { open, handleClose, item, readOnly, employee, handleCloseDialog } = props;
  const [method, setMethod] = useState('');

  const formik = useFormik({
    initialValues: {
      decisionNumber: item?.id ? item?.decisionNumber : '',
      day: item?.id ? item?.day : '',
      month: item?.id ? item?.month : '',
      year: item?.id ? item?.year : '',
      type: item?.id ? item?.type : '',
      reason: item?.id ? item?.reason : '',
      staffName: item?.id ? item?.staffName : employee?.fullName || '',
      status: item?.id ? item?.status : '',
      employeeDto: item?.id ? item?.employeeDto : employee || {},
      basicSalary: item?.id ? item?.basicSalary : employee?.contract?.basicSalary || '',
      coefficientSalary: item?.id
        ? item?.coefficientSalary
        : employee?.contract?.coefficientSalary || '',
      hourlyRate: item?.id ? item?.hourlyRate : employee?.contract?.hourlyRate || '',
    },
    enableReinitialize: true,
    validateOnChange: false,
    validateOnBlur: false,
    validationSchema: Yup.object({
      decisionNumber: Yup.string()
        .matches(/^[0-9]{4}\/QĐ-[0-9]{4}$/, 'Số quyết định không dúng định dạng VD: 1234/QĐ-4567')
        .required('Vui lòng nhập trường này'),
      day: Yup.number().required('Vui lòng nhập trường này'),
      month: Yup.number().required('Vui lòng nhập trường này'),
      year: Yup.number().required('Vui lòng nhập trường này'),
      basicSalary: Yup.number().required('Vui lòng nhập trường này'),
      coefficientSalary: Yup.number().required('Vui lòng nhập trường này'),
      hourlyRate: Yup.number().required('Vui lòng nhập trường này'),
    }),
    onSubmit: (values) => {
      values.id = item?.id;
      values.status = method;
      values.basicSalary = Number(values.basicSalary);
      values.hourlyRate = Number(values.hourlyRate);
      values.type = 3;
      handleAdd(values);
    },
  });

  const handleAdd = (values) => {
    if (values?.id) {
      if (values?.status === 5) {
        editPromote(values)
          .then((res) => {
            if (res.data.statusCode === 200) {
              toast.success('Đã thi hành quyết định thành công');
              handleClose();
            } else {
              toast.warning(res.data.message);
            }
          })
          .catch((err) => {
            toast.error('Có lỗi xảy ra!');
          });
      }
    } else {
      toast.warning('Có lỗi xảy ra!');
    }
  };

  const handlePrint = () => {
    var oldstr = document.body.innerHTML;
    document.body.innerHTML = document.getElementById('promote').innerHTML;
    window.print();
    window.location.reload();
    document.body.innerHTML = oldstr;
  };

  return (
    <Dialog open={open} fullWidth maxWidth={'md'}>
      <DialogTitle style={{ marginBlockEnd: 0, padding: '16px 24px 0' }}>
        <Box className="icon-close" onClick={handleCloseDialog}>
          <IconButton color="error">
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>
      <form onSubmit={formik.handleSubmit} onError={(errors) => console.log(errors)}>
        <DialogContent style={{ padding: '0 20px' }} id="promote">
          <Grid
            container
            spacing={2}
            style={{ marginTop: 5, fontFamily: '"Times New Roman", Times, serif', fontSize: 18 }}
          >
            <Grid container item xs={12} md={12} spacing={2} style={{ marginBottom: 10 }}>
              <Grid container item xs={4} md={4} justifyContent="center">
                <Grid
                  item
                  xs={12}
                  md={12}
                  style={{
                    textAlign: 'center',
                    textTransform: 'uppercase',
                    fontWeight: 'bold',
                    fontSize: 18,
                  }}
                >
                  CÔNG TY OCEANTECH
                </Grid>
                <Grid container item xs={12} md={12} justifyContent="center">
                  <Grid item style={{ lineHeight: '2', marginRight: 5 }}>
                    Số:{' '}
                  </Grid>
                  <Grid item xs={4.3} md={4.3}>
                    <TextField
                      variant="standard"
                      name="decisionNumber"
                      fullWidth
                      value={formik.values?.decisionNumber}
                      onChange={formik.handleChange}
                      InputProps={{
                        readOnly: readOnly,
                      }}
                      error={formik.errors.decisionNumber && formik.touched.decisionNumber}
                      helperText={formik.errors.decisionNumber}
                    />
                  </Grid>
                </Grid>
              </Grid>
              <Grid container item xs={8} md={8} justifyContent="center">
                <Grid
                  item
                  xs={12}
                  md={12}
                  style={{
                    textAlign: 'center',
                    textTransform: 'uppercase',
                    fontWeight: 'bold',
                    fontSize: 18,
                  }}
                >
                  CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM
                </Grid>
                <Grid
                  item
                  xs={12}
                  md={12}
                  style={{
                    textAlign: 'center',
                    fontWeight: 'bold',
                    fontSize: 18,
                  }}
                >
                  Độc lập - Tự do - Hạnh phúc
                </Grid>
              </Grid>
            </Grid>
            <Grid
              container
              item
              xs={12}
              md={12}
              justifyContent="flex-end"
              style={{ marginBottom: 20, marginRight: 10 }}
            >
              <Grid item style={{ lineHeight: '1.5', marginRight: 5 }}>
                Hà Nội, ngày{' '}
              </Grid>
              <Grid item style={{ width: 30 }}>
                <TextField
                  variant="standard"
                  type="number"
                  name="day"
                  value={formik.values?.day}
                  onChange={formik.handleChange}
                  InputProps={{
                    readOnly: readOnly,
                  }}
                  error={formik.errors.day && formik.touched.day}
                  helperText={formik.errors.day}
                />
              </Grid>
              <Grid item style={{ lineHeight: '1.5', marginRight: 5 }}>
                tháng
              </Grid>
              <Grid item style={{ width: 30 }}>
                <TextField
                  variant="standard"
                  type="number"
                  name="month"
                  value={formik.values?.month}
                  onChange={formik.handleChange}
                  InputProps={{
                    readOnly: readOnly,
                  }}
                  error={formik.errors.month && formik.touched.month}
                  helperText={formik.errors.month}
                />
              </Grid>
              <Grid item style={{ lineHeight: '1.5', marginRight: 5 }}>
                năm
              </Grid>
              <Grid item style={{ width: 50 }}>
                <TextField
                  variant="standard"
                  type="number"
                  name="year"
                  value={formik.values?.year}
                  onChange={formik.handleChange}
                  InputProps={{
                    readOnly: readOnly,
                  }}
                  error={formik.errors.year && formik.touched.year}
                  helperText={formik.errors.year}
                />
              </Grid>
            </Grid>
            <Grid
              item
              xs={12}
              md={12}
              spacing={2}
              style={{ textAlign: 'center', fontWeight: 'bold', fontSize: 25 }}
            >
              QUYẾT ĐỊNH
            </Grid>
            <Grid container item xs={12} md={12} spacing={1} style={{ marginBottom: 20 }}>
              <Grid item xs={4.5} md={4.5}></Grid>
              <Grid item style={{ lineHeight: '2' }}>
                Tăng lương cá nhân năm {formik.values?.year}
              </Grid>
            </Grid>
            <Grid
              container
              item
              xs={12}
              md={12}
              spacing={1}
              style={{
                marginBottom: 20,
                textAlign: 'center',
                textTransform: 'uppercase',
                fontWeight: 'bold',
                fontSize: 18,
              }}
            >
              <Grid item xs={12} md={12}>
                CÔNG TY OCEANTECH
              </Grid>
            </Grid>
            <Grid
              container
              item
              xs={12}
              md={12}
              spacing={1}
              style={{
                marginBottom: 20,
              }}
            >
              <Grid item xs={2} md={2}></Grid>
              <Grid container item xs={9} md={9}>
                <Grid item xs={12} md={12}>
                  - Căn cứ Bộ luật lao động;
                </Grid>
                <Grid item xs={12} md={12}>
                  - Căn cứ vào Điều lệ hoạt động của Công ty OCEANTECH;
                </Grid>
                <Grid item xs={12} md={12}>
                  - Xét đến cống hiến của nhân viên cho công ty
                </Grid>
                <Grid item xs={12} md={12}>
                  - Để động viên khuyến khích CBNV toàn Công ty
                </Grid>
                <Grid item xs={12} md={12}>
                  - Xét đề nghị của Trưởng Phòng Hành chính Nhân sự;
                </Grid>
              </Grid>
              <Grid item xs={1} md={1}></Grid>
            </Grid>
            <Grid
              container
              item
              xs={12}
              md={12}
              spacing={1}
              style={{
                marginBottom: 5,
                textAlign: 'center',
                textTransform: 'uppercase',
                fontWeight: 'bold',
                fontSize: 18,
              }}
            >
              <Grid item xs={12} md={12}>
                Quyết định
              </Grid>
            </Grid>
            <Grid
              container
              item
              xs={12}
              md={12}
              spacing={1}
              style={{
                marginBottom: 5,
              }}
            >
              <Grid item xs={1} md={1}></Grid>
              <Grid container item xs={10} md={10}>
                <Grid item style={{ lineHeight: '1.5', marginRight: 5 }}>
                  {' '}
                  <span>1: </span> Tăng lương: Ông/bà:
                </Grid>
                <Grid item xs={4}>
                  <TextField
                    variant="standard"
                    name="staffName"
                    fullWidth
                    value={formik.values?.staffName}
                    onChange={formik.handleChange}
                    InputProps={{
                      readOnly: readOnly,
                    }}
                    error={formik.errors.staffName && formik.touched.staffName}
                    helperText={formik.errors.staffName}
                  />
                </Grid>
              </Grid>
              <Grid item xs={1} md={1}></Grid>
              <Grid item xs={1} md={1}></Grid>
              <Grid container item xs={10} md={10}>
                <Grid item style={{ lineHeight: '2', marginRight: 5 }}>
                  {' '}
                  <span>2: </span> Lý do tăng lương :
                </Grid>
                <Grid item xs={9}>
                  <TextField
                    variant="standard"
                    name="reason"
                    fullWidth
                    className="dotted"
                    multiline
                    value={formik.values?.reason}
                    onChange={formik.handleChange}
                    InputProps={{
                      readOnly: readOnly,
                      disableUnderline: true,
                    }}
                    error={formik.errors.reason && formik.touched.reason}
                    helperText={formik.errors.reason}
                  />
                </Grid>
              </Grid>
              <Grid item xs={1} md={1}></Grid>
              <Grid item xs={1} md={1}></Grid>
              <Grid container item xs={10} md={10}>
                <Grid item style={{ lineHeight: '1.5', marginRight: 5 }}>
                  <span>3: </span> Mức lương cơ bản :
                </Grid>
                <Grid item xs={3}>
                  <TextField
                    variant="standard"
                    name="basicSalary"
                    fullWidth
                    value={formik.values?.basicSalary
                      .toString()
                      .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',')}
                    onChange={(event) => {
                      formik.setFieldValue('basicSalary', event.target.value.replace(/,/g, ''));
                    }}
                    InputProps={{
                      readOnly: readOnly,
                      endAdornment: <InputAdornment position="end">VND</InputAdornment>,
                    }}
                    error={formik.errors.basicSalary && formik.touched.basicSalary}
                    helperText={formik.errors.basicSalary}
                  />
                </Grid>
              </Grid>
              <Grid item xs={1} md={1}></Grid>
              <Grid item xs={1} md={1}></Grid>
              <Grid container item xs={10} md={10}>
                <Grid item style={{ lineHeight: '1.5', marginRight: 5 }}>
                  <span>4: </span> Mức lương làm ngoài giờ / 1 giờ :
                </Grid>
                <Grid item xs={3}>
                  <TextField
                    variant="standard"
                    name="hourlyRate"
                    fullWidth
                    value={formik.values?.hourlyRate
                      .toString()
                      .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',')}
                    onChange={(event) => {
                      formik.setFieldValue('hourlyRate', event.target.value.replace(/,/g, ''));
                    }}
                    InputProps={{
                      readOnly: readOnly,
                      endAdornment: <InputAdornment position="end">VND</InputAdornment>,
                    }}
                    error={formik.errors.hourlyRate && formik.touched.hourlyRate}
                    helperText={formik.errors.hourlyRate}
                  />
                </Grid>
              </Grid>
              <Grid item xs={1} md={1}></Grid>
              <Grid item xs={1} md={1}></Grid>
              <Grid container item xs={10} md={10}>
                <Grid item style={{ lineHeight: '1.5', marginRight: 5 }}>
                  <span>5: </span> Mức hệ số lương:
                </Grid>
                <Grid item xs={3}>
                  <TextField
                    type="number"
                    variant="standard"
                    name="coefficientSalary"
                    fullWidth
                    value={formik.values?.coefficientSalary}
                    onChange={(event) => {
                      formik.setFieldValue('coefficientSalary', event.target.value);
                    }}
                    InputProps={{
                      readOnly: readOnly,
                    }}
                    error={formik.errors.coefficientSalary && formik.touched.coefficientSalary}
                    helperText={formik.errors.coefficientSalary}
                  />
                </Grid>
              </Grid>
              <Grid item xs={1} md={1}></Grid>
              <Grid item xs={1} md={1}></Grid>
              <Grid container item xs={10} md={10}>
                <Grid item style={{ lineHeight: '2', marginRight: 5 }}>
                  <span>6: </span> Quyết định có hiểu lực từ ngày ký, Phòng Kế toán, Phòng Hành
                  chính Nhân sự và các Phòng/Ban có liên quan chịu trách nhiệm thi hành quyết định
                  này.
                </Grid>
              </Grid>
            </Grid>
            <Grid
              container
              item
              xs={12}
              md={12}
              spacing={1}
              style={{
                marginBottom: 100,
              }}
            >
              <Grid item xs={6}></Grid>
              <Grid
                item
                xs={6}
                md={6}
                style={{ textAlign: 'center', textTransform: 'uppercase', fontWeight: 'bold' }}
              >
                GIÁM ĐỐC
              </Grid>
              <Grid item xs={6}></Grid>
              <Grid item xs={6} md={6} style={{ textAlign: 'center', fontStyle: 'italic' }}>
                (Ký tên, đóng dấu)
              </Grid>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button variant="contained" color="secondary" onClick={handleCloseDialog}>
            Hủy
          </Button>
          {item?.status !== 5 && (
            <Button type="submit" variant="contained" color="primary" onClick={() => setMethod(5)}>
              Thi hành
            </Button>
          )}
          <Button variant="contained" color="primary" onClick={handlePrint}>
            In
          </Button>
        </DialogActions>
      </form>
    </Dialog>
  );
}
