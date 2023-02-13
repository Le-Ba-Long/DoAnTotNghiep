import React, { useEffect, useRef } from 'react';
import { Button } from '@mui/material';
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
import { approveRecruit } from './ApproveService';
import { toast } from 'react-toastify';
import ConfirmationDialog from '../../components/ConfirmationDialog';
import RequestAdditional from './RequestAdditional';

export default function RecruitView(props) {
  const { open, handleClose, item, setItem } = props;
  const [shouldOpenConfirmDialog, setShouldOpenConfirmDialog] = React.useState(false);
  const [shouldOpenRefuseDialog, setShouldOpenRefuseDialog] = React.useState(false);
  const [shouldOpenFeedbackDialog, setShouldOpenFeedbackDialog] = React.useState(false);

  const firstRender = useRef(false);

  useEffect(() => {
    if (firstRender.current) {
      if (item.status === 4 || item.status === 6 || item.status === 10) {
        handleAprrove();
      }
    } else {
      firstRender.current = true;
    }
  }, [item.status]);

  const handleAprrove = () => {
    if (item.status === 4) {
      approveRecruit(item)
        .then((res) => {
          if (res.data.statusCode === 200) {
            toast.success('Phê duyệt kế hoạch thành công');
            handleClose();
          } else {
            toast.warning(res.data.message);
          }
        })
        .catch((err) => toast.error('Có lỗi xảy ra'));
    } else if (item.status === 10) {
      approveRecruit(item)
        .then((res) => {
          if (res.data.statusCode === 200) {
            toast.success('Yêu cầu bổ sung kế hoạch thành công');
            handleClose();
          } else {
            toast.warning(res.data.message);
          }
        })
        .catch((err) => toast.error('Có lỗi xảy ra'));
    } else if (item.status === 6) {
      approveRecruit(item)
        .then((res) => {
          if (res.data.statusCode === 200) {
            toast.success('Đã từ chối kế hoạch');
            handleClose();
          } else {
            toast.warning(res.data.message);
          }
        })
        .catch((err) => toast.error('Có lỗi xảy ra'));
    }
  };
  return (
    <>
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
            <Grid container item xs={4} md={4}>
              <Grid item xs={4} md={4} className="fw-600">
                Mã kế hoạch:
              </Grid>
              <Grid item xs={6} md={6}>
                {item?.code}
              </Grid>
            </Grid>
            <Grid container item xs={4} md={4}>
              <Grid item xs={6} md={6} className="fw-600">
                Số lượng tuyển dụng:
              </Grid>
              <Grid item xs={5} md={5}>
                {item?.quantity}
              </Grid>
            </Grid>
            <Grid container item xs={4} md={4}>
              <Grid item xs={6} md={6} className="fw-600">
                Trạng thái kế hoạch:
              </Grid>
              <Grid item xs={5} md={5}>
                {checkStatus(item?.status).message}
              </Grid>
            </Grid>
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
          <>
            <Button
              variant="contained"
              color="primary"
              onClick={() => setShouldOpenConfirmDialog(true)}
            >
              Phê duyệt
            </Button>
            <Button
              variant="contained"
              color="primary"
              onClick={() => setShouldOpenFeedbackDialog(true)}
            >
              Yêu cầu bổ sung
            </Button>
            <Button
              variant="contained"
              color="error"
              onClick={() => setShouldOpenRefuseDialog(true)}
            >
              Từ chối
            </Button>
            <Button variant="contained" color="secondary" onClick={handleClose}>
              Đóng
            </Button>
          </>
        </DialogActions>
      </Dialog>
      {shouldOpenConfirmDialog && (
        <ConfirmationDialog
          title="Xác nhận"
          text="Bạn có muốn phê duyệt kế hoạch này?"
          open={shouldOpenConfirmDialog}
          onConfirmDialogClose={() => setShouldOpenConfirmDialog(false)}
          onYesClick={() => {
            setItem({ ...item, status: 4 });
          }}
          Yes="Đồng ý"
          No="Hủy"
        />
      )}
      {shouldOpenRefuseDialog && (
        <ConfirmationDialog
          title="Xác nhận"
          text="Bạn có muốn từ chối phê hoạch kế hoạch này?"
          open={shouldOpenRefuseDialog}
          onConfirmDialogClose={() => setShouldOpenRefuseDialog(false)}
          onYesClick={() => {
            setItem({ ...item, status: 6 });
          }}
          Yes="Đồng ý"
          No="Hủy"
        />
      )}
      {shouldOpenFeedbackDialog && (
        <RequestAdditional
          open={shouldOpenFeedbackDialog}
          handleClose={() => setShouldOpenFeedbackDialog(false)}
          onSelectYes={() => {
            setItem({ ...item, status: 10 });
          }}
          item={item}
          setItem={setItem}
        />
      )}
    </>
  );
}
