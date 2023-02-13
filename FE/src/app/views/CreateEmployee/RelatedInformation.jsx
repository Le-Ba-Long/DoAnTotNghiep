import React, { useState, useEffect } from 'react';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import Autocomplete, { createFilterOptions } from '@mui/material/Autocomplete';
import Box from '@mui/material/Box';
import moment from 'moment';
import Checkbox from '@mui/material/Checkbox';
import CheckBoxOutlineBlankIcon from '@mui/icons-material/CheckBoxOutlineBlank';
import CheckBoxIcon from '@mui/icons-material/CheckBox';
import { getListCertificate, getListLanguage } from './EmployeeService';
import { toast } from 'react-toastify';

const icon = <CheckBoxOutlineBlankIcon fontSize="small" />;
const checkedIcon = <CheckBoxIcon fontSize="small" />;
const filterOptions = createFilterOptions({
  // matchFrom: 'start',
  stringify: (option) => option.name,
});

export default function RelatedInformation(props) {
  const { formik } = props;
  const [listCertificate, setListCertificate] = useState([]);
  const [listLanguage, setListLanguage] = useState([]);
  useEffect(() => {
    getListCertificate()
      .then((res) => {
        if (res.data.statusCode === 200) {
          setListCertificate(res.data.data);
        } else {
          toast.warning('Lỗi xác thực');
        }
      })
      .catch((err) => toast.error('Có lỗi xảy ra'));

    getListLanguage()
      .then((res) => {
        if (res.data.statusCode === 200) {
          setListLanguage(res.data.data);
        } else {
          toast.warning('Lỗi xác thực');
        }
      })
      .catch((err) => toast.error('Có lỗi xảy ra'));
  }, []);

  return (
    <Grid container spacing={2} style={{ marginTop: 5 }}>
      <Grid container item xs={12} md={12} spacing={2}>
        <Grid item xs={4} md={4}>
          <TextField
            label="Số bảo hiểm y tế"
            variant="outlined"
            fullWidth
            name="numberMedicalInsurance"
            value={formik.values?.numberMedicalInsurance}
            onChange={formik.handleChange}
            error={formik.errors.numberMedicalInsurance && formik.touched.numberMedicalInsurance}
            helperText={formik.errors.numberMedicalInsurance}
          />
        </Grid>
        <Grid item xs={4} md={4}>
          <LocalizationProvider dateAdapter={AdapterDayjs} fullWidth>
            <DatePicker
              label="Ngày cấp bảo hiểm y tế"
              name="issuedDateMedicalInsurance"
              inputFormat="DD/MM/YYYY"
              value={formik.values?.issuedDateMedicalInsurance || null}
              onChange={(value) => {
                if (value) {
                  formik.setFieldValue('issuedDateMedicalInsurance', new Date(value));
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
                      formik.errors.issuedDateMedicalInsurance &&
                      formik.touched.issuedDateMedicalInsurance
                    }
                    helperText={formik.errors.issuedDateMedicalInsurance}
                  />
                );
              }}
            />
          </LocalizationProvider>
        </Grid>
        <Grid item xs={4} md={4}>
          <TextField
            label="Nơi cấp bảo hiểm y tế"
            variant="outlined"
            fullWidth
            name="placeOfIssueMedicalInsurance"
            value={formik.values?.placeOfIssueMedicalInsurance}
            onChange={formik.handleChange}
            error={
              formik.errors.placeOfIssueMedicalInsurance &&
              formik.touched.placeOfIssueMedicalInsurance
            }
            helperText={formik.errors.placeOfIssueMedicalInsurance}
          />
        </Grid>
      </Grid>
      <Grid container item xs={12} md={12} spacing={2}>
        <Grid item xs={4} md={4}>
          <TextField
            label="Số bảo hiểm xã hội"
            variant="outlined"
            fullWidth
            name="numberSocialInsurance"
            value={formik.values?.numberSocialInsurance}
            onChange={formik.handleChange}
            error={formik.errors.numberSocialInsurance && formik.touched.numberSocialInsurance}
            helperText={formik.errors.numberSocialInsurance}
          />
        </Grid>
        <Grid item xs={4} md={4}>
          <LocalizationProvider dateAdapter={AdapterDayjs} fullWidth>
            <DatePicker
              label="Ngày cấp bảo hiểm xã hội"
              name="issuedDateSocialInsurance"
              inputFormat="DD/MM/YYYY"
              value={formik.values?.issuedDateSocialInsurance || null}
              onChange={(value) => {
                if (value) {
                  formik.setFieldValue('issuedDateSocialInsurance', new Date(value));
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
                      formik.errors.issuedDateSocialInsurance &&
                      formik.touched.issuedDateSocialInsurance
                    }
                    helperText={formik.errors.issuedDateSocialInsurance}
                  />
                );
              }}
            />
          </LocalizationProvider>
        </Grid>
        <Grid item xs={4} md={4}>
          <TextField
            label="Nơi cấp bảo hiểm xã hội"
            variant="outlined"
            fullWidth
            name="placeOfIssueSocialInsurance"
            value={formik.values?.placeOfIssueSocialInsurance}
            onChange={formik.handleChange}
            error={
              formik.errors.placeOfIssueSocialInsurance &&
              formik.touched.placeOfIssueSocialInsurance
            }
            helperText={formik.errors.placeOfIssueSocialInsurance}
          />
        </Grid>
      </Grid>
      <Grid item xs={12} md={12}>
        <Autocomplete
          fullWidth
          freeSolo
          options={listCertificate}
          getOptionLabel={(option) => option.name}
          value={formik.values?.certificate}
          onChange={(event, value) => formik.setFieldValue('certificate', value)}
          filterOptions={filterOptions}
          renderOption={(props, option, { selected }) => (
            <Box component="li" {...props}>
              <Checkbox
                icon={icon}
                checkedIcon={checkedIcon}
                style={{ marginRight: 8 }}
                checked={selected}
              />
              <span style={{ width: '10%' }}>{option.code}</span>{' '}
              <span style={{ width: '15%' }}>{option.name}</span>{' '}
              <span style={{ width: '25%' }}>{option.majors}</span>{' '}
              <span style={{ width: '10%' }}>{moment(option.issuedDate).format('DD/MM/YYYY')}</span>{' '}
              <span style={{ width: '25%' }}>{option.grantedBy}</span>
            </Box>
          )}
          renderInput={(params) => (
            <TextField
              {...params}
              label="Bằng cấp"
              placeholder="Bằng cấp"
              error={formik.errors.certificate && formik.touched.certificate}
              helperText={formik.errors.certificate}
            />
          )}
        />
      </Grid>
      <Grid item xs={12} md={12}>
        <Autocomplete
          multiple
          fullWidth
          options={listLanguage}
          getOptionLabel={(option) => option.name}
          value={formik.values?.languages}
          onChange={(event, value) => formik.setFieldValue('languages', value)}
          filterOptions={filterOptions}
          renderOption={(props, option, { selected }) => (
            <Box component="li" {...props}>
              <Checkbox
                icon={icon}
                checkedIcon={checkedIcon}
                style={{ marginRight: 8 }}
                checked={selected}
              />
              <span style={{ width: '10%' }}>{option.code}</span>{' '}
              <span style={{ width: '15%' }}>{option.name}</span>{' '}
              <span style={{ width: '50%' }}>{option.description}</span>
            </Box>
          )}
          renderInput={(params) => (
            <TextField
              {...params}
              label="Chứng chỉ"
              placeholder="Chứng chỉ"
              error={formik.errors.languages && formik.touched.languages}
              helperText={formik.errors.languages}
            />
          )}
        />
      </Grid>
    </Grid>
  );
}
