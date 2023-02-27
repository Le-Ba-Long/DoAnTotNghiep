import React, { useEffect, useState } from "react";
import { Box, Button } from "@mui/material";
import { Breadcrumb } from "app/components";
import MaterialTable from "material-table";
import { getListEmployee } from "./ApproveEmployeeService";
import IconButton from "@mui/material/IconButton";
import RemoveRedEyeIcon from "@mui/icons-material/RemoveRedEye";
import { toast } from "react-toastify";
import { checkStatus } from "app/constant";
import EmployeeView from "./EmployeeView";
import LoopIcon from "@mui/icons-material/Loop";

export default function ApproveEmployee() {
  const [listEmployee, setListEmployee] = useState([]);
  const [shouldOpenViewDialog, setShouldOpenViewDialog] = useState(false);
  const [item, setItem] = useState({});
  const [loading, setLoading] = useState(false);

  const columns = [
    {
      title: "STT",
      field: "STT",
      render: (rowData) => rowData.tableData.id + 1,
      cellStyle: {
        width: "3%",
        textAlign: "center",
      },
    },
    {
      title: "Thao tác",
      field: "action",
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
        </>
      ),
      cellStyle: {
        width: "10%",
        textAlign: "center",
      },
    },
    {
      title: "Mã hồ sơ",
      field: "code",
      render: (rowData) => rowData?.code,
      cellStyle: {
        width: "10%",
        textAlign: "center",
      },
    },
    {
      title: "Họ và tên",
      field: "fullName",
      render: (rowData) => rowData?.fullName,
      cellStyle: {
        width: "10%",
        textAlign: "left",
      },
      headerStyle: {
        textAlign: "left",
      },
    },
    {
      title: "Email",
      field: "email",
      render: (rowData) => rowData?.email,
      cellStyle: {
        width: "10%",
        textAlign: "left",
      },
      headerStyle: {
        textAlign: "left",
      },
    },
    {
      title: "SĐT",
      field: "phone",
      render: (rowData) => rowData?.phone,
      cellStyle: {
        width: "10%",
        textAlign: "left",
      },
      headerStyle: {
        textAlign: "left",
      },
    },
    {
      title: "Trạng thái",
      field: "status",
      render: (rowData) => {
        let message = checkStatus(rowData.status).message;
        let color = checkStatus(rowData.status).color;
        return <div className={color}>{message}</div>;
      },
      cellStyle: {
        width: "10%",
        textAlign: "left",
      },
      headerStyle: {
        textAlign: "left",
      },
    },
  ];

  useEffect(() => {
    setLoading(true);
    updatePageData();
  }, []);

  const updatePageData = () => {
    getListEmployee()
      .then((res) => {
        if (res.data.statusCode === 200) {
          setLoading(false);
          setListEmployee(
            res.data.data.filter(
              (item) => item.status === 2 && !item?.quitJobDate
            )
          );
        } else {
          setLoading(false);
          toast.warning("Lỗi xác thực!");
        }
      })
      .catch((err) => {
        toast.error("Có lỗi xảy ra!");
        setLoading(false);
      });
  };

  const handleClose = () => {
    setShouldOpenViewDialog(false);
    updatePageData();
    setItem({});
  };
  return (
    <>
      <Box style={{ margin: 20 }}>
        <Breadcrumb
          routeSegments={[
            { name: "Phê duyệt", path: "/leader" },
            { name: "Phê duyệt hồ sơ nhân viên" },
          ]}
        />
        <div style={{ display: "flex", justifyContent: "flex-end" }}>
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
            title="Danh sách hồ sơ nhân viên"
            columns={columns}
            data={listEmployee}
            options={{
              sorting: false,
              maxBodyHeight: "60vh",
              draggable: false,
              pageSize: 10,
              pageSizeOptions: [10, 20, 50],
              headerStyle: {
                textAlign: "center",
              },
            }}
            isLoading={loading}
            localization={{
              toolbar: {
                searchTooltip: "Tìm kiếm",
                searchPlaceholder: "Tìm kiếm",
              },
              pagination: {
                labelDisplayedRows: "{from}-{to} của {count}",
                labelRowsSelect: "hàng",
                labelRowsPerPage: "Số hàng mỗi trang:",
                firstAriaLabel: "Trang đầu",
                firstTooltip: "Trang đầu",
                previousAriaLabel: "Trang trước",
                previousTooltip: "Trang trước",
                nextAriaLabel: "Trang sau",
                nextTooltip: "Trang sau",
                lastAriaLabel: "Trang cuối",
                lastTooltip: "Trang cuối",
              },
              body: { emptyDataSourceMessage: "Không có bản ghi nào" },
            }}
          />
        </div>
        {shouldOpenViewDialog && (
          <EmployeeView
            open={shouldOpenViewDialog}
            item={item}
            handleClose={handleClose}
            setItem={setItem}
          />
        )}
      </Box>
    </>
  );
}
