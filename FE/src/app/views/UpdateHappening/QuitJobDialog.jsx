import React, { useState } from 'react';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import { Grid } from '@material-ui/core';
import { TextField } from '@mui/material';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import Box from '@mui/material/Box';
import moment from 'moment';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { toast } from 'react-toastify';
import { editEmployee } from './UpdateHappeningService';
import './Dialog.scss';

export default function QuitJobDialog(props) {
  const { open, handleClose, employee, handleCloseFinishDialog } = props;
  const formik = useFormik({
    initialValues: {
      id: employee?.id ? employee?.id : '',
      code: employee?.id ? employee?.code : '',
      fullName: employee?.id ? employee?.fullName : '',
      dateOfBirth: employee?.id ? employee?.dateOfBirth : null,
      sex: employee?.id ? employee?.sex : '',
      image: employee?.id ? employee?.image : '',
      phone: employee?.id ? employee?.phone : '',
      email: employee?.id ? employee?.email : '',
      education: employee?.id ? employee?.education : '',
      major: employee?.id ? employee?.major : '',
      nation: employee?.id ? employee?.nation : '',
      religion: employee?.id ? employee?.religion : '',
      address: employee?.id ? employee?.address : '',
      numberIdentityCard: employee?.id ? employee?.numberIdentityCard : '',
      issuedDateIdentityCard: employee?.id ? employee?.issuedDateIdentityCard : null,
      placeOfGrantIdentityCard: employee?.id ? employee?.placeOfGrantIdentityCard : '',
      numberMedicalInsurance: employee?.id ? employee?.numberMedicalInsurance : '',
      issuedDateMedicalInsurance: employee?.id ? employee?.issuedDateMedicalInsurance : null,
      placeOfIssueMedicalInsurance: employee?.id ? employee?.placeOfIssueMedicalInsurance : '',
      numberSocialInsurance: employee?.id ? employee?.numberSocialInsurance : '',
      issuedDateSocialInsurance: employee?.id ? employee?.issuedDateSocialInsurance : null,
      placeOfIssueSocialInsurance: employee?.id ? employee?.placeOfIssueSocialInsurance : '',
      certificate: employee?.id ? employee?.certificate : null,
      languages: employee?.id ? employee?.languages : [],
      department: employee?.id ? employee?.department : null,
      positions: employee?.id ? employee?.positions : null,
      status: employee?.id ? employee?.status : null,
      quitJobDate: employee?.id ? employee?.quitJobDate : '',
      refusalReason: employee?.id ? employee?.refusalReason : '',
    },
    enableReinitialize: true,
    validateOnChange: false,
    validateOnBlur: false,
    validationSchema: Yup.object({
      refusalReason: Yup.string().nullable().required('Vui lòng nhập lý do!'),
      quitJobDate: Yup.date()
        .nullable()
        .typeError('Sai định dạng ngày!')
        .required('Vui lòng nhập ngày xin nghỉ!'),
    }),
    onSubmit: (values) => {
      values.status = 2;
      handleSubmit(values);
    },
  });

  const handleSubmit = (values) => {
    editEmployee(values)
      .then((res) => {
        if (res.data.statusCode === 200) {
          toast.success('Trình lãnh đạo đơn nghỉ việc thành công');
          handleClose();
        } else {
          toast.warning(res.data.message);
        }
      })
      .catch((err) => toast.error('Có lỗi xảy ra!'));
  };

  return (
    <Dialog open={open} maxWidth="md" fullWidth>
      <DialogTitle>
        <Box className="icon-close" onClick={handleCloseFinishDialog}>
          <IconButton color="error">
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>
      <form onSubmit={formik.handleSubmit}>
        <DialogContent>
          <Grid
            container
            style={{
              fontFamily: '"Times New Roman", Times, serif',
              fontSize: 18,
              padding: 10,
            }}
          >
            <Grid container>
              <Grid container item sm={12} xs={12} justifyContent="center">
                <span className="font-20 uppercase fw-600">Cộng hòa xã hội Việt Nam</span>
              </Grid>
              <Grid container item sm={12} xs={12} justifyContent="center">
                <span className="font-18 fw-600">Độc lập - Tự do - Hạnh phúc</span>
              </Grid>
              <Grid
                container
                item
                sm={12}
                xs={12}
                justifyContent="center"
                style={{ marginBottom: 20 }}
              >
                <span className="font-18 fw-600">-------------------------------------</span>
              </Grid>
              <Grid
                style={{ marginBottom: 30 }}
                container
                item
                sm={12}
                xs={12}
                justifyContent="center"
              >
                <span className="font-20 fw-600">ĐƠN XIN NGHỈ VIỆC</span>
              </Grid>
              <Grid
                container
                item
                sm={12}
                xs={12}
                justifyContent="center"
                style={{ marginBottom: 30 }}
              >
                <Grid item sm={5} xs={5}>
                  <span>Kính gửi: Ban giám đốc công ty OceanTech</span>
                </Grid>
              </Grid>
              <Grid
                container
                item
                sm={12}
                xs={12}
                className="pd-60"
                justifyContent="flex-start"
                style={{ marginBottom: 10 }}
              >
                <Grid item className="mr-10" style={{ lineHeight: '1.5' }}>
                  <span>Tôi tên là:</span>
                </Grid>
                <Grid item sm={10.5} xs={10.5}>
                  <TextField
                    className=" title-1   "
                    fullWidth
                    variant="standard"
                    value={employee?.fullName}
                    InputProps={{
                      readOnly: true,
                    }}
                  />
                </Grid>
              </Grid>
              <Grid
                container
                item
                sm={12}
                xs={12}
                className="pd-60"
                justifyContent="flex-start"
                style={{ marginBottom: 10 }}
              >
                <Grid item className="mr-10" style={{ lineHeight: '1.5' }}>
                  <span>Hiện đang công tác tại vị trí:</span>
                </Grid>
                <Grid item sm={5} xs={5}>
                  <TextField
                    className=" title-1   "
                    fullWidth
                    variant="standard"
                    value={employee.positions[0]?.name}
                    InputProps={{
                      readOnly: true,
                    }}
                  />
                </Grid>
              </Grid>
              <Grid
                container
                item
                sm={12}
                xs={12}
                className="pd-60"
                justifyContent="flex-start"
                style={{ marginBottom: 10 }}
              >
                <Grid item className="mr-10" style={{ lineHeight: '1.5' }}>
                  <span>Tôi xin được phép nghỉ làm từ ngày:</span>
                </Grid>
                <Grid item sm={3} xs={3}>
                  <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DatePicker
                      name="quitJobDate"
                      inputFormat="DD/MM/YYYY"
                      value={formik.values.quitJobDate || null}
                      onChange={(value) => {
                        if (value) {
                          formik.setFieldValue('quitJobDate', new Date(value));
                        }
                      }}
                      renderInput={(params) => {
                        return (
                          <TextField
                            {...params}
                            format="DD/MM/YYYY"
                            type="date"
                            fullWidth
                            className=" title-1   "
                            variant="standard"
                            size="small"
                            error={formik.errors.quitJobDate && formik.touched.quitJobDate}
                            helperText={formik.errors.quitJobDate}
                          />
                        );
                      }}
                    />
                  </LocalizationProvider>
                </Grid>
              </Grid>
              <Grid
                container
                item
                sm={12}
                xs={12}
                className="pd-60"
                justifyContent="flex-start"
                style={{ marginBottom: 10 }}
              >
                <Grid item sm={12} xs={12}>
                  <span>Tôi làm đơn này đề nghị ban giám đốc cho tôi xin nghỉ việc vì lí do:</span>
                </Grid>
                <Grid item sm={12} xs={12}>
                  <TextField
                    className=" title-1 dotted"
                    type="text"
                    fullWidth
                    multiline
                    name="refusalReason"
                    variant="standard"
                    value={formik.values.refusalReason}
                    onChange={formik.handleChange}
                    error={formik.errors.refusalReason && formik.touched.refusalReason}
                    helperText={formik.errors.refusalReason}
                    InputProps={{
                      disableUnderline: true,
                    }}
                  />
                </Grid>
                <Grid item sm={12} xs={12}>
                  <span>
                    Trong khi chờ đợi sự chấp thuật của Ban Giám đốc Công ty, tôi sẽ tiếp tục làm
                    việc nghiêm túc và tiến hành bàn giao công việc cũng như tài sản cho người quản
                    lý trực tiếp của tôi
                  </span>
                </Grid>
                <Grid item sm={12} xs={12}>
                  <span>Tôi xin chân thành cảm ơn</span>
                </Grid>
              </Grid>
              <Grid
                container
                item
                sm={12}
                xs={12}
                className="pd-60"
                justifyContent="flex-end"
                style={{ marginBottom: 10 }}
              >
                <Grid item sm={4} xs={4}>
                  <span>
                    Hà nội, {moment(Date.now()).format('DD')} ngày {moment(Date.now()).format('MM')}{' '}
                    năm {moment(Date.now()).format('YYYY')}
                  </span>
                </Grid>
              </Grid>
            </Grid>
            <Grid
              container
              item
              sm={12}
              xs={12}
              className="pd-60"
              justifyContent="flex-end"
              style={{ marginBottom: 100 }}
            >
              <Grid item sm={3} xs={3}>
                <span style={{ fontWeight: 'bold' }}>Người làm đơn</span>
              </Grid>
            </Grid>
            <Grid
              container
              item
              sm={12}
              xs={12}
              className="pd-60"
              justifyContent="flex-end"
              style={{ marginBottom: 30 }}
            >
              <Grid item sm={3} xs={3}>
                <span style={{ fontWeight: 'bold', textDecoration: 'uppercase' }}>
                  {employee.fullName}
                </span>
              </Grid>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            color="primary"
            onClick={() => {
              formik.handleSubmit();
            }}
          >
            Trình lãnh đạo
          </Button>
          <Button variant="contained" color="secondary" onClick={handleCloseFinishDialog}>
            Hủy
          </Button>
        </DialogActions>
      </form>
    </Dialog>
  );
}
