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
import Autocomplete from '@mui/material/Autocomplete';
import { ValidatorForm, TextValidator } from 'react-material-ui-form-validator';
import { toast } from 'react-toastify';
import Collapse from '@mui/material/Collapse';
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';
import UpdateSalary from './UpdateSalary';
import InformationDialog from './InformationDialog';
import PromoteDialog from './PromoteDialog';

export default function UpdateHappeningDialog(props) {
  const { open, handleClose, item } = props;

  const [shouldOpenInformationDialog, setOpenInformationDialog] = useState(false);
  const [shouldOpenPromoteDialog, setOpenPromoteDialog] = useState(false);

  return (
    <Dialog open={open} fullWidth maxWidth={'md'}>
      <DialogTitle style={{ marginBlockEnd: 0, padding: '16px 24px 0' }}>
        <Box className="icon-close" onClick={handleClose}>
          <IconButton color="error">
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>
      <DialogContent style={{ padding: '20px 20px' }}>
        <p
          onClick={() => setOpenInformationDialog(!shouldOpenInformationDialog)}
          className="collapse"
        >
          THÔNG TIN CÁ NHÂN {shouldOpenInformationDialog ? <ExpandLess /> : <ExpandMore />}
        </p>
        <Collapse in={shouldOpenInformationDialog} timeout="auto" unmountOnExit>
          <InformationDialog item={item} />
        </Collapse>
        <p onClick={() => setOpenPromoteDialog(!shouldOpenPromoteDialog)} className="collapse">
          THĂNG CHỨC / ĐIỀU CHUYỂN CÔNG TÁC{' '}
          {shouldOpenPromoteDialog ? <ExpandLess /> : <ExpandMore />}
        </p>
        <Collapse in={shouldOpenPromoteDialog} timeout="auto" unmountOnExit>
          <PromoteDialog item={item} />
        </Collapse>
        <p
          onClick={() => setOpenInformationDialog(!shouldOpenInformationDialog)}
          className="collapse"
        >
          TĂNG LƯƠNG {shouldOpenInformationDialog ? <ExpandLess /> : <ExpandMore />}
        </p>
        <Collapse in={shouldOpenInformationDialog} timeout="auto" unmountOnExit>
          <UpdateSalary />
        </Collapse>
      </DialogContent>
      <DialogActions>
        <>
          <Button variant="contained" color="secondary" onClick={handleClose}>
            Hủy
          </Button>
        </>
      </DialogActions>
    </Dialog>
  );
}
