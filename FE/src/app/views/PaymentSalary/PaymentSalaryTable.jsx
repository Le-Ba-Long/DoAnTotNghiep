import React, { useEffect, useState } from 'react';
import { Box, Button } from '@mui/material';
import MaterialTable from 'material-table';
import { getListSalary } from './PaymentSalaryService';
import IconButton from '@mui/material/IconButton';
import { toast } from 'react-toastify';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import CloseIcon from '@mui/icons-material/Close';
import { months } from 'app/constant';
import Autocomplete from '@mui/material/Autocomplete';
import TextField from '@mui/material/TextField';
import Grid from '@mui/material/Grid';
import RemoveRedEyeIcon from '@mui/icons-material/RemoveRedEye';
import { Span } from 'app/components/Typography';

export default function PaymentSalary(props) {
  const { open, handleClose, item } = props;
  const [listPaymentSalary, setListPaymentSalary] = useState([]);
  const [shouldOpenDialog, setShouldOpenDialog] = useState(false);
  // const [paymentSalary, setPaymentSalary] = useState({});
  const [month, setMonth] = useState('');
  const [year, setYear] = useState('');
  const [loading, setLoading] = useState(false);

  const columns = [
    {
      title: 'STT',
      field: 'STT',
      render: (rowData) => rowData.tableData.id + 1,
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
      render: (rowData) => rowData?.timeKeeping?.code,
      cellStyle: {
        width: '7%',
        textAlign: 'center',
      },
      headerStyle: {
        width: '7%',
        textAlign: 'center',
      },
    },
    {
      title: 'Năm',
      field: 'year',
      render: (rowData) => rowData?.year,
      cellStyle: {
        width: '4%',
        textAlign: 'center',
      },
      headerStyle: {
        width: '4%',
        textAlign: 'center',
      },
    },
    {
      title: 'Tháng',
      field: 'month',
      render: (rowData) => rowData?.month,
      cellStyle: {
        width: '4%',
        textAlign: 'center',
      },
      headerStyle: {
        width: '4%',
        textAlign: 'center',
      },
    },
    {
      title: 'Tiền chuyên cần',
      field: 'fullTimeSalary',
      render: (rowData) =>
        rowData?.fullTimeSalary.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ','),
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
      title: 'Tiền trợ cấp',
      field: 'numberDayOff',
      render: (rowData) =>
        rowData?.transportationAndLunchAllowance
          .toString()
          .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ','),
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
      title: 'Tiền BHXH(8%)',
      field: 'socialInsuranceCosts',
      render: (rowData) =>
        Math.round(rowData?.socialInsuranceCosts)
          .toString()
          .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ','),
      cellStyle: {
        width: '11%',
        textAlign: 'center',
      },
      headerStyle: {
        width: '11%',
        textAlign: 'center',
      },
    },
    {
      title: 'Tiền BHYT(1.5%)',
      field: 'healthInsurancePremium',
      render: (rowData) =>
        Math.round(rowData?.healthInsurancePremium)
          .toString()
          .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ','),
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
      title: 'Tiền thuế TNCN',
      field: 'personalIncomeTax',
      render: (rowData) =>
        Math.round(rowData?.personalIncomeTax)
          .toString()
          .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ','),
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
      title: 'Giá trị gia tăng(TNCN)',
      field: 'valueAddedWithEachSalary',
      render: (rowData) => <span>{rowData?.valueAddedWithEachSalary * 100}%</span>,
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
      title: 'Lương thực lĩnh',
      field: 'netWage',
      render: (rowData) =>
        Math.round(rowData?.netWage)
          .toString()
          .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ','),
      cellStyle: {
        width: '10%',
        textAlign: 'center',
      },
      headerStyle: {
        width: '10%',
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
    getListSalary(searchObject)
      .then((res) => {
        if (res.data.statusCode === 200) {
          setLoading(false);
          setListPaymentSalary(res.data.data.content);
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

  return (
    <>
      <Dialog open={open} fullWidth maxWidth={'xl'}>
        <DialogTitle style={{ marginBlockEnd: 0, padding: '16px 24px 0' }}>
          Danh sách lương của nhân viên {item?.fullName}
          <Box className="icon-close" onClick={handleClose}>
            <IconButton color="error">
              <CloseIcon />
            </IconButton>
          </Box>
        </DialogTitle>
        <DialogContent>
          <Box style={{ margin: 20 }}>
            <Grid container spacing={1} style={{ margin: '20px 0' }}>
              <Grid item xs={7}></Grid>
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
            <div style={{ width: '100%' }}>
              <MaterialTable
                title="Danh sách lương"
                columns={columns}
                data={listPaymentSalary}
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
            </div>
          </Box>
        </DialogContent>
        <DialogActions>
          <Button variant="contained" color="secondary" onClick={handleClose}>
            Đóng
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
