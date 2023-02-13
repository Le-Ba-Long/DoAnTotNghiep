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
import { addRecruit, editRecruit } from './RecruitService';
import { ValidatorForm, TextValidator } from 'react-material-ui-form-validator';
import { toast } from 'react-toastify';

export default function RecruitDialog(props) {
  const { open, handleClose, item } = props;
  const [recruit, setRecruit] = useState({});

  useEffect(() => {
    setRecruit(item);
  }, []);

  const handleAdd = () => {
    if (recruit.id) {
      if (recruit.status === 1) {
        editRecruit(recruit)
          .then((res) => {
            toast.success('Lưu kế hoạch thành công');
            handleClose();
          })
          .catch((err) => toast.error('Có lỗi xảy ra!'));
      } else if (recruit.status === 2) {
        editRecruit(recruit)
          .then((res) => {
            toast.success('Gửi yêu cầu phê duyệt thành công');
            handleClose();
          })
          .catch((err) => toast.error('Có lỗi xảy ra!'));
      }
    } else {
      if (recruit.status === 1) {
        addRecruit(recruit)
          .then((res) => {
            if (res.data.statusCode === 200) {
              toast.success('Thêm kế hoạch thành công');
              handleClose();
            } else {
              toast.warning(res.data.message);
            }
          })
          .catch((err) => toast.error('Có lỗi xảy ra!'));
      } else if (recruit.status === 2) {
        addRecruit(recruit)
          .then((res) => {
            if (res.data.statusCode === 200) {
              toast.success('Gửi yêu cầu duyệt thành công');
              handleClose();
            } else {
              toast.warning(res.data.message);
            }
          })
          .catch((err) => toast.error('Có lỗi xảy ra!'));
      }
    }
  };

  return (
    <Dialog open={open} fullWidth maxWidth={'md'}>
      <DialogTitle style={{ marginBlockEnd: 0, padding: '16px 24px 0' }}>
        {recruit?.id ? 'Sửa kế hoạch' : 'Thêm kế hoạch'}
        <Box className="icon-close" onClick={handleClose}>
          <IconButton color="error">
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>
      <ValidatorForm onSubmit={() => handleAdd()} onError={(errors) => console.log(errors)}>
        <DialogContent style={{ padding: '0 20px' }}>
          <Grid container spacing={2} style={{ marginTop: 5 }}>
            <Grid container item xs={12} md={12} spacing={2}>
              <Grid item xs={4} md={4}>
                <TextValidator
                  label="Mã kế hoạch"
                  variant="outlined"
                  fullWidth
                  value={recruit?.code}
                  onChange={(event) => setRecruit({ ...recruit, code: event.target.value })}
                  validators={['required', 'matchRegexp:^MaTD[0-9]{4}$']}
                  errorMessages={[
                    'Vui lòng nhập trường này',
                    'Mã kế hoạch chưa đúng format VD:(MaTD9999)',
                  ]}
                />
              </Grid>
              <Grid item xs={5} md={5}>
                <TextValidator
                  label="Tên kế hoạch"
                  variant="outlined"
                  fullWidth
                  value={recruit?.titleRecruit}
                  onChange={(event) => setRecruit({ ...recruit, titleRecruit: event.target.value })}
                  validators={['required', 'matchRegexp:^[0-9a-zA-Z].{5,100}$']}
                  errorMessages={[
                    'Vui lòng nhập trường này',
                    'Tên kế hoạch phải lớn hơn 5 kí tự và nhỏ hơn 100 kí tự',
                  ]}
                />
              </Grid>
              <Grid item xs={3} md={3}>
                <TextValidator
                  label="Số lượng tuyển dụng"
                  type="number"
                  variant="outlined"
                  fullWidth
                  value={recruit?.quantity}
                  onChange={(event) => setRecruit({ ...recruit, quantity: event.target.value })}
                  validators={['required']}
                  errorMessages={['Vui lòng nhập trường này']}
                />
              </Grid>
            </Grid>
            <Grid item xs={12} md={12}>
              <TextValidator
                label="Đãi ngộ"
                variant="outlined"
                fullWidth
                multiline
                minRows={2}
                maxRows={5}
                value={recruit?.benefitsReceived}
                onChange={(event) =>
                  setRecruit({ ...recruit, benefitsReceived: event.target.value })
                }
                validators={['required']}
                errorMessages={['Vui lòng nhập trường này']}
              />
            </Grid>
            <Grid item xs={12} md={12}>
              <TextValidator
                label="Yêu cầu tuyển dụng"
                variant="outlined"
                fullWidth
                multiline
                minRows={2}
                maxRows={5}
                value={recruit?.requireRecruit}
                onChange={(event) => setRecruit({ ...recruit, requireRecruit: event.target.value })}
                validators={['required']}
                errorMessages={['Vui lòng nhập trường này']}
              />
            </Grid>
            <Grid item xs={12} md={12}>
              <TextValidator
                label="Mô tả"
                multiline
                minRows={4}
                maxRows={10}
                variant="outlined"
                fullWidth
                value={recruit?.description}
                onChange={(event) => setRecruit({ ...recruit, description: event.target.value })}
                validators={['required']}
                errorMessages={['Vui lòng nhập trường này']}
              />
            </Grid>
            <Grid item xs={12} md={12}>
              <TextValidator
                label="Kênh tuyển dụng"
                variant="outlined"
                fullWidth
                multiline
                minRows={1}
                value={recruit?.recruitmentChannel}
                onChange={(event) =>
                  setRecruit({ ...recruit, recruitmentChannel: event.target.value })
                }
                validators={['required']}
                errorMessages={['Vui lòng nhập trường này']}
              />
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <>
            <Button variant="contained" color="secondary" onClick={handleClose}>
              Hủy
            </Button>
            <Button
              type="submit"
              variant="contained"
              color="primary"
              onClick={() => setRecruit({ ...recruit, status: 2 })}
            >
              Lưu và gửi
            </Button>
            <Button
              type="submit"
              variant="contained"
              color="primary"
              onClick={() => setRecruit({ ...recruit, status: 1 })}
            >
              Lưu nháp
            </Button>
          </>
        </DialogActions>
      </ValidatorForm>
    </Dialog>
  );
}
