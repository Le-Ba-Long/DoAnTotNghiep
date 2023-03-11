import React, { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import CloseIcon from '@mui/icons-material/Close';
import Collapse from '@mui/material/Collapse';
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';
import UpdateSalary from './UpdateSalary';
import InformationDialog from './InformationDialog';
import PromoteDialog from './PromoteDialog';
import QuitJobDialog from './QuitJobDialog';
import HistoryDialog from './HistoryDialog';
import ContractExtensionDialog from './ContractExtensionDialog';
import { getListHistoryEmployee } from './UpdateHappeningService';
import { toast } from 'react-toastify';

export default function UpdateHappeningDialog(props) {
  const { open, handleClose, item } = props;

  const [shouldOpenInformationDialog, setOpenInformationDialog] = useState(false);
  const [shouldOpenPromoteDialog, setOpenPromoteDialog] = useState(false);
  const [shouldOpenSalaryeDialog, setOpenSalaryDialog] = useState(false);
  const [shouldOpenContractExtensionDialog, setOpenContractExtensionDialog] = useState(false);
  const [shouldOpenQuitJobDialog, setOpenQuitJobDialog] = useState(false);
  const [shouldOpenHistoryDialog, setOpenHistoryDialog] = useState(false);
  const [listHistoryEmployee, setListHistoryEmployee] = useState(false);

  useEffect(() => {
    getListHistoryEmployee(item?.id)
      .then((res) => {
        if (res.data.statusCode === 200) {
          setListHistoryEmployee(res.data.data);
        } else {
          toast.warning(res.data.message);
        }
      })
      .catch((err) => toast.error('Có lỗi xảy ra!'));
  }, []);

  return (
    <>
      <Dialog open={open} fullWidth maxWidth={'lg'}>
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
          <p onClick={() => setOpenSalaryDialog(!shouldOpenSalaryeDialog)} className="collapse">
            TĂNG LƯƠNG {shouldOpenSalaryeDialog ? <ExpandLess /> : <ExpandMore />}
          </p>
          <Collapse in={shouldOpenSalaryeDialog} timeout="auto" unmountOnExit>
            <UpdateSalary item={item} />
          </Collapse>
          <p
            onClick={() => setOpenContractExtensionDialog(!shouldOpenContractExtensionDialog)}
            className="collapse"
          >
            GIA HẠN HỢP ĐỒNG {shouldOpenContractExtensionDialog ? <ExpandLess /> : <ExpandMore />}
          </p>
          <Collapse in={shouldOpenContractExtensionDialog} timeout="auto" unmountOnExit>
            <ContractExtensionDialog item={item} />
          </Collapse>
          <p onClick={() => setOpenHistoryDialog(!shouldOpenHistoryDialog)} className="collapse">
            LỊCH SỬ CÔNG TÁC {shouldOpenHistoryDialog ? <ExpandLess /> : <ExpandMore />}
          </p>
          <Collapse in={shouldOpenHistoryDialog} timeout="auto" unmountOnExit>
            <HistoryDialog list={listHistoryEmployee} />
          </Collapse>
        </DialogContent>
        <DialogActions>
          <>
            <Button variant="contained" color="primary" onClick={() => setOpenQuitJobDialog(true)}>
              Nghỉ việc
            </Button>
            <Button variant="contained" color="secondary" onClick={handleClose}>
              Hủy
            </Button>
          </>
        </DialogActions>
      </Dialog>
      {shouldOpenQuitJobDialog && (
        <QuitJobDialog
          open={shouldOpenQuitJobDialog}
          handleCloseFinishDialog={() => setOpenQuitJobDialog(false)}
          employee={item}
          handleClose={handleClose}
        />
      )}
    </>
  );
}
