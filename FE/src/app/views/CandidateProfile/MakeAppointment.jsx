import React, { useState, useEffect, useRef } from 'react';
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
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { useFormik } from 'formik';
import * as Yup from 'yup';

export default function MakeAppointment(props) {
  const { open, handleDialogClose, candidate, setCandidate, handleMakeApointment } = props;

  const firstRender = useRef(false);
  const formik = useFormik({
    initialValues: {
      interviewer: candidate.id ? candidate?.interviewer : '',
      interviewDate: candidate.id ? candidate?.interviewDate : null,
    },
    enableReinitialize: true,
    validateOnChange: false,
    validateOnBlur: false,
    validationSchema: Yup.object({
      interviewer: Yup.string()
        .matches(
          /^[0-9a-zA-Z].{5,100}$/,
          'Họ tên người hẹn phải lớn hơn 5 kí tự và nhỏ hơn 100 kí tự'
        )
        .nullable()
        .required('Vui lòng nhập trường này'),
      interviewDate: Yup.date()
        .nullable()
        .typeError('Sai định dạng ngày!')
        .required('Vui lòng nhập trường này'),
    }),
    onSubmit: (values) => {
      setCandidate({
        ...candidate,
        interviewDate: values.interviewDate,
        interviewer: values.interviewer,
        status: 18,
      });
    },
  });

  useEffect(() => {
    if (firstRender.current) {
      if (candidate.status === 18) {
        handleMakeApointment();
      }
    } else {
      firstRender.current = true;
    }
  }, [candidate]);

  return (
    <Dialog open={open} fullWidth maxWidth={'md'}>
      <DialogTitle style={{ marginBlockEnd: 0, padding: '16px 24px 0' }}>
        Hẹn lịch phỏng vấn
        <Box className="icon-close" onClick={handleDialogClose}>
          <IconButton color="error">
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>
      <form onSubmit={formik.handleSubmit} onError={(errors) => console.log(errors)}>
        <DialogContent style={{ padding: '0 20px' }}>
          <Grid container spacing={2} style={{ marginTop: 5 }}>
            <Grid container item xs={12} md={12} spacing={2}>
              <Grid item xs={6} md={6}>
                <TextField
                  label="Họ và tên người hẹn"
                  variant="outlined"
                  fullWidth
                  name="interviewer"
                  value={formik.values?.interviewer}
                  onChange={formik.handleChange}
                  error={formik.errors.interviewer && formik.touched.interviewer}
                  helperText={formik.errors.interviewer}
                />
              </Grid>
              <Grid item xs={6} md={6}>
                <LocalizationProvider dateAdapter={AdapterDayjs} fullWidth>
                  <DateTimePicker
                    label="Ngày hẹn"
                    name="interviewDate"
                    inputFormat={'DD/MM/YYYY hh:mm A'}
                    value={formik.values?.interviewDate || null}
                    onChange={(value) => {
                      if (value) {
                        formik.setFieldValue('interviewDate', new Date(value));
                      }
                    }}
                    renderInput={(params) => {
                      return (
                        <TextField
                          {...params}
                          format={'DD/MM/YYYY hh:mm A'}
                          type="date"
                          fullWidth
                          variant="outlined"
                          error={formik.errors.interviewDate && formik.touched.interviewDate}
                          helperText={formik.errors.interviewDate}
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
          <Button variant="contained" color="secondary" onClick={handleDialogClose}>
            Hủy
          </Button>
          <Button type="submit" variant="contained" color="primary">
            Thêm lịch hẹn
          </Button>
        </DialogActions>
      </form>
    </Dialog>
  );
}
