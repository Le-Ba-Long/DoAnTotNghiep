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
import { ValidatorForm, TextValidator } from 'react-material-ui-form-validator';

export default function RequestDialog(props) {
  const { open, item, handleRequest, handleCloseDialog, setItem } = props;

  return (
    <Dialog open={open} fullWidth maxWidth={'md'}>
      <DialogTitle style={{ marginBlockEnd: 0, padding: '16px 24px 0' }}>
        Yêu cầu chỉnh sửa
        <Box className="icon-close" onClick={handleCloseDialog}>
          <IconButton color="error">
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>
      <ValidatorForm onSubmit={() => handleRequest()} onError={(errors) => console.log(errors)}>
        <DialogContent style={{ padding: '0 20px' }}>
          <Grid container spacing={2} style={{ marginTop: 5 }}>
            <Grid item xs={12} md={12}>
              <TextValidator
                label="Nội dung yêu cầu"
                multiline
                minRows={4}
                maxRows={10}
                variant="outlined"
                fullWidth
                value={item?.additionalRequestContent}
                onChange={(event) =>
                  setItem({ ...item, additionalRequestContent: event.target.value })
                }
                validators={['required']}
                errorMessages={['Vui lòng nhập trường này']}
              />
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <>
            <Button variant="contained" color="secondary" onClick={handleCloseDialog}>
              Hủy
            </Button>
            <Button type="submit" variant="contained" color="primary">
              Lưu
            </Button>
          </>
        </DialogActions>
      </ValidatorForm>
    </Dialog>
  );
}
