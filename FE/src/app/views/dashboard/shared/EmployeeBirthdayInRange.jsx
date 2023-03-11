import React, { useEffect, useState } from 'react';
import MaterialTable from 'material-table';
import moment from 'moment';
import { colorTable } from 'app/constant';

export default function EmployeeBirthdayInRange(props) {
  const { listEmployeeBirthdayInRange, loading } = props;

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
        width: '7%',
        textAlign: 'left',
      },
      headerStyle: {
        textAlign: 'left',
      },
    },
    {
      title: 'Ngày sinh',
      field: 'dateOfBirth',
      render: (rowData) => moment(rowData?.dateOfBirth).format('DD/MM/YYYY'),
      cellStyle: {
        width: '5%',
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
        width: '5%',
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
        data={listEmployeeBirthdayInRange}
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
