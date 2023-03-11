import React, { useEffect, useState } from 'react';
import { Box, Button } from '@mui/material';
import { Breadcrumb } from 'app/components';
import MaterialTable from 'material-table';
import { getListRecruit, deleteRecruit } from './ListRecruitApprovedService';
import IconButton from '@mui/material/IconButton';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import RemoveRedEyeIcon from '@mui/icons-material/RemoveRedEye';
import { toast } from 'react-toastify';
import ConfirmationDialog from '../../components/ConfirmationDialog';
import ListRecruitApprovedView from './ListRecruitApprovedView';
import { checkStatus } from 'app/constant';
import './Recruit.scss';
import LoopIcon from '@mui/icons-material/Loop';
import { colorTable } from 'app/constant';

export default function ListRecruitApproved() {
  const [listRecruit, setListRecruit] = useState([]);
  const [shouldOpenDialog, setShouldOpenDialog] = useState(false);
  const [shouldOpenViewDialog, setShouldOpenViewDialog] = useState(false);
  const [shouldOpenConfirmDialog, setShouldOpenConfirmDialog] = useState(false);
  const [item, setItem] = useState({});
  const [loading, setLoading] = useState(false);

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
        <IconButton
          color="primary"
          onClick={() => {
            setShouldOpenViewDialog(true);
            setItem(rowData);
          }}
        >
          <RemoveRedEyeIcon />
        </IconButton>
      ),
      cellStyle: {
        width: '5%',
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
  }, []);

  const updatePageData = () => {
    getListRecruit()
      .then((res) => {
        if (res.data.statusCode === 200) {
          setLoading(false);
          setListRecruit(res.data.data.filter((item) => item.status === 4 || item.status === 5));
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
    setShouldOpenDialog(false);
    setShouldOpenConfirmDialog(false);
    setShouldOpenViewDialog(false);
    setItem({});
    updatePageData();
  };

  return (
    <>
      <Box style={{ margin: 20 }}>
        <Breadcrumb
          routeSegments={[{ name: 'Tuyển dụng', path: '/plan' }, { name: 'Kế hoạch tuyển dụng' }]}
        />
        <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
          <IconButton
            color="primary"
            onClick={() => {
              updatePageData();
            }}
          >
            <LoopIcon />
          </IconButton>
        </div>
        <div>
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
                backgroundColor: colorTable.HEADER,
                color: colorTable.TEXTHEADER,
              },
              rowStyle: {
                backgroundColor: colorTable.ROW,
                color: colorTable.TEXTROW,
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
      {shouldOpenViewDialog && (
        <ListRecruitApprovedView
          open={shouldOpenViewDialog}
          handleClose={handleClose}
          item={item}
        />
      )}
      {/* {shouldOpenConfirmDialog && (
        <ConfirmationDialog
          title="Xác nhận"
          text="Bạn có muốn xóa kế hoạch này?"
          open={shouldOpenConfirmDialog}
          onConfirmDialogClose={handleClose}
          onYesClick={handleDelete}
          Yes="Đồng ý"
          No="Hủy"
        />
      )} */}
    </>
  );
}
