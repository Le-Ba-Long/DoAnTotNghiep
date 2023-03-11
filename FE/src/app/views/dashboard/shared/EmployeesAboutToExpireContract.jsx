import React, { useEffect, useState } from 'react';
import MaterialTable from 'material-table';
import moment from 'moment';
import { colorTable } from 'app/constant';

export default function EmployeesAboutToExpireContract(props) {
  const { listEmployeesAboutToExpireContract, loading } = props;

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
      title: 'Mã nhân viên',
      field: 'code',
      render: (rowData) => rowData?.code,
      cellStyle: {
        width: '5%',
        textAlign: 'center',
      },
    },
    {
      title: 'Họ và tên',
      field: 'fullName',
      render: (rowData) => rowData?.fullName,
      cellStyle: {
        width: '5%',
        textAlign: 'left',
      },
      headerStyle: {
        textAlign: 'left',
      },
    },
    {
      title: 'Chức vụ',
      field: 'positions',
      render: (rowData) => rowData?.positions[0]?.name,
      cellStyle: {
        width: '4%',
        textAlign: 'left',
      },
      headerStyle: {
        textAlign: 'left',
      },
    },
    {
      title: 'Mã hợp đồng',
      field: 'contract',
      render: (rowData) => rowData?.contract?.code,
      cellStyle: {
        width: '5%',
        textAlign: 'left',
      },
      headerStyle: {
        textAlign: 'left',
      },
    },
    {
      title: 'Ngày hết hạn hợp đồng',
      field: 'phone',
      render: (rowData) => moment(rowData?.contract?.contractEffect).format('DD/MM/YYYY'),
      cellStyle: {
        width: '7%',
        textAlign: 'left',
      },
      headerStyle: {
        textAlign: 'left',
      },
    },
  ];

  return (
    <>
      <MaterialTable
        columns={columns}
        data={listEmployeesAboutToExpireContract}
        options={{
          showTitle: false,
          sorting: false,
          toolbar: false,
          search: false,
          maxBodyHeight: '60vh',
          draggable: false,
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
        isLoading={loading}
        localization={{
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
    </>
  );
}
