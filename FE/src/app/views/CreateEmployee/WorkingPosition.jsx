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
import { getListDepartment, getListPosition } from './EmployeeService';
import { toast } from 'react-toastify';

const icon = <CheckBoxOutlineBlankIcon fontSize="small" />;
const checkedIcon = <CheckBoxIcon fontSize="small" />;
const filterOptions = createFilterOptions({
  matchFrom: 'start',
  stringify: (option) => option.name,
});

export default function WorkingPosition(props) {
  const { formik } = props;
  const [listDepartment, setListDepartment] = useState([]);
  const [listPosition, setListPosition] = useState([]);
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
  }, []);
  return (
    <Grid container spacing={2} style={{ marginTop: 5, minHeight: '20vh' }}>
      <Grid item xs={6} md={6}>
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
      <Grid item xs={6} md={6}>
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
    </Grid>
  );
}
