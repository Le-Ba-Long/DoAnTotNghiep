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
import { addUser, editUser, getListRole } from './UserService';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import { ValidatorForm, TextValidator } from 'react-material-ui-form-validator';
import Autocomplete from '@mui/material/Autocomplete';
import TextField from '@mui/material/TextField';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { toast } from 'react-toastify';

const addNew = Yup.object({
  userName: Yup.string().required('Vui lòng nhập trường này'),
  fullName: Yup.string().required('Vui lòng nhập trường này'),
  email: Yup.string()
    .matches(/^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[A-Za-z]+$/, 'Đây không phải là email!')
    .required('Vui lòng nhập trường này'),
  roles: Yup.array().nullable().required('Vui lòng chọn vai trò'),
  passWord: Yup.string().required('Vui lòng nhập trường này'),
  confirm_password: Yup.string()
    .oneOf([Yup.ref('passWord')], 'Mật khẩu nhập lại không chính xác')
    .required('Vui lòng nhập lại mật khẩu'),
  // newPassword: Yup.string(),
});

const changePass = Yup.object({
  userName: Yup.string().required('Vui lòng nhập trường này'),
  fullName: Yup.string().required('Vui lòng nhập trường này'),
  email: Yup.string()
    .matches(/^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[A-Za-z]+$/, 'Đây không phải là email!')
    .required('Vui lòng nhập trường này'),
  roles: Yup.array().nullable().required('Vui lòng chọn vai trò'),
  newPassword: Yup.string().required('Vui lòng nhập lại mật khẩu'),
});

const edit = Yup.object({
  userName: Yup.string().required('Vui lòng nhập trường này'),
  fullName: Yup.string().required('Vui lòng nhập trường này'),
  email: Yup.string()
    .matches(/^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[A-Za-z]+$/, 'Đây không phải là email!')
    .required('Vui lòng nhập trường này'),
  roles: Yup.array().nullable().required('Vui lòng chọn vai trò'),
});

export default function UserDialog(props) {
  const { open, handleClose, item } = props;
  const [showPassword, setShowPassword] = React.useState(false);
  const [editPassword, setEditPassword] = React.useState(false);
  const [listRole, setListRole] = useState([]);
  const formik = useFormik({
    initialValues: {
      userName: item.id ? item?.userName : '',
      fullName: item.id ? item?.fullName : '',
      email: item.id ? item?.email : '',
      roles: item.id ? item?.roles : [],
      passWord: '',
      confirm_password: '',
      newPassword: '',
    },
    enableReinitialize: true,
    validateOnChange: false,
    validateOnBlur: false,
    validationSchema: item?.id && editPassword ? changePass : item?.id ? edit : addNew,
    onSubmit: (values) => {
      values.id = item.id;
      // values.roles = [values.roles];
      handleAdd(values);
    },
  });
  console.log(formik.values);

  const handleClickShowPassword = () => setShowPassword((show) => !show);

  useEffect(() => {
    getListRole().then((res) => setListRole(res.data));
  }, []);

  const handleAdd = (values) => {
    if (item.id) {
      editUser(values)
        .then((res) => {
          toast.success('Sửa thông tin người dùng thành công');
          handleClose();
        })
        .catch((err) => toast.error('Có lỗi xảy ra!'));
    } else {
      addUser(values)
        .then((res) => {
          if (res.data.statusCode === 200) {
            toast.success('Thêm người dùng thành công');
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
        {item.id ? 'Sửa thông tin người dùng' : 'Thêm người dùng'}
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
                  label="Tên đăng nhập"
                  variant="outlined"
                  fullWidth
                  name="userName"
                  value={formik.values.userName}
                  onChange={formik.handleChange}
                  error={formik.errors.userName && formik.touched.userName}
                  helperText={formik.errors.userName}
                />
              </Grid>
              <Grid item xs={4} md={4}>
                <TextField
                  label="Họ và tên"
                  variant="outlined"
                  fullWidth
                  name="fullName"
                  value={formik.values.fullName}
                  onChange={formik.handleChange}
                  error={formik.errors.fullName && formik.touched.fullName}
                  helperText={formik.errors.fullName}
                />
              </Grid>
              <Grid item xs={4} md={4}>
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
            </Grid>
            <Grid item xs={4} md={4}>
              <Autocomplete
                options={listRole}
                getOptionLabel={(option) => option.roleName}
                value={formik.values?.roles ? formik.values?.roles[0] : null}
                onChange={(event, newValue) => formik.setFieldValue('roles', [newValue])}
                componentsProps={{ paper: { elevation: 8 } }}
                renderInput={(params) => (
                  <TextField
                    {...params}
                    variant="outlined"
                    fullWidth
                    label="Vai trò"
                    error={formik.errors.roles && formik.touched.roles}
                    helperText={formik.errors.roles}
                  />
                )}
              />
            </Grid>

            {item.id && editPassword ? (
              <Grid item xs={4} md={4}>
                <TextField
                  label="Mật khẩu mới"
                  variant="outlined"
                  fullWidth
                  type={showPassword ? 'text' : 'password'}
                  name="newPassword"
                  value={formik?.values.newPassword}
                  onChange={formik.handleChange}
                  InputProps={{
                    endAdornment: (
                      <IconButton onClick={handleClickShowPassword}>
                        {showPassword ? <VisibilityOff /> : <Visibility />}
                      </IconButton>
                    ),
                  }}
                  error={formik.errors.passWord && formik.touched.passWord}
                  helperText={formik.errors.passWord}
                />
              </Grid>
            ) : (
              !item.id && (
                <>
                  <Grid item xs={4} md={4}>
                    <TextField
                      label="Mật khẩu"
                      variant="outlined"
                      fullWidth
                      type={showPassword ? 'text' : 'password'}
                      name="passWord"
                      value={formik?.values.passWord}
                      onChange={formik.handleChange}
                      InputProps={{
                        endAdornment: (
                          <IconButton onClick={handleClickShowPassword}>
                            {showPassword ? <VisibilityOff /> : <Visibility />}
                          </IconButton>
                        ),
                      }}
                      error={formik.errors.passWord && formik.touched.passWord}
                      helperText={formik.errors.passWord}
                    />
                  </Grid>
                  <Grid item xs={4} md={4}>
                    <TextField
                      label="Nhập lại mật khẩu"
                      variant="outlined"
                      fullWidth
                      type={showPassword ? 'text' : 'password'}
                      name="confirm_password"
                      value={formik?.values.confirm_password}
                      onChange={formik.handleChange}
                      InputProps={{
                        endAdornment: (
                          <IconButton onClick={handleClickShowPassword}>
                            {showPassword ? <VisibilityOff /> : <Visibility />}
                          </IconButton>
                        ),
                      }}
                      error={formik.errors.confirm_password && formik.touched.confirm_password}
                      helperText={formik.errors.confirm_password}
                    />
                  </Grid>
                </>
              )
            )}
          </Grid>
        </DialogContent>
        <DialogActions>
          <>
            <Button variant="contained" color="secondary" onClick={handleClose}>
              Hủy
            </Button>
            <Button type="submit" variant="contained" color="primary">
              {item.id ? 'Lưu' : 'Thêm'}
            </Button>
            {item.id && (
              <Button
                variant="contained"
                color="secondary"
                onClick={() => setEditPassword(!editPassword)}
              >
                Đổi mật khẩu
              </Button>
            )}
          </>
        </DialogActions>
      </form>
    </Dialog>
  );
}
