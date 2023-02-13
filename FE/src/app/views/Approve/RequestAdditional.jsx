import React, { useEffect } from 'react';
import { Button } from '@mui/material';
import Dialog from '@mui/material/Dialog';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import CloseIcon from '@mui/icons-material/Close';
import { ValidatorForm, TextValidator } from 'react-material-ui-form-validator';

export default function RequestAdditional(props) {
  const { open, handleClose, item, setItem, onSelectYes } = props;
  const handleRefuse = () => {
    onSelectYes();
    handleClose();
  };
  return (
    <Dialog open={open} fullWidth maxWidth={'md'}>
      <DialogTitle style={{ marginBlockEnd: 0, padding: '16px 24px 0' }}>
        Yêu cầu bổ sung
        <Box className="icon-close" onClick={handleClose}>
          <IconButton color="error">
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>
      <ValidatorForm onSubmit={() => handleRefuse()} onError={(errors) => console.log(errors)}>
        <DialogContent style={{ padding: '20px' }}>
          <TextValidator
            label="Nội dung yêu cầu bổ sung"
            variant="outlined"
            fullWidth
            value={item?.feedback}
            onChange={(event) => setItem({ ...item, feedback: event.target.value })}
            validators={['required']}
            errorMessages={['Vui lòng nhập trường này']}
          />
        </DialogContent>
        <DialogActions>
          <Button variant="contained" color="primary" type="submit">
            Lưu
          </Button>
          <Button variant="contained" color="secondary" onClick={handleClose}>
            Đóng
          </Button>
        </DialogActions>
      </ValidatorForm>
    </Dialog>
  );
}
