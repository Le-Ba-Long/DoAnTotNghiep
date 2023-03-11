import React, { useEffect, useState } from 'react';
import { Box, Button } from '@mui/material';
import { Breadcrumb } from 'app/components';
import MaterialTable from 'material-table';
import {
  getPersonnelChangeReport,
  getMonthlyEmployeeCountReport,
  getEmployeeAllocationRatioByDepartment,
} from './StatisticReportService';
import IconButton from '@mui/material/IconButton';
import RemoveRedEyeIcon from '@mui/icons-material/RemoveRedEye';
import { toast } from 'react-toastify';
import LoopIcon from '@mui/icons-material/Loop';
import { colorTable } from 'app/constant';

export default function StatisticReport() {
  const [listPersonnelChangeReport, setListPersonnelChangeReport] = useState([]);
  const [listEmployeeAllocationRatioByDepartment, setListEmployeeAllocationRatioByDepartment] =
    useState([]);
  const [loading1, setLoading1] = useState(false);
  const [loading2, setLoading2] = useState(false);

  const columns1 = [
    {
      title: 'Loại',
      field: 'name',
      render: (rowData) => rowData.name,
      cellStyle: {
        width: '5%',
        textAlign: 'center',
      },
    },
    {
      title: 'Tháng 1',
      field: 'thang1',
      render: (rowData) => rowData?.thang1,
      cellStyle: {
        width: '3%',
        textAlign: 'center',
      },
    },
    {
      title: 'Tháng 2',
      field: 'thang2',
      render: (rowData) => rowData?.thang2,
      cellStyle: {
        width: '3%',
        textAlign: 'center',
      },
      headerStyle: {
        textAlign: 'center',
      },
    },
    {
      title: 'Tháng 3',
      field: 'thang3',
      render: (rowData) => rowData?.thang3,
      cellStyle: {
        width: '3%',
        textAlign: 'center',
      },
      headerStyle: {
        textAlign: 'center',
      },
    },
    {
      title: 'Tháng 4',
      field: 'thang4',
      render: (rowData) => rowData?.thang4,
      cellStyle: {
        width: '3%',
        textAlign: 'center',
      },
      headerStyle: {
        textAlign: 'center',
      },
    },
    {
      title: 'Tháng 5',
      field: 'thang5',
      render: (rowData) => rowData?.thang5,
      cellStyle: {
        width: '3%',
        textAlign: 'center',
      },
      headerStyle: {
        textAlign: 'center',
      },
    },
    {
      title: 'Tháng 6',
      field: 'thang6',
      render: (rowData) => rowData?.thang6,
      cellStyle: {
        width: '3%',
        textAlign: 'center',
      },
      headerStyle: {
        textAlign: 'center',
      },
    },
    {
      title: 'Tháng 7',
      field: 'thang7',
      render: (rowData) => rowData?.thang7,
      cellStyle: {
        width: '3%',
        textAlign: 'center',
      },
      headerStyle: {
        textAlign: 'center',
      },
    },
    {
      title: 'Tháng 8',
      field: 'thang8',
      render: (rowData) => rowData?.thang8,
      cellStyle: {
        width: '3%',
        textAlign: 'center',
      },
      headerStyle: {
        textAlign: 'center',
      },
    },
    {
      title: 'Tháng 9',
      field: 'thang9',
      render: (rowData) => rowData?.thang9,
      cellStyle: {
        width: '3%',
        textAlign: 'center',
      },
      headerStyle: {
        textAlign: 'center',
      },
    },
    {
      title: 'Tháng 10',
      field: 'thang10',
      render: (rowData) => rowData?.thang10,
      cellStyle: {
        width: '3%',
        textAlign: 'center',
      },
      headerStyle: {
        textAlign: 'center',
      },
    },
    {
      title: 'Tháng 11',
      field: 'thang11',
      render: (rowData) => rowData?.thang11,
      cellStyle: {
        width: '3%',
        textAlign: 'center',
      },
      headerStyle: {
        textAlign: 'center',
      },
    },
    {
      title: 'Tháng 12',
      field: 'thang12',
      render: (rowData) => rowData?.thang12,
      cellStyle: {
        width: '3%',
        textAlign: 'center',
      },
      headerStyle: {
        textAlign: 'center',
      },
    },
  ];

  const columns2 = [
    {
      title: 'STT',
      field: 'stt',
      render: (rowData) => rowData.tableData.id + 1,
      cellStyle: {
        width: '5%',
        textAlign: 'center',
      },
    },
    {
      title: 'Tên phòng ban',
      field: 'name',
      render: (rowData) => rowData.name,
      headerStyle: {
        textAlign: 'left',
      },
      cellStyle: {
        width: '20%',
        textAlign: 'left',
      },
    },
    {
      title: 'Số lượng',
      field: 'count',
      render: (rowData) => rowData.count,
      cellStyle: {
        width: '15%',
        textAlign: 'center',
      },
    },
  ];

  useEffect(() => {
    setLoading1(true);
    setLoading2(true);
    updatePageData1();
  }, []);

  useEffect(() => {
    if (loading1) {
      updatePageData1();
    }
  }, [loading1]);

  useEffect(() => {
    if (loading2) {
      updatePageData2();
    }
  }, [loading2]);

  const updatePageData1 = () => {
    getPersonnelChangeReport()
      .then((res) => {
        if (res.data.statusCode === 200) {
          setLoading1(false);
          const newArray = res.data?.data.map((item) => {
            const obj = {
              name: item.name,
            };
            item.value.forEach((val, i) => {
              obj[`thang${i + 1}`] = val;
            });
            return obj;
          });
          setListPersonnelChangeReport(newArray);
        } else {
          setLoading1(false);
          toast.warning('Lỗi xác thực!');
        }
      })
      .catch((err) => {
        toast.error('Có lỗi xảy ra!');
        setLoading1(false);
      });
  };

  const updatePageData2 = () => {
    getEmployeeAllocationRatioByDepartment()
      .then((res) => {
        if (res.data.statusCode === 200) {
          setLoading2(false);
          setListEmployeeAllocationRatioByDepartment(res.data.data);
        } else {
          setLoading2(false);
          toast.warning('Lỗi xác thực!');
        }
      })
      .catch((err) => {
        toast.error('Có lỗi xảy ra!');
        setLoading2(false);
      });
  };

  return (
    <>
      <Box style={{ margin: 20 }}>
        <Breadcrumb
          routeSegments={[{ name: 'Quản lý', path: '/manage' }, { name: 'Báo cáo thống kê' }]}
        />
        <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
          <IconButton
            color="primary"
            onClick={() => {
              setLoading1(true);
            }}
          >
            <LoopIcon />
          </IconButton>
        </div>
        <div>
          <MaterialTable
            title="Biến Động Nhân Sự Năm 2023"
            columns={columns1}
            data={listPersonnelChangeReport}
            options={{
              doubleHorizontalScroll: false,
              sorting: false,
              search: false,
              paging: false,
              pageSize: 2,
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
            isLoading={loading1}
            localization={{
              body: { emptyDataSourceMessage: 'Không có bản ghi nào' },
            }}
          />
        </div>
        <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
          <IconButton
            color="primary"
            onClick={() => {
              setLoading2(true);
            }}
          >
            <LoopIcon />
          </IconButton>
        </div>
        <div>
          <MaterialTable
            title="Nhân Sự Phân Bổ Theo Các Phòng Ban Năm 2023"
            columns={columns2}
            data={listEmployeeAllocationRatioByDepartment}
            options={{
              doubleHorizontalScroll: false,
              sorting: false,
              search: false,
              paging: false,
              pageSize: 2,
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
            isLoading={loading2}
            localization={{
              body: { emptyDataSourceMessage: 'Không có bản ghi nào' },
            }}
          />
        </div>
      </Box>
    </>
  );
}
