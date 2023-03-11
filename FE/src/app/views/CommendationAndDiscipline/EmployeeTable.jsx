import React, { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import RemoveRedEyeIcon from '@mui/icons-material/RemoveRedEye';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import CloseIcon from '@mui/icons-material/Close';
import { checkStatus } from 'app/constant';
import MaterialTable from 'material-table';
import { getListEmployee } from './CommendationAndDisciplineService';
import { toast } from 'react-toastify';
import CommendationAndDisciplineDialog from './CommendationAndDisciplineDialog';
import { colorTable } from 'app/constant';
export default function EmployeeTable(props) {
  const { open, handleClose } = props;
  const [listEmployee, setListEmployee] = useState([]);
  const [shouldOpenCommendationAndDisciplineDialog, setShouldOpenCommendationAndDisciplineDialog] =
    useState();
  const [item, setItem] = useState({});

  const columns = [
    {
      title: 'STT',
      field: 'STT',
      render: (rowData) => rowData.tableData.id + 1,
      cellStyle: {
        width: '3%',
        textAlign: 'center',
      },
    },
    {
      title: 'Thao tác',
      field: 'action',
      render: (rowData) => (
        <>
          <IconButton
            color="primary"
            onClick={() => {
              setItem(rowData);
              setShouldOpenCommendationAndDisciplineDialog(true);
            }}
          >
            <RemoveRedEyeIcon />
          </IconButton>
        </>
      ),
      cellStyle: {
        width: '10%',
        textAlign: 'center',
      },
    },
    {
      title: 'Mã hồ sơ',
      field: 'code',
      render: (rowData) => rowData?.code,
      cellStyle: {
        width: '10%',
        textAlign: 'center',
      },
    },
    {
      title: 'Họ và tên',
      field: 'fullName',
      render: (rowData) => rowData?.fullName,
      cellStyle: {
        width: '10%',
        textAlign: 'left',
      },
      headerStyle: {
        textAlign: 'left',
      },
    },
    {
      title: 'Email',
      field: 'email',
      render: (rowData) => rowData?.email,
      cellStyle: {
        width: '10%',
        textAlign: 'left',
      },
      headerStyle: {
        textAlign: 'left',
      },
    },
    {
      title: 'SĐT',
      field: 'phone',
      render: (rowData) => rowData?.phone,
      cellStyle: {
        width: '10%',
        textAlign: 'left',
      },
      headerStyle: {
        textAlign: 'left',
      },
    },
    {
      title: 'Trạng thái',
      field: 'status',
      render: (rowData) => {
        let message = checkStatus(rowData.status).message;
        let color = checkStatus(rowData.status).color;
        return <div className={color}>{message}</div>;
      },
      cellStyle: {
        width: '10%',
        textAlign: 'left',
      },
      headerStyle: {
        textAlign: 'left',
      },
    },
  ];

  useEffect(() => {
    updatePageData();
  }, []);

  const updatePageData = () => {
    getListEmployee()
      .then((res) => {
        if (res.data.statusCode === 200) {
          setListEmployee(
            res.data.data.filter((item) => item?.status === 12 || item?.status === 14)
          );
        } else {
          toast.warning('Lỗi xác thực!');
        }
      })
      .catch((err) => toast.error('Có lỗi xảy ra!'));
  };

  return (
    <>
      <Dialog open={open} fullWidth maxWidth={'lg'}>
        <DialogTitle>
          <Box className="icon-close" onClick={handleClose}>
            <IconButton color="error">
              <CloseIcon />
            </IconButton>
          </Box>
        </DialogTitle>
        <DialogContent style={{ padding: '30px 20px 0' }}>
          <MaterialTable
            title="Danh sách hồ sơ nhân viên"
            columns={columns}
            data={listEmployee}
            options={{
              sorting: false,
              draggable: false,
              maxBodyHeight: '40vh',
              pageSize: 10,
              pageSizeOptions: [10, 20, 50],
              headerStyle: {
                textAlign: 'center',
                backgroundColor: colorTable.HEADER,
                color: colorTable.TEXTHEADER,
              },
              rowStyle: {
                backgroundColor: colorTable.ROW,
                color: colorTable.TEXTROW,
              },
            }}
            localization={{
              toolbar: {
                searchTooltip: 'Tìm kiếm',
                searchPlaceholder: 'Tìm kiếm',
              },
              pagination: {
                labelDisplayedRows: '{from}-{to} của {count}',
                labelRowsSelect: 'hàng',
                labelRowsPerPage: 'Số hàng mỗi trang:',
                firstAriaLabel: 'Trang đầu',
                firstTooltip: 'Trang đầu',
                previousAriaLabel: 'Trang trước',
                previousTooltip: 'Trang trước',
                nextAriaLabel: 'Trang sau',
                nextTooltip: 'Trang sau',
                lastAriaLabel: 'Trang cuối',
                lastTooltip: 'Trang cuối',
              },
              body: { emptyDataSourceMessage: 'Không có bản ghi nào' },
            }}
          />
        </DialogContent>
        <DialogActions>
          <Button variant="contained" color="secondary" onClick={handleClose}>
            Hủy
          </Button>
        </DialogActions>
      </Dialog>
      {shouldOpenCommendationAndDisciplineDialog && (
        <CommendationAndDisciplineDialog
          open={shouldOpenCommendationAndDisciplineDialog}
          handleCloseDialog={() => setShouldOpenCommendationAndDisciplineDialog(false)}
          handleClose={handleClose}
          employee={item}
        />
      )}
    </>
  );
}
