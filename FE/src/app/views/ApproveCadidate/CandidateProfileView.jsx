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
import moment from 'moment';
import ConfirmationDialog from '../../components/ConfirmationDialog';
import RefuseDialog from './RefuseDialog';
import { editCandidate } from './ApproveCandidateService';
import { toast } from 'react-toastify';

export default function CandidateProfileView(props) {
  const { open, handleClose, item } = props;

  const [candidate, setCandidate] = useState({});
  const [shouldOpenConfirmDialog, setShouldOpenConfirmDialog] = useState(false);
  const [shouldOpenRefuseDialog, setShouldOpenRefuseDialog] = useState(false);

  useEffect(() => {
    setCandidate(item);
  }, []);

  useEffect(() => {
    if (candidate?.status === 8) {
      editCandidate(candidate)
        .then((res) => {
          if (res.data.statusCode === 200) {
            toast.success('Đã phê duyệt hồ sơ ứng viên này');
            handleClose();
          } else {
            toast.warning('Lỗi xác thực');
          }
        })
        .catch((err) => toast.error('Có lỗi xảy ra'));
    } else if (candidate?.status === 9) {
      editCandidate(candidate)
        .then((res) => {
          if (res.data.statusCode === 200) {
            toast.success('Thành công');
            handleClose();
          } else {
            toast.warning('Lỗi xác thực');
          }
        })
        .catch((err) => toast.error('Có lỗi xảy ra'));
    }
  }, [candidate?.status]);

  return (
    <>
      <Dialog open={open} fullWidth maxWidth={'md'}>
        <DialogTitle style={{ marginBlockEnd: 0, padding: '16px 24px 0' }}>
          Hồ sơ ứng viên
          <Box className="icon-close" onClick={handleClose}>
            <IconButton color="error">
              <CloseIcon />
            </IconButton>
          </Box>
        </DialogTitle>
        <DialogContent style={{ margin: '20px', fontSize: 16, overflowX: 'hidden' }}>
          <Grid container>
            <Grid container spacing={2} style={{ marginTop: 5 }}>
              <Grid container item xs={8} md={8} spacing={2}>
                <Grid item xs={2} md={2} style={{ fontWeight: 600 }}>
                  Mã hồ sơ:
                </Grid>
                <Grid item xs={8} md={8}>
                  {candidate.code}
                </Grid>
                <Grid item style={{ fontWeight: 600 }}>
                  Họ và tên ứng viên:
                </Grid>
                <Grid item xs={8} md={8}>
                  {candidate.fullName}
                </Grid>
                <Grid item style={{ fontWeight: 600 }}>
                  Tuổi:
                </Grid>
                <Grid item xs={9} md={9}>
                  {candidate.age}
                </Grid>
                <Grid item style={{ fontWeight: 600 }}>
                  Ngày sinh:
                </Grid>
                <Grid item xs={8} md={8}>
                  {moment(candidate.dateOfBirth).format('DD/MM/YYYY')}
                </Grid>
                <Grid item style={{ fontWeight: 600 }}>
                  Số điện thoại:
                </Grid>
                <Grid item xs={9} md={9}>
                  {candidate.phone}
                </Grid>
                <Grid item style={{ fontWeight: 600 }}>
                  Email:
                </Grid>
                <Grid item xs={8} md={8}>
                  {candidate.email}
                </Grid>
              </Grid>
              {/* <Grid container item xs={4} md={4} spacing={2} style={{ height: '50%' }}>
                <img
                  src="https://eitrawmaterials.eu/wp-content/uploads/2016/09/person-icon.png"
                  alt=""
                  style={{ height: '100%' }}
                />
              </Grid> */}
              <Grid container item xs={4} md={4} style={{ height: 200 }}>
                <Grid item xs={12} style={{ height: '100%' }}>
                  <img
                    src={candidate?.image}
                    alt=""
                    style={{
                      height: '100%',
                      float: 'right',
                      border: '1px solid #ccc',
                      borderRadius: 10,
                    }}
                  />
                </Grid>
              </Grid>
              <Grid container item xs={8} md={8} spacing={2}>
                <Grid container item xs={12} md={12} spacing={2}>
                  <Grid item style={{ fontWeight: 600 }}>
                    Địa chỉ:
                  </Grid>
                  <Grid item xs={10} md={10}>
                    {candidate.address}
                  </Grid>
                </Grid>
                <Grid container item xs={6} md={6} spacing={2}>
                  <Grid item style={{ fontWeight: 600 }}>
                    Trình độ học vấn:
                  </Grid>
                  <Grid item xs={5} md={5}>
                    {candidate.education}
                  </Grid>
                </Grid>
                <Grid container item xs={6} md={6} spacing={2}>
                  <Grid item style={{ fontWeight: 600 }}>
                    Ngành:
                  </Grid>
                  <Grid item xs={8} md={8}>
                    {candidate.major}
                  </Grid>
                </Grid>
                <Grid container item xs={12} md={12} spacing={2}>
                  <Grid item style={{ fontWeight: 600 }}>
                    Ứng tuyển vị trí:
                  </Grid>
                  <Grid item xs={8} md={8}>
                    {item?.recruitDtos[0]?.titleRecruit || ''}
                  </Grid>
                </Grid>
                {candidate?.status === 18 ? (
                  <>
                    <Grid container item xs={12} md={12} spacing={2}>
                      <Grid item style={{ fontWeight: 600 }}>
                        Người hẹn:
                      </Grid>
                      <Grid item xs={8} md={8}>
                        {candidate?.interviewer}
                      </Grid>
                    </Grid>
                    <Grid container item xs={12} md={12} spacing={2}>
                      <Grid item style={{ fontWeight: 600 }}>
                        Thời gian hẹn:
                      </Grid>
                      <Grid item xs={8} md={8}>
                        {moment(candidate?.interviewDate).format('DD/MM/YYYY hh:mm A')}
                      </Grid>
                    </Grid>
                  </>
                ) : candidate?.status === 6 ? (
                  <Grid container item xs={12} md={12} spacing={2}>
                    <Grid item style={{ fontWeight: 600 }}>
                      Lý do từ chối:
                    </Grid>
                    <Grid item xs={8} md={8}>
                      {candidate?.refusalReason}
                    </Grid>
                  </Grid>
                ) : (
                  ''
                )}
              </Grid>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            color="primary"
            onClick={() => setShouldOpenConfirmDialog(true)}
            disabled={candidate?.status === 8 || candidate?.status === 9}
          >
            Tiếp Nhận
          </Button>
          <Button
            variant="contained"
            color="error"
            onClick={() => setShouldOpenRefuseDialog(true)}
            disabled={candidate?.status === 8 || candidate?.status === 9}
          >
            Từ Chối
          </Button>
          <Button variant="contained" color="secondary" onClick={handleClose}>
            Hủy
          </Button>
        </DialogActions>
      </Dialog>
      {shouldOpenConfirmDialog && (
        <ConfirmationDialog
          title="Xác nhận"
          text="Bạn có muốn xác nhận ứng viên này đã pass phỏng vấn"
          open={shouldOpenConfirmDialog}
          onConfirmDialogClose={() => setShouldOpenConfirmDialog(false)}
          onYesClick={() => setCandidate({ ...candidate, status: 8 })}
          Yes="Đồng ý"
          No="Hủy"
        />
      )}
      {shouldOpenRefuseDialog && (
        <RefuseDialog
          open={shouldOpenRefuseDialog}
          handleCloseDialog={() => setShouldOpenRefuseDialog(false)}
          candidate={candidate}
          setCandidate={setCandidate}
          handleRefuse={() => setCandidate({ ...candidate, status: 9 })}
        />
      )}
    </>
  );
}
