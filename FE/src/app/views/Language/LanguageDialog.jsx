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
import { addLanguage, editLanguage } from './LanguageService';
import { ValidatorForm, TextValidator } from 'react-material-ui-form-validator';
import { toast } from 'react-toastify';

export default function LanguageDialog(props) {
  const { open, handleClose, item, readOnly } = props;
  const [language, setLanguage] = useState({});

  useEffect(() => {
    setLanguage(item);
  }, []);

  const handleAdd = () => {
    if (language.id) {
      editLanguage(language)
        .then((res) => {
          toast.success('Sửa thông tin chứng chỉ thành công');
          handleClose();
        })
        .catch((err) => toast.error('Có lỗi xảy ra!'));
    } else {
      addLanguage(language)
        .then((res) => {
          if (res.data.statusCode === 200) {
            toast.success('Thêm chứng chỉ thành công');
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
        {language.id && readOnly
          ? 'Thông tin chứng chỉ'
          : language.id
          ? 'Sửa chứng chỉ'
          : 'Thêm chứng chỉ'}
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
              <Grid item xs={6} md={6}>
                <TextValidator
                  label="Mã chứng chỉ"
                  variant="outlined"
                  fullWidth
                  InputProps={{
                    readOnly: readOnly,
                  }}
                  value={language?.code}
                  onChange={(event) => setLanguage({ ...language, code: event.target.value })}
                  validators={['required', 'matchRegexp:^MaNN[0-9]{4}$']}
                  errorMessages={[
                    'Vui lòng nhập trường này',
                    'Mã chứng chỉ chưa đúng format VD:(MaNN9999)',
                  ]}
                />
              </Grid>
              <Grid item xs={6} md={6}>
                <TextValidator
                  label="Tên chứng chỉ"
                  variant="outlined"
                  fullWidth
                  InputProps={{
                    readOnly: readOnly,
                  }}
                  value={language?.name}
                  onChange={(event) => setLanguage({ ...language, name: event.target.value })}
                  validators={['required', 'matchRegexp:^[0-9a-zA-Z].{5,100}$']}
                  errorMessages={[
                    'Vui lòng nhập trường này',
                    'Tên chứng chỉ phải lớn hơn 5 kí tự và nhỏ hơn 100 kí tự',
                  ]}
                />
              </Grid>
            </Grid>
            <Grid item xs={12} md={12} spacing={2}>
              <TextValidator
                label="Mô tả"
                multiline
                minRows={4}
                maxRows={10}
                variant="outlined"
                fullWidth
                InputProps={{
                  readOnly: readOnly,
                }}
                value={language?.description}
                onChange={(event) =>
                  setLanguage({
                    ...language,
                    description: event.target.value,
                  })
                }
                validators={['required']}
                errorMessages={['Vui lòng nhập trường này']}
              />
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          {readOnly ? (
            <Button variant="contained" color="secondary" onClick={handleClose}>
              Đóng
            </Button>
          ) : (
            <>
              <Button variant="contained" color="secondary" onClick={handleClose}>
                Hủy
              </Button>
              <Button type="submit" variant="contained" color="primary">
                {language.id ? 'Lưu' : 'Thêm'}
              </Button>
            </>
          )}
        </DialogActions>
      </ValidatorForm>
    </Dialog>
  );
}
