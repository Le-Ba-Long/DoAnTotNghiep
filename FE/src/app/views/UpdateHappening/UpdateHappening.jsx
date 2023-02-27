import React, { useEffect, useState } from 'react';
import { Box, Button } from '@mui/material';
import { Breadcrumb } from 'app/components';
import MaterialTable from 'material-table';
import { getListEmployee } from './UpdateHappeningService';
import IconButton from '@mui/material/IconButton';
import RemoveRedEyeIcon from '@mui/icons-material/RemoveRedEye';
import { toast } from 'react-toastify';
import { checkStatus, trangThaiNhanVienCNDB } from 'app/constant';
import UpdateHappeningDialog from './UpdateHappeningDialog';
import LoopIcon from '@mui/icons-material/Loop';
import Grid from '@mui/material/Grid';
import Autocomplete from '@mui/material/Autocomplete';
import TextField from '@mui/material/TextField';

export default function UpdateHappening() {
  const [listEmployee, setListEmployee] = useState([]);
  const [shouldOpenConfirmDialog, setShouldOpenConfirmDialog] = useState(false);
  const [shouldOpenCandidateTable, setShouldOpenCandidateTable] = useState(false);
  const [item, setItem] = useState({});
  const [shouldOpenDialog, setShouldOpenDialog] = useState(false);
  const [shouldOpenViewDialog, setShouldOpenViewDialog] = useState(false);
  const [shouldOpenRequestDialog, setShouldOpenRequestDialog] = useState(false);
  const [loading, setLoading] = useState(false);
  const [status, setStatus] = useState({});

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
              setShouldOpenDialog(true);
              setItem(rowData);
            }}
            disabled={rowData?.status === 15}
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
    setLoading(true);
    updatePageData();
  }, [status]);

  const updatePageData = () => {
    getListEmployee()
      .then((res) => {
        if (res.data.statusCode === 200) {
          setLoading(false);
          status?.value
            ? setListEmployee(res.data.data.filter((item) => item.status === status?.value))
            : setListEmployee(
                res.data.data.filter(
                  (item) =>
                    item?.status === 12 ||
                    item?.status === 14 ||
                    item?.status === 15 ||
                    item?.status === 16
                )
              );
        } else {
          setLoading(false);
          toast.warning('Lỗi xác thực!');
        }
      })
      .catch((err) => {
        toast.error('Có lỗi xảy ra!');
        setLoading(false);
      });
  };

  const handleClose = () => {
    setShouldOpenConfirmDialog(false);
    setShouldOpenCandidateTable(false);
    setShouldOpenDialog(false);
    setShouldOpenViewDialog(false);
    setShouldOpenRequestDialog(false);
    updatePageData();
    setItem({});
  };

  return (
    <>
      <Box style={{ margin: 20 }}>
        <Breadcrumb
          routeSegments={[{ name: 'Quản lý ', path: '/manage' }, { name: 'Cập nhật diễn biến' }]}
        />
        <Grid container spacing={1} justifyContent="space-between">
          <Grid item xs={9}></Grid>
          <Grid item container xs={3}>
            <Grid item xs={10}>
              <Autocomplete
                options={trangThaiNhanVienCNDB}
                getOptionLabel={(option) => option?.name || ''}
                value={status}
                onChange={(event, newValue) => setStatus(newValue)}
                componentsProps={{ paper: { elevation: 8 } }}
                fullWidth
                renderInput={(params) => (
                  <TextField
                    {...params}
                    variant="standard"
                    fullWidth
                    label="Lọc theo trạng thái"
                    size="small"
                  />
                )}
              />
            </Grid>
            <Grid item xs={2}>
              <IconButton
                color="primary"
                onClick={() => {
                  updatePageData();
                }}
              >
                <LoopIcon />
              </IconButton>
            </Grid>
          </Grid>
        </Grid>
        <div style={{ marginTop: 10 }}>
          <MaterialTable
            title="Danh sách hồ sơ nhân viên"
            columns={columns}
            data={listEmployee}
            options={{
              sorting: false,
              draggable: false,
              maxBodyHeight: '60vh',
              pageSize: 10,
              pageSizeOptions: [10, 20, 50],
              headerStyle: {
                textAlign: 'center',
              },
            }}
            isLoading={loading}
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
        </div>
      </Box>
      {shouldOpenDialog && (
        <UpdateHappeningDialog open={shouldOpenDialog} handleClose={handleClose} item={item} />
      )}
    </>
  );
}
