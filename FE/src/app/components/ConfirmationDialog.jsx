import React from 'react';
import { Dialog, Button, DialogActions } from '@mui/material';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';

const ConfirmationDialog = ({
  open,
  onConfirmDialogClose,
  text,
  title = 'confirm',
  onYesClick,
  Yes,
  No,
}) => {
  return (
    <Dialog maxWidth="xs" fullWidth={true} open={open} onClose={onConfirmDialogClose}>
      <div style={{ padding: '0 24px' }}>
        <h4 style={{ fontSize: 20, marginBlockEnd: 0, marginBlockStart: 10 }}>{title}</h4>
        <Box className="icon-close" onClick={onConfirmDialogClose}>
          <IconButton color="error">
            <CloseIcon />
          </IconButton>
        </Box>
        <p>{text}</p>
      </div>

      <DialogActions>
        <>
          <Button onClick={onYesClick} variant="contained" color="primary">
            {Yes}
          </Button>

          {No && (
            <Button onClick={onConfirmDialogClose} variant="contained" color="secondary">
              {No}
            </Button>
          )}
        </>
      </DialogActions>
    </Dialog>
  );
};

export default ConfirmationDialog;
