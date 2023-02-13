import React, { useState, useEffect, useRef } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import CloseIcon from '@mui/icons-material/Close';
import { Collapse, Typography } from '@material-ui/core';

export default function EmployeeDialog(props) {
  const { open, handleClose, handleCloseDialog, item } = props;
  return (
    <Dialog open={open} fullWidth maxWidth={'lg'}>
      <DialogTitle>
        Thông tin nhân viên
        <Box className="icon-close" onClick={handleClose}>
          <IconButton color="error">
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>
      <DialogContent>
        <Collapse in={true}>
          <Typography>
            This is some content that can be shown or hidden by clicking the button above.
          </Typography>
        </Collapse>
      </DialogContent>
      <DialogActions>
        <Button variant="contained" color="secondary" onClick={handleClose}>
          Hủy
        </Button>
      </DialogActions>
    </Dialog>
  );
}
