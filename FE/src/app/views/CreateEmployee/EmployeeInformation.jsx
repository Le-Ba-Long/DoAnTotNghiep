import React, { useState, useEffect } from 'react';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { MenuItem } from '@mui/material';
import { sex } from 'app/constant';

export default function EmployeeInformation(props) {
  const { formik } = props;
  return (
    <Grid container spacing={2} style={{ marginTop: 5 }}>
      <Grid container item xs={12} md={12} spacing={2}>
        <Grid item xs={3} md={3}>
          <TextField
            label="Mã nhân viên"
            variant="outlined"
            fullWidth
            name="code"
            value={formik.values?.code}
            onChange={formik.handleChange}
            error={formik.errors.code && formik.touched.code}
            helperText={formik.errors.code}
          />
        </Grid>
        <Grid item xs={3} md={3}>
          <TextField
            label="Họ và Tên"
            variant="outlined"
            fullWidth
            name="fullName"
            value={formik.values?.fullName}
            onChange={formik.handleChange}
            error={formik.errors.fullName && formik.touched.fullName}
            helperText={formik.errors.fullName}
          />
        </Grid>
        <Grid item xs={3} md={3}>
          <LocalizationProvider dateAdapter={AdapterDayjs} fullWidth>
            <DatePicker
              label="Ngày sinh"
              name="dateOfBirth"
              inputFormat="DD/MM/YYYY"
              value={formik.values?.dateOfBirth}
              onChange={(value) => {
                if (value) {
                  formik.setFieldValue('dateOfBirth', new Date(value));
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
                    error={formik.errors.dateOfBirth && formik.touched.dateOfBirth}
                    helperText={formik.errors.dateOfBirth}
                  />
                );
              }}
            />
          </LocalizationProvider>
        </Grid>
        <Grid item xs={3} md={3}>
          <TextField
            label="Giới tính"
            variant="outlined"
            select
            fullWidth
            name="sex"
            value={formik.values?.sex}
            onChange={formik.handleChange}
            error={formik.errors.sex && formik.touched.sex}
            helperText={formik.errors.sex}
          >
            {sex.map((item) => (
              <MenuItem value={item.name}>{item.name}</MenuItem>
            ))}
          </TextField>
        </Grid>
      </Grid>
      <Grid container item xs={12} md={12} spacing={2}>
        <Grid item xs={3} md={3}>
          <TextField
            label="Số điện thoại"
            variant="outlined"
            fullWidth
            name="phone"
            value={formik.values?.phone}
            onChange={formik.handleChange}
            error={formik.errors.phone && formik.touched.phone}
            helperText={formik.errors.phone}
          />
        </Grid>
        <Grid item xs={3} md={3}>
          <TextField
            label="Email"
            variant="outlined"
            fullWidth
            name="email"
            value={formik.values?.email}
            onChange={formik.handleChange}
            error={formik.errors.email && formik.touched.email}
            helperText={formik.errors.email}
          />
        </Grid>
        <Grid item xs={3} md={3}>
          <TextField
            label="Trình độ học vấn"
            variant="outlined"
            fullWidth
            name="education"
            value={formik.values?.education}
            onChange={formik.handleChange}
            error={formik.errors.education && formik.touched.education}
            helperText={formik.errors.education}
          />
        </Grid>
        <Grid item xs={3} md={3}>
          <TextField
            label="Chuyên ngành"
            variant="outlined"
            fullWidth
            name="major"
            value={formik.values?.major}
            onChange={formik.handleChange}
            error={formik.errors.major && formik.touched.major}
            helperText={formik.errors.major}
          />
        </Grid>
      </Grid>
      <Grid container item xs={12} md={12} spacing={2}>
        <Grid item xs={4} md={4}>
          <TextField
            label="Số CCCD"
            variant="outlined"
            fullWidth
            name="numberIdentityCard"
            value={formik.values?.numberIdentityCard}
            onChange={formik.handleChange}
            error={formik.errors.numberIdentityCard && formik.touched.numberIdentityCard}
            helperText={formik.errors.numberIdentityCard}
          />
        </Grid>
        <Grid item xs={4} md={4}>
          <LocalizationProvider dateAdapter={AdapterDayjs} fullWidth>
            <DatePicker
              label="Ngày cấp"
              name="issuedDateIdentityCard"
              inputFormat="DD/MM/YYYY"
              value={formik.values?.issuedDateIdentityCard || null}
              onChange={(value) => {
                if (value) {
                  formik.setFieldValue('issuedDateIdentityCard', new Date(value));
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
                    error={
                      formik.errors.issuedDateIdentityCard && formik.touched.issuedDateIdentityCard
                    }
                    helperText={formik.errors.issuedDateIdentityCard}
                  />
                );
              }}
            />
          </LocalizationProvider>
        </Grid>
        <Grid item xs={4} md={4}>
          <TextField
            label="Nơi cấp"
            variant="outlined"
            fullWidth
            name="placeOfGrantIdentityCard"
            value={formik.values?.placeOfGrantIdentityCard}
            onChange={formik.handleChange}
            error={
              formik.errors.placeOfGrantIdentityCard && formik.touched.placeOfGrantIdentityCard
            }
            helperText={formik.errors.placeOfGrantIdentityCard}
          />
        </Grid>
      </Grid>
      <Grid container item xs={12} md={12} spacing={2}>
        <Grid item xs={2} md={2}>
          <TextField
            label="Dân tộc"
            variant="outlined"
            fullWidth
            name="nation"
            value={formik.values?.nation}
            onChange={formik.handleChange}
            error={formik.errors.nation && formik.touched.nation}
            helperText={formik.errors.nation}
          />
        </Grid>
        <Grid item xs={2} md={2}>
          <TextField
            label="Tôn giáo"
            variant="outlined"
            fullWidth
            name="religion"
            value={formik.values?.religion}
            onChange={formik.handleChange}
            error={formik.errors.religion && formik.touched.religion}
            helperText={formik.errors.religion}
          />
        </Grid>
        <Grid item xs={8} md={8}>
          <TextField
            label="Địa chỉ"
            variant="outlined"
            fullWidth
            multiline
            maxRows={2}
            name="address"
            value={formik.values?.address}
            onChange={formik.handleChange}
            error={formik.errors.address && formik.touched.address}
            helperText={formik.errors.address}
          />
        </Grid>
      </Grid>
    </Grid>
  );
}
