import React, { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import IconButton from '@mui/material/IconButton';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import CloseIcon from '@mui/icons-material/Close';
import moment from 'moment';
import { editEmployee } from './EmployeeService';
import ConfirmationDialog from '../../components/ConfirmationDialog';
import './EmployeeView.scss';
import { toast } from 'react-toastify';

export default function EmployeeView(props) {
  const { open, handleClose, item, setItem } = props;
  const [shouldOpenConfirmDialog, setShouldOpenConfirmDialog] = useState(false);

  console.log(item);

  useEffect(() => {
    if (item.status === 2) {
      editEmployee(item)
        .then((res) => {
          if (res.data.statusCode === 200) {
            toast.success('Hồ sơ đã được trình lên lãnh đạo');
            handleClose();
          } else {
            toast.warning('Lỗi xác thực');
            handleClose();
          }
        })
        .catch((err) => toast.error('Có lỗi xảy ra'));
    }
  }, [item.status]);

  return (
    <>
      <Dialog open={open} fullWidth maxWidth={'md'}>
        <DialogTitle sx={{ bgcolor: '#FFFFE8' }}>
          <Box className="icon-close" onClick={handleClose}>
            <IconButton color="error">
              <CloseIcon />
            </IconButton>
          </Box>
        </DialogTitle>
        <DialogContent style={{ padding: '30px 60px 30px' }} sx={{ bgcolor: '#FFFFE8' }}>
          <Grid
            container
            spacing={2}
            style={{ fontFamily: '"Times New Roman", Times, serif', fontSize: '18px' }}
          >
            <Grid
              item
              container
              xs={12}
              style={{ fontSize: '30px', marginBottom: 40 }}
              alignItems="center"
              justifyContent="center"
            >
              Hồ sơ nhân viên
            </Grid>
            <Grid item container xs={8} className="pd-60" spacing={1}>
              <Grid item container xs={12} className="fw-600">
                I .THÔNG TIN CÁ NHÂN
              </Grid>
              <Grid item container xs={12} className="ml-10">
                <Grid item xs={5} className="fw-600 ">
                  Mã nhân viên:
                </Grid>
                <Grid item xs={6}>
                  {item?.code}
                </Grid>
              </Grid>
              <Grid item container xs={12} className="ml-10">
                <Grid item xs={5} className="fw-600">
                  Họ và Tên:
                </Grid>
                <Grid item xs={6}>
                  {item?.fullName}
                </Grid>
              </Grid>
              <Grid item container xs={12} className="ml-10">
                <Grid item xs={5} className="fw-600">
                  Giới tính:
                </Grid>
                <Grid item xs={6}>
                  {item?.sex}
                </Grid>
              </Grid>
              <Grid item container xs={12} className="ml-10">
                <Grid item xs={5} className="fw-600">
                  Ngày sinh:
                </Grid>
                <Grid item xs={6}>
                  {moment(item?.dateOfBirth).format('DD/MM/YYYY')}
                </Grid>
              </Grid>
              <Grid item container xs={12} className="ml-10">
                <Grid item xs={5} className="fw-600">
                  Email:
                </Grid>
                <Grid item xs={6}>
                  {item?.email}
                </Grid>
              </Grid>
              <Grid item container xs={12} className="ml-10">
                <Grid item xs={5} className="fw-600">
                  SĐT:
                </Grid>
                <Grid item xs={6}>
                  {item?.phone}
                </Grid>
              </Grid>
            </Grid>
            <Grid
              item
              container
              xs={3}
              spacing={1}
              justifyContent="flex-end"
              style={{ marginRight: 60, marginBottom: 20 }}
            >
              {item?.image ? (
                <img
                  src={item?.image}
                  alt=""
                  style={{ height: '210px', border: '1px solid #000' }}
                />
              ) : (
                <img
                  src="https://icons.veryicon.com/png/o/internet--web/55-common-web-icons/person-4.png"
                  alt=""
                  style={{ height: '210px', border: '1px solid #000' }}
                />
              )}
            </Grid>
            <Grid item container xs={12} className="pd-60" spacing={1} style={{}}>
              <Grid item xs={2} className="fw-600 mr-10">
                Địa chỉ:
              </Grid>
              <Grid item xs={8} style={{ marginLeft: 60 }}>
                {item?.address}
              </Grid>
            </Grid>
            <Grid item container xs={12} className="pd-60 fw-600">
              II. THÔNG TIN LIÊN QUAN
            </Grid>
            <Grid item container xs={12} className="pd-60" spacing={1}>
              <Grid item container xs={6}>
                <Grid item className="fw-600 mr-10 ml-10">
                  Số CCCD:
                </Grid>
                <Grid item xs={7}>
                  {item?.numberIdentityCard}
                </Grid>
              </Grid>
              <Grid item container xs={6}>
                <Grid item className="fw-600 mr-10 ml-10">
                  Ngày cấp:
                </Grid>
                <Grid item xs={7}>
                  {moment(item?.issuedDateIdentityCard).format('DD/MM/YYYY')}
                </Grid>
              </Grid>
            </Grid>
            <Grid item container xs={12} className="pd-60" spacing={1}>
              <Grid item container xs={12}>
                <Grid item className="fw-600 mr-10 ml-10">
                  Nơi cấp:
                </Grid>
                <Grid item xs={7}>
                  {item?.placeOfGrantIdentityCard}
                </Grid>
              </Grid>
              <Grid item container xs={6}>
                <Grid item className="fw-600 mr-10 ml-10">
                  Dân tộc:
                </Grid>
                <Grid item xs={7}>
                  {item?.nation}
                </Grid>
              </Grid>
              <Grid item container xs={6}>
                <Grid item className="fw-600 mr-10 ml-10">
                  Tôn giáo:
                </Grid>
                <Grid item xs={7}>
                  {item?.religion}
                </Grid>
              </Grid>
            </Grid>
            <Grid item container xs={12} className="pd-60" spacing={1}>
              <Grid item container xs={6}>
                <Grid item className="fw-600 mr-10 ml-10">
                  Số bảo hiểm y tế:
                </Grid>
                <Grid item xs={5}>
                  {item?.numberMedicalInsurance}
                </Grid>
              </Grid>
              <Grid item container xs={6}>
                <Grid item className="fw-600 mr-10 ml-10">
                  Ngày cấp:
                </Grid>
                <Grid item xs={6}>
                  {item?.issuedDateMedicalInsurance
                    ? moment(item?.issuedDateMedicalInsurance).format('DD/MM/YYYY')
                    : ''}
                </Grid>
              </Grid>
              <Grid item container xs={12}>
                <Grid item className="fw-600 mr-10 ml-10">
                  Nơi cấp:
                </Grid>
                <Grid item xs={7}>
                  {item?.placeOfIssueMedicalInsurance}
                </Grid>
              </Grid>
            </Grid>
            <Grid item container xs={12} className="pd-60" spacing={1}>
              <Grid item container xs={6}>
                <Grid item className="fw-600 mr-10 ml-10">
                  Số bảo hiểm xã hội:
                </Grid>
                <Grid item xs={5}>
                  {item?.numberSocialInsurance}
                </Grid>
              </Grid>
              <Grid item container xs={6}>
                <Grid item className="fw-600 mr-10 ml-10">
                  Ngày cấp:
                </Grid>
                <Grid item xs={6}>
                  {item?.issuedDateSocialInsurance
                    ? moment(item?.issuedDateSocialInsurance).format('DD/MM/YYYY')
                    : ''}
                </Grid>
              </Grid>
              <Grid item container xs={12}>
                <Grid item className="fw-600 mr-10 ml-10">
                  Nơi cấp:
                </Grid>
                <Grid item xs={7}>
                  {item?.placeOfIssueSocialInsurance}
                </Grid>
              </Grid>
            </Grid>
            <Grid item container xs={12} spacing={1}>
              <Grid item container xs={12} className="fw-600" style={{ padding: '0 50px' }}>
                III. VỊ TRÍ LÀM VIỆC
              </Grid>
              <Grid item container xs={12} className="pd-60">
                <Grid item className="fw-600 mr-10">
                  Phòng ban:
                </Grid>
                <Grid item xs={9}>
                  {item?.department?.name}
                </Grid>
              </Grid>
              <Grid item container xs={12} className="pd-60">
                <Grid item className="fw-600 mr-10">
                  Vị trí công việc:
                </Grid>
                <Grid item xs={6}>
                  {item?.positions[0]?.name}
                </Grid>
              </Grid>
              <Grid item container xs={12} className="fw-600" style={{ padding: '0 50px' }}>
                IV. TRÌNH ĐỘ CHUYÊN MÔN
              </Grid>
              <Grid item container xs={12} className="pd-60">
                <Grid item className="fw-600 mr-10">
                  Trình độ học vấn:
                </Grid>
                <Grid item xs={6}>
                  {item?.education}
                </Grid>
              </Grid>
              <Grid item container xs={12} className="pd-60">
                <Grid item className="fw-600 mr-10">
                  Chuyên ngành:
                </Grid>
                <Grid item xs={7}>
                  {item?.major}
                </Grid>
              </Grid>
              <Grid item container xs={12} className="pd-60">
                <Grid item className="fw-600 mr-10">
                  Bằng cấp:
                </Grid>
                <Grid item xs={6}>
                  {item?.certificate?.name}
                </Grid>
              </Grid>
              <Grid item container xs={12} className="pd-60">
                <Grid item className="fw-600 mr-10">
                  Danh sách chứng chỉ:
                </Grid>
                <Grid item xs={12} style={{ marginTop: 10 }}>
                  <table style={{ width: '85%' }} className="table">
                    <tr className="tr">
                      <th style={{ textAlign: 'center', width: '5%' }}>STT</th>
                      <th style={{ textAlign: 'center', width: '20%' }}>Tên chứng chỉ</th>
                      <th style={{ textAlign: 'center', width: '20%' }}>Mô tả</th>
                    </tr>
                    {item?.languages.map((item, index) => (
                      <>
                        <tr className="tr">
                          <td style={{ textAlign: 'center' }}>{index + 1}</td>
                          <td style={{ textAlign: 'center' }}>{item?.name}</td>
                          <td style={{ textAlign: 'center' }}>{item?.description}</td>
                        </tr>
                      </>
                    ))}
                  </table>
                </Grid>
              </Grid>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions sx={{ bgcolor: '#FFFFE8' }}>
          <Button variant="contained" color="secondary" onClick={handleClose}>
            Hủy
          </Button>
          {item?.status === 12 || item?.status === 2 || item?.status === 3 ? (
            ''
          ) : (
            <Button
              variant="contained"
              color="primary"
              onClick={() => setShouldOpenConfirmDialog(true)}
              disabled={
                item?.status === 12 ||
                item?.status === 2 ||
                item?.status === 3 ||
                item?.status === 14
              }
            >
              Trình lãnh đạo
            </Button>
          )}
        </DialogActions>
      </Dialog>
      {shouldOpenConfirmDialog && (
        <ConfirmationDialog
          title="Xác nhận"
          text="Bạn có muốn trình lãnh đạo hồ sơ này?"
          open={shouldOpenConfirmDialog}
          onConfirmDialogClose={() => setShouldOpenConfirmDialog(false)}
          onYesClick={() => setItem({ ...item, status: 2 })}
          Yes="Đồng ý"
          No="Hủy"
        />
      )}
    </>
  );
}
