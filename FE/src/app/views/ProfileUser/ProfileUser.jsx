import React, { useEffect, useState } from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { getProfileUser, editUser } from './ProfileUserService';
import { toast } from 'react-toastify';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { Breadcrumb } from 'app/components';
import { Box, Button } from '@mui/material';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import './ProfileUser.scss';
import { IconButton } from '@mui/material';
import { uploadImage } from 'app/constant';
import ChangePassword from './ChangePassword';

export default function ProfileUser() {
  const [profile, setProfile] = useState({});
  const [shouldOpenDialog, setShouldOpenDialog] = useState(false);
  useEffect(() => {
    let userName = JSON.parse(localStorage.getItem('account')).username;
    getProfileUser(userName)
      .then((res) => {
        if (res.data.statusCode === 200) {
          setProfile(res.data?.data);
        } else {
          toast.warning('Lỗi xác thực!');
        }
      })
      .catch((err) => {
        toast.error('Có lỗi xảy ra!');
      });
  }, []);
  const formik = useFormik({
    initialValues: {
      fullName: profile?.id ? profile?.fullName : '',
      sex: profile?.id ? profile?.sex : '',
      dateOfBirth: profile?.id ? profile?.dateOfBirth : null,
      email: profile?.id ? profile?.email : '',
      phone: profile?.id ? profile?.phone : '',
      address: profile?.id ? profile?.address : '',
      avatar: profile?.id ? profile?.avatar : '',
      roles: profile?.id ? profile?.roles : null,
      userName: profile?.id ? profile?.userName : '',
    },
    enableReinitialize: true,
    validateOnChange: false,
    validateOnBlur: false,
    validationSchema: Yup.object({
      fullName: Yup.string(),
      sex: Yup.string(),
      dateOfBirth: Yup.date().nullable().typeError('Sai định dạng ngày!'),
      email: Yup.string().matches(
        /^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[A-Za-z]+$/,
        'Đây không phải là email!'
      ),
      phone: Yup.string().matches(/(84|0[3|5|7|8|9])+([0-9]{8})\b/, 'Số điện thoại không hợp lệ'),
      address: Yup.string(),
    }),
    onSubmit: (values) => {
      values.id = profile?.id;
      handleSave(values);
    },
  });

  const handleSave = (values) => {
    editUser(values)
      .then((res) => {
        if (res.data.statusCode === 200) {
          toast.success('Lưu thông tin cá nhân thành công');
        } else {
          toast.warning(res.data.message);
        }
      })
      .catch((err) => toast.error('Có lỗi xảy ra!'));
  };

  return (
    <Box style={{ margin: 20 }}>
      <Breadcrumb
        routeSegments={[{ name: 'Người dùng', path: '/user' }, { name: 'Thông tin cá nhân' }]}
      />
      <form onSubmit={formik.handleSubmit} onError={(errors) => console.log(errors)}>
        <Grid container style={{ marginTop: 100 }} justifyContent="space-between">
          <Card sx={{ width: '60%', padding: '50px 20px 20px', height: '445px' }}>
            <Grid container item xs={12} md={12} spacing={2} justifyContent="center">
              <Grid item xs={5} md={5} style={{ marginBottom: 50 }}>
                <TextField
                  label="Họ và tên"
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
                <TextField
                  label="Giới tính"
                  variant="outlined"
                  fullWidth
                  name="sex"
                  value={formik.values?.sex}
                  onChange={formik.handleChange}
                  error={formik.errors.sex && formik.touched.sex}
                  helperText={formik.errors.sex}
                />
              </Grid>
              <Grid item xs={4} md={4}>
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
              <Grid item xs={6} md={6} style={{ marginBottom: 50 }}>
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
              <Grid item xs={6} md={6}>
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
              <Grid item xs={12} md={12}>
                <TextField
                  label="Địa chỉ"
                  variant="outlined"
                  fullWidth
                  name="address"
                  value={formik.values?.address}
                  onChange={formik.handleChange}
                  error={formik.errors.address && formik.touched.address}
                  helperText={formik.errors.address}
                />
              </Grid>
            </Grid>
          </Card>
          <Card sx={{ width: '35%', padding: '10px 10px 0' }}>
            <CardContent style={{ display: 'flex', justifyContent: 'center' }}>
              {profile?.avatar ? (
                <img
                  src={formik.values?.avatar || profile?.avatar}
                  alt=""
                  style={{ width: '80%', height: '80%' }}
                />
              ) : (
                <img
                  src="https://static.vecteezy.com/system/resources/previews/008/442/086/original/illustration-of-human-icon-user-symbol-icon-modern-design-on-blank-background-free-vector.jpg"
                  alt=""
                  style={{ width: '80%', height: '80%' }}
                />
              )}
            </CardContent>
            <CardActions style={{ justifyContent: 'center' }} className="icon">
              <IconButton>
                <label for="file-upload" class="custom-file-upload">
                  <AddCircleIcon />
                </label>
                <input
                  style={{ display: 'none' }}
                  type="file"
                  id="file-upload"
                  name="filename"
                  onChange={(event) => {
                    uploadImage(event.currentTarget.files[0]).then((res) => {
                      formik.setFieldValue('avatar', res?.data?.fileDownloadUri);
                    });
                  }}
                ></input>
              </IconButton>
            </CardActions>
          </Card>
          <Grid item xs={7.4} md={7.4} style={{ display: 'flex', justifyContent: 'flex-end' }}>
            <div>
              <Button
                variant="contained"
                color="secondary"
                size="medium"
                style={{ margin: '20px', padding: '10px 30px' }}
                onClick={() => setShouldOpenDialog(true)}
              >
                Đổi mật khẩu
              </Button>
              <Button
                variant="contained"
                color="primary"
                size="medium"
                type="submit"
                style={{ margin: '20px', padding: '10px 30px' }}
              >
                Lưu
              </Button>
            </div>
          </Grid>
        </Grid>
      </form>
      {shouldOpenDialog && (
        <ChangePassword
          open={shouldOpenDialog}
          handleClose={() => setShouldOpenDialog(false)}
          item={profile}
        />
      )}
    </Box>
  );
}
