import React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import CloseIcon from '@mui/icons-material/Close';
import Grid from '@mui/material/Grid';
import { TextField } from '@mui/material';
import { checkStatus } from 'app/constant';

export default function RecruitView(props) {
  const { open, handleClose, item } = props;
  return (
    <Dialog open={open} fullWidth maxWidth={'md'}>
      <DialogTitle style={{ marginBlockEnd: 0, padding: '16px 24px 0' }}>
        Thông tin kế hoạch
        <Box className="icon-close" onClick={handleClose}>
          <IconButton color="error">
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>
      <DialogContent style={{ padding: '0 20px' }}>
        <Grid container spacing={2}>
          <Grid container item xs={12} md={12} justifyContent="center">
            <h2>{item?.titleRecruit}</h2>
          </Grid>
          <Grid container item xs={3} md={3} spacing={1}>
            <Grid item className="fw-600">
              Mã kế hoạch:
            </Grid>
            <Grid item xs={6} md={6}>
              {item?.code}
            </Grid>
          </Grid>
          <Grid container item xs={3} md={3} spacing={1}>
            <Grid item className="fw-600">
              Số lượng tuyển dụng:
            </Grid>
            <Grid item xs={1} md={1}>
              {item?.quantity}
            </Grid>
          </Grid>
          <Grid container item xs={6} md={6} spacing={1}>
            <Grid item className="fw-600">
              Trạng thái kế hoạch:
            </Grid>
            <Grid item xs={5} md={5}>
              {checkStatus(item?.status).message}
            </Grid>
          </Grid>
          {item?.feedback && (
            <Grid container item spacing={1}>
              <Grid item className="fw-600">
                Yêu cầu chỉnh sửa:
              </Grid>
              <Grid item xs={10}>
                {item?.feedback}
              </Grid>
            </Grid>
          )}
          <Grid container item spacing={1}>
            <Grid item xs={12} md={12}>
              <span className="fz-18 fw-600">Mô tả công việc</span>
            </Grid>
            <Grid item xs={12} md={12}>
              <TextField
                fullWidth
                multiline
                variant="standard"
                value={item?.description}
                InputProps={{
                  readOnly: true,
                  disableUnderline: true,
                }}
              />
            </Grid>
          </Grid>
          <Grid container item spacing={1}>
            <Grid item xs={12} md={12}>
              <span className="fz-18 fw-600">Yêu cầu ứng viên</span>
            </Grid>
            <Grid item xs={12} md={12}>
              <TextField
                fullWidth
                multiline
                variant="standard"
                value={item?.requireRecruit}
                InputProps={{
                  readOnly: true,
                  disableUnderline: true,
                }}
              />
            </Grid>
          </Grid>
          <Grid container item spacing={1}>
            <Grid item xs={12} md={12}>
              <span className="fz-18 fw-600">Quyền lợi</span>
            </Grid>
            <Grid item xs={12} md={12}>
              <TextField
                fullWidth
                multiline
                variant="standard"
                value={item?.benefitsReceived}
                InputProps={{
                  readOnly: true,
                  disableUnderline: true,
                }}
              />
            </Grid>
          </Grid>
          <Grid container item spacing={1}>
            <Grid item xs={12} md={12}>
              <span className="fz-18 fw-600">Các kênh tuyển dụng</span>
            </Grid>
            <Grid item xs={12} md={12}>
              <TextField
                fullWidth
                multiline
                variant="standard"
                value={item?.recruitmentChannel}
                InputProps={{
                  readOnly: true,
                  disableUnderline: true,
                }}
              />
            </Grid>
          </Grid>
        </Grid>
      </DialogContent>
      <DialogActions>
        <Button variant="contained" color="secondary" onClick={handleClose}>
          Đóng
        </Button>
      </DialogActions>
    </Dialog>
  );
}
