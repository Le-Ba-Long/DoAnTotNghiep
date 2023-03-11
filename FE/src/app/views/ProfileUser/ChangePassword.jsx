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
import { changePass } from './ProfileUserService';
import { ValidatorForm, TextValidator } from 'react-material-ui-form-validator';
import { toast } from 'react-toastify';
import useAuth from 'app/hooks/useAuth';

export default function ChangePassword(props) {
  const { open, handleClose, item } = props;
  const { logout } = useAuth();
  const [pass, setPass] = useState({});

  useEffect(() => {
    setPass({ ...pass, userName: item?.userName, id: item?.id });
  }, []);

  const handleSave = () => {
    changePass(pass)
      .then((res) => {
        if (res.data.statusCode === 200) {
          toast.success('Đổi mật khẩu thành công');
          handleClose();
          logout();
        } else {
          toast.warning(res.data.message);
        }
      })
      .catch((err) => toast.error('Có lỗi xảy ra!'));
  };

  return (
    <Dialog open={open} fullWidth maxWidth={'md'}>
      <DialogTitle style={{ marginBlockEnd: 0, padding: '16px 24px 0' }}>
        Đổi mật khẩu
        <Box className="icon-close" onClick={handleClose}>
          <IconButton color="error">
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>
      <ValidatorForm onSubmit={() => handleSave()} onError={(errors) => console.log(errors)}>
        <DialogContent style={{ padding: '20px' }}>
          <Grid container spacing={2} style={{ marginTop: 5 }}>
            <Grid container item xs={12} md={12} spacing={2}>
              <Grid item xs={6} md={6}>
                <TextValidator
                  label="Mật khẩu cũ"
                  variant="outlined"
                  fullWidth
                  value={pass?.passWord}
                  onChange={(event) => setPass({ ...pass, passWord: event.target.value })}
                  validators={['required']}
                  errorMessages={['Vui lòng nhập trường này']}
                />
              </Grid>
              <Grid item xs={6} md={6}>
                <TextValidator
                  label="Mật khẩu mới"
                  variant="outlined"
                  fullWidth
                  value={pass?.newPassword}
                  onChange={(event) => setPass({ ...pass, newPassword: event.target.value })}
                  validators={['required']}
                  errorMessages={['Vui lòng nhập trường này']}
                />
              </Grid>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <>
            <Button variant="contained" color="secondary" onClick={handleClose}>
              Hủy
            </Button>
            <Button type="submit" variant="contained" color="primary">
              Đổi
            </Button>
          </>
        </DialogActions>
      </ValidatorForm>
    </Dialog>
  );
}
