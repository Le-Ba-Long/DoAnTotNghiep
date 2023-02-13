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
import { addTimeKeeping, editTimeKeeping } from './TimeKeepingService';
import Autocomplete from '@mui/material/Autocomplete';
import { ValidatorForm, TextValidator } from 'react-material-ui-form-validator';
import { toast } from 'react-toastify';
import { months } from 'app/constant';

export default function TimeKeepingDialog(props) {
  const { open, handleClose, timeKeeping, setTimeKeeping, hanldeCloseDialog, item } = props;
  console.log(timeKeeping);
  const [monthObject, setMonthObject] = useState(null);

  ValidatorForm.addValidationRule('isOptionSelected', (value) => {
    return value ? true : 'Vui lòng chọn trường này';
  });

  useEffect(() => {
    if (timeKeeping?.month) {
      setMonthObject({ name: `Tháng ${timeKeeping?.month}`, value: timeKeeping?.month });
    }
    // setTimeKeeping({ ...timeKeeping, employee: item });
  }, []);

  useEffect(() => {
    setTimeKeeping({ ...timeKeeping, month: monthObject?.value });
  }, [monthObject]);

  const handleAdd = () => {
    if (timeKeeping?.id) {
      editTimeKeeping(timeKeeping)
        .then((res) => {
          if (res.data.statusCode === 200) {
            toast.success('Sửa bản ghi chấm công ');
            handleClose();
          } else {
            toast.warning(res.data.message);
          }
        })
        .catch((err) => toast.error('Có lỗi xảy ra!'));
    } else {
      addTimeKeeping(timeKeeping)
        .then((res) => {
          if (res.data.statusCode === 200) {
            toast.success('Thêm bản ghi chấm công ');
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
        {timeKeeping?.id ? 'Sửa bản ghi chấm công' : 'Thêm bản ghi chấm công'}
        <Box className="icon-close" onClick={hanldeCloseDialog}>
          <IconButton color="error">
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>
      <ValidatorForm onSubmit={() => handleAdd()} onError={(errors) => console.log(errors)}>
        <DialogContent style={{ padding: '0 20px' }}>
          <Grid container spacing={2} style={{ marginTop: 5 }}>
            <Grid container item xs={12} md={12} spacing={2}>
              <Grid item xs={3} md={3}>
                <TextValidator
                  label="Mã chấm công"
                  variant="outlined"
                  fullWidth
                  value={timeKeeping?.code}
                  onChange={(event) => setTimeKeeping({ ...timeKeeping, code: event.target.value })}
                  validators={['required', 'matchRegexp:^MaCC[0-9]{4}$']}
                  errorMessages={[
                    'Vui lòng nhập trường này',
                    'Mã kế hoạch chưa đúng format VD:(MaCC9999)',
                  ]}
                />
              </Grid>
              <Grid item xs={3} md={3}>
                <TextValidator
                  label="Năm"
                  variant="outlined"
                  fullWidth
                  type="number"
                  value={timeKeeping?.year}
                  onChange={(event) => setTimeKeeping({ ...timeKeeping, year: event.target.value })}
                  validators={['required']}
                  errorMessages={['Vui lòng nhập trường này']}
                />
              </Grid>
              <Grid item xs={3} md={3}>
                <Autocomplete
                  fullWidth
                  options={months}
                  getOptionLabel={(option) => option.name}
                  value={monthObject}
                  onChange={(event, value) => setMonthObject(value)}
                  renderInput={(params) => (
                    <TextValidator
                      {...params}
                      label="Tháng"
                      placeholder="Tháng"
                      validators={['isOptionSelected']}
                      errorMessages={['Vui lòng chọn trường này']}
                    />
                  )}
                />
              </Grid>{' '}
              <Grid item xs={3} md={3}>
                <TextValidator
                  label="Số ngày đi làm"
                  type="number"
                  variant="outlined"
                  fullWidth
                  value={timeKeeping?.numberWorkDay}
                  onChange={(event) =>
                    setTimeKeeping({ ...timeKeeping, numberWorkDay: event.target.value })
                  }
                  validators={['required', 'maxNumber:23']}
                  errorMessages={[
                    'Vui lòng nhập trường này',
                    'Số ngày nhập không được quá số ngày quy định đi làm',
                  ]}
                />
              </Grid>
              <Grid item xs={3} md={3}>
                <TextValidator
                  label="Số ngày nghỉ (có phép)"
                  type="number"
                  variant="outlined"
                  fullWidth
                  value={timeKeeping?.numberDayOff}
                  onChange={(event) =>
                    setTimeKeeping({ ...timeKeeping, numberDayOff: event.target.value })
                  }
                  validators={['required', 'maxNumber:3']}
                  errorMessages={[
                    'Vui lòng nhập trường này',
                    'Số ngày nhập không được quá số ngày quy định',
                  ]}
                />
              </Grid>
              <Grid item xs={3} md={3}>
                <TextValidator
                  label="Số ngày nghỉ (không phép)"
                  type="number"
                  variant="outlined"
                  fullWidth
                  value={timeKeeping?.numberDayUnexcusedLeave}
                  onChange={(event) =>
                    setTimeKeeping({ ...timeKeeping, numberDayUnexcusedLeave: event.target.value })
                  }
                  validators={['required', 'maxNumber:2']}
                  errorMessages={[
                    'Vui lòng nhập trường này',
                    'Số ngày nhập không được quá số ngày quy định',
                  ]}
                />
              </Grid>
              <Grid item xs={3} md={3}>
                <TextValidator
                  label="Số giờ làm thêm"
                  type="number"
                  variant="outlined"
                  fullWidth
                  value={timeKeeping?.numberOvertimeHours}
                  onChange={(event) =>
                    setTimeKeeping({ ...timeKeeping, numberOvertimeHours: event.target.value })
                  }
                  validators={['required']}
                  errorMessages={['Vui lòng nhập trường này']}
                />
              </Grid>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <>
            <Button variant="contained" color="secondary" onClick={hanldeCloseDialog}>
              Hủy
            </Button>
            <Button
              type="submit"
              variant="contained"
              color="primary"
              onClick={() => setTimeKeeping({ ...timeKeeping, status: 2 })}
            >
              {timeKeeping?.id ? 'Sửa' : 'Thêm'}
            </Button>
          </>
        </DialogActions>
      </ValidatorForm>
    </Dialog>
  );
}
