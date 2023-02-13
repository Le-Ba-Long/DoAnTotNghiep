import React, { useEffect, useState } from 'react';
import { Box, Button } from '@mui/material';
import { Breadcrumb } from 'app/components';
import MaterialTable from 'material-table';
import { getListRecruit, deleteRecruit } from './RecruitService';
import IconButton from '@mui/material/IconButton';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import RemoveRedEyeIcon from '@mui/icons-material/RemoveRedEye';
import { toast } from 'react-toastify';
import RecruitDialog from './RecruitDialog';
import ConfirmationDialog from '../../components/ConfirmationDialog';
import RecruitView from './RecruitView';
import { checkStatus } from 'app/constant';
import Grid from '@mui/material/Grid';
import LoopIcon from '@mui/icons-material/Loop';
import Autocomplete from '@mui/material/Autocomplete';
import TextField from '@mui/material/TextField';
import { trangThaiKeHoanhTD } from 'app/constant';
import './Recruit.scss';

export default function Recruit() {
  const [listRecruit, setListRecruit] = useState([]);
  const [shouldOpenDialog, setShouldOpenDialog] = useState(false);
  const [shouldOpenViewDialog, setShouldOpenViewDialog] = useState(false);
  const [shouldOpenConfirmDialog, setShouldOpenConfirmDialog] = useState(false);
  const [item, setItem] = useState({});
  const [loading, setLoading] = useState(false);
  const [status, setStatus] = useState('');

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
              setShouldOpenViewDialog(true);
              setItem(rowData);
            }}
          >
            <RemoveRedEyeIcon />
          </IconButton>
          <IconButton
            color="success"
            onClick={() => {
              setShouldOpenDialog(true);
              setItem(rowData);
            }}
            disabled={rowData?.status === 6 || rowData?.status === 4 || rowData?.status === 5}
          >
            <EditIcon />
          </IconButton>

          <IconButton
            color="error"
            onClick={() => {
              setShouldOpenConfirmDialog(true);
              setItem(rowData);
            }}
            disabled={
              rowData?.status === 6 ||
              rowData?.status === 4 ||
              rowData?.status === 5 ||
              rowData?.status === 10
            }
          >
            <DeleteIcon />
          </IconButton>
        </>
      ),
      cellStyle: {
        width: '10%',
        textAlign: 'center',
      },
    },
    {
      title: 'Tên kế hoạch',
      field: 'name',
      render: (rowData) => rowData.titleRecruit,
      cellStyle: {
        width: '10%',
        textAlign: 'left',
      },
      headerStyle: {
        textAlign: 'left',
      },
    },
    {
      title: 'Số lượng',
      field: 'quantity',
      render: (rowData) => rowData.quantity,
      cellStyle: {
        width: '5%',
        textAlign: 'center',
      },
      headerStyle: {
        textAlign: 'center',
      },
    },
    {
      title: 'Các kênh tuyển dụng chính',
      field: 'description',
      render: (rowData) => rowData.recruitmentChannel,
      headerStyle: {
        textAlign: 'center',
      },
      cellStyle: {
        width: '20%',
        textOverflow: 'ellipsis',
        textAlign: 'center',

        whiteSpace: 'nowrap',
        overflow: 'hidden',
        maxWidth: 100,
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
    getListRecruit()
      .then((res) => {
        if (res.data.statusCode === 200) {
          setLoading(false);
          status?.value
            ? setListRecruit(res.data.data.filter((item) => item.status === status?.value))
            : setListRecruit(
                res.data.data.filter((item) => item.status !== 3 && item.status !== 2)
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
  console.log(listRecruit);
  const handleClose = () => {
    setShouldOpenDialog(false);
    setShouldOpenConfirmDialog(false);
    setShouldOpenViewDialog(false);
    setItem({});
    updatePageData();
  };

  const handleDelete = () => {
    deleteRecruit(item.id).then((res) => {
      if (res.data.statusCode === 200) {
        toast.success('Xóa thành công');
      } else {
        toast.warning(res.data.message);
      }
      handleClose();
    });
  };

  return (
    <>
      <Box style={{ margin: 20 }}>
        <Breadcrumb
          routeSegments={[{ name: 'Tuyển dụng', path: '/plan' }, { name: 'Kế hoạch tuyển dụng' }]}
        />
        <Grid container spacing={1} justifyContent="space-between">
          <Grid item xs={9}>
            <Button
              variant="contained"
              color="primary"
              size="medium"
              style={{ margin: '20px 0', padding: '5px 20px' }}
              onClick={() => setShouldOpenDialog(true)}
            >
              Thêm
            </Button>
          </Grid>
          <Grid item container xs={3}>
            <Grid item xs={10}>
              <Autocomplete
                options={trangThaiKeHoanhTD}
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
        <MaterialTable
          title="Danh sách kế hoạch"
          columns={columns}
          data={listRecruit}
          options={{
            sorting: false,
            maxBodyHeight: '60vh',
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
      {shouldOpenDialog && (
        <RecruitDialog open={shouldOpenDialog} handleClose={handleClose} item={item} />
      )}
      {shouldOpenViewDialog && (
        <RecruitView open={shouldOpenViewDialog} handleClose={handleClose} item={item} />
      )}
      {shouldOpenConfirmDialog && (
        <ConfirmationDialog
          title="Xác nhận"
          text="Bạn có muốn xóa kế hoạch này?"
          open={shouldOpenConfirmDialog}
          onConfirmDialogClose={handleClose}
          onYesClick={handleDelete}
          Yes="Đồng ý"
          No="Hủy"
        />
      )}
    </>
  );
}
