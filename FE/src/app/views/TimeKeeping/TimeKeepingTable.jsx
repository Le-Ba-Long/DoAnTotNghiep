import React, { useEffect, useState } from 'react';
import { Box, Button } from '@mui/material';
import { Breadcrumb } from 'app/components';
import MaterialTable from 'material-table';
import { getListTimeKeeping } from './TimeKeepingService';
import IconButton from '@mui/material/IconButton';
import { toast } from 'react-toastify';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import CloseIcon from '@mui/icons-material/Close';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import { months } from 'app/constant';
import Autocomplete from '@mui/material/Autocomplete';
import TextField from '@mui/material/TextField';
import Grid from '@mui/material/Grid';
import ConfirmationDialog from '../../components/ConfirmationDialog';
import { deleteListTimeKeeping } from './TimeKeepingService';
import TimeKeepingDialog from './TimeKeepingDialog';

export default function TimeKeepingTable(props) {
  const { open, handleClose, item } = props;
  const [listTimeKeeping, setListTimeKeeping] = useState([]);
  const [timeKeeping, setTimeKeeping] = useState({});
  const [shouldOpenDialog, setShouldOpenDialog] = useState(false);
  const [loading, setLoading] = useState(false);
  const [month, setMonth] = useState('');
  const [year, setYear] = useState('');
  const [shouldOpenConfirmDialog, setShouldOpenConfirmDialog] = useState(false);

  console.log(item);

  useEffect(() => {
    setTimeKeeping({ ...timeKeeping, employee: item });
  }, [shouldOpenDialog]);

  const columns = [
    {
      title: 'STT',
      field: 'STT',
      render: (rowData) => rowData.tableData.id + 1,
      cellStyle: {
        width: '2%',
        textAlign: 'center',
      },
      headerStyle: {
        width: '2%',
        textAlign: 'center',
      },
    },
    {
      title: 'Thao tác',
      field: 'action',
      render: (rowData) => (
        <>
          <IconButton
            color="success"
            onClick={() => {
              setTimeKeeping(rowData);
              setShouldOpenDialog(true);
            }}
          >
            <EditIcon />
          </IconButton>
          <IconButton
            color="error"
            onClick={() => {
              setTimeKeeping(rowData);
              setShouldOpenConfirmDialog(true);
            }}
          >
            <DeleteIcon />
          </IconButton>
        </>
      ),
      cellStyle: {
        width: '5%',
        textAlign: 'center',
      },
      headerStyle: {
        width: '5%',
        textAlign: 'center',
      },
    },
    {
      title: 'Mã chấm công',
      field: 'code',
      render: (rowData) => rowData?.code,
      cellStyle: {
        width: '5%',
        textAlign: 'center',
      },
    },
    {
      title: 'Năm',
      field: 'year',
      render: (rowData) => rowData?.year,
      cellStyle: {
        width: '5%',
        textAlign: 'center',
      },
      headerStyle: {
        width: '5%',
        textAlign: 'center',
      },
    },
    {
      title: 'Tháng',
      field: 'month',
      render: (rowData) => rowData?.month,
      cellStyle: {
        width: '5%',
        textAlign: 'center',
      },
      headerStyle: {
        width: '5%',
        textAlign: 'center',
      },
    },
    {
      title: 'Số ngày đi làm',
      field: 'numberWorkDay',
      render: (rowData) => rowData?.numberWorkDay,
      cellStyle: {
        width: '10%',
        textAlign: 'center',
      },
      headerStyle: {
        width: '10%',
        textAlign: 'center',
      },
    },
    {
      title: 'Số ngày nghỉ (có phép)',
      field: 'numberDayOff',
      render: (rowData) => rowData?.numberDayOff,
      cellStyle: {
        width: '8%',
        textAlign: 'center',
      },
      headerStyle: {
        width: '8%',
        textAlign: 'center',
      },
    },
    {
      title: 'Số ngày nghỉ (không phép)',
      field: 'numberDayUnexcusedLeave',
      render: (rowData) => rowData?.numberDayUnexcusedLeave,
      cellStyle: {
        width: '8%',
        textAlign: 'center',
      },
      headerStyle: {
        width: '8%',
        textAlign: 'center',
      },
    },
    {
      title: 'Số giờ làm thêm',
      field: 'numberOvertimeHours',
      render: (rowData) => rowData?.numberOvertimeHours,
      cellStyle: {
        width: '6%',
        textAlign: 'center',
      },
      headerStyle: {
        width: '6%',
        textAlign: 'center',
      },
    },
  ];

  useEffect(() => {
    setLoading(true);
    updatePageData();
  }, []);

  const updatePageData = () => {
    let searchObject = {};
    searchObject.pageIndex = 1;
    searchObject.pageSize = 1000;
    searchObject.employeeId = item?.id;
    if (month) {
      searchObject.month = month?.value;
    }

    if (year) {
      searchObject.year = Number(year);
    }

    getListTimeKeeping(searchObject)
      .then((res) => {
        if (res.data.statusCode === 200) {
          setLoading(false);
          setListTimeKeeping(res.data?.data?.content);
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

  const handleDelete = () => {
    deleteListTimeKeeping(timeKeeping?.id)
      .then((res) => {
        if (res.data.statusCode === 200) {
          toast.success('Xóa bản ghi chấm công thành công');
          setTimeKeeping({});
          updatePageData();
        } else {
          toast.warning('Lỗi xác thực!');
        }
        setShouldOpenConfirmDialog(false);
      })
      .catch((err) => {
        toast.error('Có lỗi xảy ra!');
        setShouldOpenConfirmDialog(false);
      });
  };

  return (
    <>
      <Dialog open={open} fullWidth maxWidth={'lg'}>
        <DialogTitle style={{ marginBlockEnd: 0, padding: '16px 24px 0' }}>
          Danh sách chấm công của nhân viên {item?.fullName}
          <Box className="icon-close" onClick={handleClose}>
            <IconButton color="error">
              <CloseIcon />
            </IconButton>
          </Box>
        </DialogTitle>
        <DialogContent>
          <Box style={{ margin: 20 }}>
            <Grid container spacing={1} style={{ margin: '20px 0' }}>
              <Grid item xs={7}>
                <Button
                  variant="contained"
                  color="primary"
                  size="medium"
                  style={{ padding: '5px 20px' }}
                  onClick={() => setShouldOpenDialog(true)}
                >
                  Thêm
                </Button>
              </Grid>
              <Grid item xs={2}>
                <Autocomplete
                  options={months}
                  getOptionLabel={(option) => option?.name || ''}
                  value={month}
                  onChange={(event, newValue) => setMonth(newValue)}
                  componentsProps={{ paper: { elevation: 8 } }}
                  fullWidth
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      variant="outlined"
                      fullWidth
                      label="Tháng"
                      size="small"
                    />
                  )}
                />
              </Grid>
              <Grid item xs={2}>
                <TextField
                  label="Năm"
                  variant="outlined"
                  type="number"
                  fullWidth
                  size="small"
                  name="address"
                  value={year}
                  onChange={(event) => setYear(event.target.value)}
                />
              </Grid>
              <Grid item xs={1}>
                <Button
                  variant="contained"
                  color="primary"
                  size="medium"
                  style={{ padding: '5px 20px' }}
                  onClick={updatePageData}
                >
                  Lọc
                </Button>
              </Grid>
            </Grid>
            <MaterialTable
              title="Danh sách chấm công"
              columns={columns}
              data={listTimeKeeping}
              options={{
                sorting: false,
                maxBodyHeight: '40vh',
                pageSize: 10,
                pageSizeOptions: [10, 20, 50],
                draggable: false,
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
          </Box>
        </DialogContent>
        <DialogActions>
          <Button variant="contained" color="secondary" onClick={handleClose}>
            Đóng
          </Button>
        </DialogActions>
      </Dialog>
      {shouldOpenConfirmDialog && (
        <ConfirmationDialog
          title="Xác nhận"
          text="Bạn có muốn xóa kế hoạch này?"
          open={shouldOpenConfirmDialog}
          onConfirmDialogClose={() => setShouldOpenConfirmDialog(false)}
          onYesClick={handleDelete}
          Yes="Đồng ý"
          No="Hủy"
        />
      )}
      {shouldOpenDialog && (
        <TimeKeepingDialog
          open={shouldOpenDialog}
          hanldeCloseDialog={() => {
            setShouldOpenDialog(false);
            setTimeKeeping({});
          }}
          handleClose={handleClose}
          timeKeeping={timeKeeping}
          setTimeKeeping={setTimeKeeping}
          item={item}
        />
      )}
    </>
  );
}
