import React, { useState, useEffect } from "react";
import { useFormik } from "formik";
import * as Yup from "yup";
import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { MenuItem } from "@mui/material";
import { sex } from "app/constant";
import Autocomplete, { createFilterOptions } from "@mui/material/Autocomplete";
import moment from "moment";
import Checkbox from "@mui/material/Checkbox";
import CheckBoxOutlineBlankIcon from "@mui/icons-material/CheckBoxOutlineBlank";
import CheckBoxIcon from "@mui/icons-material/CheckBox";
import Box from "@mui/material/Box";
import { uploadImage } from "app/constant";
import {
  getListCertificate,
  getListLanguage,
  editEmployee,
} from "./UpdateHappeningService";
import { toast } from "react-toastify";
import Button from "@mui/material/Button";
import AddIcon from "@mui/icons-material/Add";

const icon = <CheckBoxOutlineBlankIcon fontSize="small" />;
const checkedIcon = <CheckBoxIcon fontSize="small" />;
const filterOptions = createFilterOptions({
  // matchFrom: 'start',
  stringify: (option) => option.name,
});

export default function InformationDialog(props) {
  const { item } = props;

  const [listCertificate, setListCertificate] = useState([]);
  const [listLanguage, setListLanguage] = useState([]);

  useEffect(() => {
    getListCertificate()
      .then((res) => {
        if (res.data.statusCode === 200) {
          setListCertificate(res.data.data);
        } else {
          toast.warning("Lỗi xác thực");
        }
      })
      .catch((err) => toast.error("Có lỗi xảy ra"));

    getListLanguage()
      .then((res) => {
        if (res.data.statusCode === 200) {
          setListLanguage(res.data.data);
        } else {
          toast.warning("Lỗi xác thực");
        }
      })
      .catch((err) => toast.error("Có lỗi xảy ra"));
  }, []);

  const formik = useFormik({
    initialValues: {
      code: item?.id ? item?.code : "",
      fullName: item?.id ? item?.fullName : "",
      dateOfBirth: item?.id ? item?.dateOfBirth : null,
      sex: item?.id ? item?.sex : "",
      image: item?.id ? item?.image : "",
      imageName: item?.id ? item?.imageName : "",
      phone: item?.id ? item?.phone : "",
      email: item?.id ? item?.email : "",
      education: item?.id ? item?.education : "",
      major: item?.id ? item?.major : "",
      nation: item?.id ? item?.nation : "",
      religion: item?.id ? item?.religion : "",
      address: item?.id ? item?.address : "",
      numberIdentityCard: item?.id ? item?.numberIdentityCard : "",
      issuedDateIdentityCard: item?.id ? item?.issuedDateIdentityCard : null,
      placeOfGrantIdentityCard: item?.id ? item?.placeOfGrantIdentityCard : "",
      numberMedicalInsurance: item?.id ? item?.numberMedicalInsurance : "",
      issuedDateMedicalInsurance: item?.id
        ? item?.issuedDateMedicalInsurance
        : null,
      placeOfIssueMedicalInsurance: item?.id
        ? item?.placeOfIssueMedicalInsurance
        : "",
      numberSocialInsurance: item?.id ? item?.numberSocialInsurance : "",
      issuedDateSocialInsurance: item?.id
        ? item?.issuedDateSocialInsurance
        : null,
      placeOfIssueSocialInsurance: item?.id
        ? item?.placeOfIssueSocialInsurance
        : "",
      certificate: item?.id ? item?.certificate : null,
      languages: item?.id ? item?.languages : [],
    },
    enableReinitialize: true,
    validateOnChange: false,
    validateOnBlur: false,
    validationSchema: Yup.object({
      code: Yup.string()
        .matches(/^MaNV[0-9]{4}$/, "Mã bằng cấp chưa đúng format VD:(MaNV9999)")
        .required("Vui lòng nhập trường này"),
      fullName: Yup.string().required("Vui lòng nhập trường này"),
      dateOfBirth: Yup.date()
        .nullable()
        .typeError("Sai định dạng ngày!")
        .required("Vui lòng nhập trường này"),
      sex: Yup.string().required("Vui lòng nhập trường này!"),
      email: Yup.string()
        .matches(
          /^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[A-Za-z]+$/,
          "Đây không phải là email!"
        )
        .required("Vui lòng nhập Email!"),
      phone: Yup.string()
        .matches(/(84|0[3|5|7|8|9])+([0-9]{8})\b/, "Số điện thoại không hợp lệ")
        .required("Vui lòng nhập số điện thoại!"),
      education: Yup.string().required("Vui lòng nhập trường này"),
      major: Yup.string().required("Vui lòng nhập trường này"),
      nation: Yup.string().required("Vui lòng nhập trường này"),
      religion: Yup.string().required("Vui lòng nhập trường này"),
      address: Yup.string().required("Vui lòng nhập trường này"),
      numberIdentityCard: Yup.number()
        .nullable()
        .typeError("Số CCCD phải là số!"),
      // .required('Vui lòng nhập trường này'),
      issuedDateIdentityCard: Yup.date()
        .nullable()
        .typeError("Sai định dạng ngày!"),
      // .required('Vui lòng nhập trường này'),
      placeOfGrantIdentityCard: Yup.string().nullable(),
      // .required('Vui lòng nhập trường này'),
      numberMedicalInsurance: Yup.number()
        .nullable()
        .typeError("Số bảo hiểm y tế phải là số!"),
      // .required('Vui lòng nhập trường này'),
      issuedDateMedicalInsurance: Yup.date()
        .nullable()
        .typeError("Sai định dạng ngày!"),
      // .required('Vui lòng nhập trường này'),
      placeOfIssueMedicalInsurance: Yup.string().nullable(),
      // .required('Vui lòng nhập trường này'),
      numberSocialInsurance: Yup.number()
        .nullable()
        .typeError("Số bảo hiểm xã hội phải là số!"),
      // .required('Vui lòng nhập trường này'),
      issuedDateSocialInsurance: Yup.date()
        .nullable()
        .typeError("Sai định dạng ngày!"),
      // .required('Vui lòng nhập trường này'),
      placeOfIssueSocialInsurance: Yup.string().nullable(),
      // .required('Vui lòng nhập trường này'),
      certificate: Yup.object().nullable().required("Vui lòng chọn trường này"),
      languages: Yup.array().nullable().required("Vui lòng chọn trường này"),
    }),
    onSubmit: (values) => {
      values.id = item?.id;
      values.status = item?.status;
      values.department = item?.department;
      values.positions = item?.positions;
      handleSave(values);
    },
  });

  const handleSave = (values) => {
    editEmployee(values)
      .then((res) => {
        if (res.data.statusCode === 200) {
          toast.success("Sửa thông tin nhân viên thành công");
        } else {
          toast.warning(res.data.message);
        }
      })
      .catch((err) => toast.error("Có lỗi xảy ra!"));
  };

  return (
    <form
      onSubmit={(event) => {
        event.preventDefault();
        formik.handleSubmit();
      }}
      onError={(errors) => console.log(errors)}
    >
      <Grid container spacing={2} style={{ marginTop: 5 }}>
        <Grid container item xs={12} md={12} spacing={2}>
          <Grid item xs={3} md={3}>
            <TextField
              label="Mã nhân viên"
              variant="outlined"
              fullWidth
              name="code"
              value={formik.values?.code}
              onChange={formik.handleChange}
              error={formik.errors.code && formik.touched.code}
              helperText={formik.errors.code}
            />
          </Grid>
          <Grid item xs={3} md={3}>
            <TextField
              label="Họ và Tên"
              variant="outlined"
              fullWidth
              name="fullName"
              value={formik.values?.fullName}
              onChange={formik.handleChange}
              error={formik.errors.fullName && formik.touched.fullName}
              helperText={formik.errors.fullName}
            />
          </Grid>
          <Grid item xs={3} md={3}>
            <LocalizationProvider dateAdapter={AdapterDayjs} fullWidth>
              <DatePicker
                label="Ngày sinh"
                name="dateOfBirth"
                inputFormat="DD/MM/YYYY"
                value={formik.values?.dateOfBirth}
                onChange={(value) => {
                  if (value) {
                    formik.setFieldValue("dateOfBirth", new Date(value));
                  }
                }}
                renderInput={(params) => {
                  return (
                    <TextField
                      {...params}
                      format="DD/MM/YYYY"
                      type="date"
                      fullWidth
                      variant="outlined"
                      error={
                        formik.errors.dateOfBirth && formik.touched.dateOfBirth
                      }
                      helperText={formik.errors.dateOfBirth}
                    />
                  );
                }}
              />
            </LocalizationProvider>
          </Grid>
          <Grid item xs={3} md={3}>
            <TextField
              label="Ảnh ứng viên"
              type="text"
              style={{ width: "82%" }}
              name="imageName"
              value={formik.values?.imageName}
              InputLabelProps={{ shrink: true }}
              inputProps={{ accept: "image/*" }}
            />
            <label for="file-upload" className="custom-file-upload">
              <AddIcon />
            </label>
            <input
              type="file"
              id="file-upload"
              className="filename"
              onChange={(event) => {
                console.log(event.currentTarget.files[0]);
                uploadImage(event.currentTarget.files[0]).then((res) => {
                  formik.setFieldValue("image", res?.data?.fileDownloadUri);
                  formik.setFieldValue("imageName", res?.data?.fileName);
                });
              }}
            ></input>
          </Grid>
        </Grid>
        <Grid container item xs={12} md={12} spacing={2}>
          <Grid item xs={3} md={3}>
            <TextField
              label="Giới tính"
              variant="outlined"
              select
              fullWidth
              name="sex"
              value={formik.values?.sex}
              onChange={formik.handleChange}
              error={formik.errors.sex && formik.touched.sex}
              helperText={formik.errors.sex}
            >
              {sex.map((item) => (
                <MenuItem value={item.name}>{item.name}</MenuItem>
              ))}
            </TextField>
          </Grid>
          <Grid item xs={3} md={3}>
            <TextField
              label="Số điện thoại"
              variant="outlined"
              fullWidth
              name="phone"
              value={formik.values?.phone}
              onChange={formik.handleChange}
              error={formik.errors.phone && formik.touched.phone}
              helperText={formik.errors.phone}
            />
          </Grid>
          <Grid item xs={3} md={3}>
            <TextField
              label="Email"
              variant="outlined"
              fullWidth
              name="email"
              value={formik.values?.email}
              onChange={formik.handleChange}
              error={formik.errors.email && formik.touched.email}
              helperText={formik.errors.email}
            />
          </Grid>
          <Grid item xs={3} md={3}>
            <TextField
              label="Trình độ học vấn"
              variant="outlined"
              fullWidth
              name="education"
              value={formik.values?.education}
              onChange={formik.handleChange}
              error={formik.errors.education && formik.touched.education}
              helperText={formik.errors.education}
            />
          </Grid>
          <Grid item xs={4} md={4}>
            <TextField
              label="Chuyên ngành"
              variant="outlined"
              fullWidth
              name="major"
              value={formik.values?.major}
              onChange={formik.handleChange}
              error={formik.errors.major && formik.touched.major}
              helperText={formik.errors.major}
            />
          </Grid>
          <Grid item xs={4} md={4}>
            <TextField
              label="Số CCCD"
              variant="outlined"
              fullWidth
              name="numberIdentityCard"
              value={formik.values?.numberIdentityCard}
              onChange={formik.handleChange}
              error={
                formik.errors.numberIdentityCard &&
                formik.touched.numberIdentityCard
              }
              helperText={formik.errors.numberIdentityCard}
            />
          </Grid>
          <Grid item xs={4} md={4}>
            <LocalizationProvider dateAdapter={AdapterDayjs} fullWidth>
              <DatePicker
                label="Ngày cấp"
                name="issuedDateIdentityCard"
                inputFormat="DD/MM/YYYY"
                value={formik.values?.issuedDateIdentityCard || null}
                onChange={(value) => {
                  if (value) {
                    formik.setFieldValue(
                      "issuedDateIdentityCard",
                      new Date(value)
                    );
                  }
                }}
                renderInput={(params) => {
                  return (
                    <TextField
                      {...params}
                      format="DD/MM/YYYY"
                      type="date"
                      fullWidth
                      variant="outlined"
                      error={
                        formik.errors.issuedDateIdentityCard &&
                        formik.touched.issuedDateIdentityCard
                      }
                      helperText={formik.errors.issuedDateIdentityCard}
                    />
                  );
                }}
              />
            </LocalizationProvider>
          </Grid>
        </Grid>
        <Grid container item xs={12} md={12} spacing={2}>
          <Grid item xs={4} md={4}>
            <TextField
              label="Nơi cấp"
              variant="outlined"
              fullWidth
              name="placeOfGrantIdentityCard"
              value={formik.values?.placeOfGrantIdentityCard}
              onChange={formik.handleChange}
              error={
                formik.errors.placeOfGrantIdentityCard &&
                formik.touched.placeOfGrantIdentityCard
              }
              helperText={formik.errors.placeOfGrantIdentityCard}
            />
          </Grid>
          <Grid item xs={4} md={4}>
            <TextField
              label="Dân tộc"
              variant="outlined"
              fullWidth
              name="nation"
              value={formik.values?.nation}
              onChange={formik.handleChange}
              error={formik.errors.nation && formik.touched.nation}
              helperText={formik.errors.nation}
            />
          </Grid>
          <Grid item xs={4} md={4}>
            <TextField
              label="Tôn giáo"
              variant="outlined"
              fullWidth
              name="religion"
              value={formik.values?.religion}
              onChange={formik.handleChange}
              error={formik.errors.religion && formik.touched.religion}
              helperText={formik.errors.religion}
            />
          </Grid>
        </Grid>
        <Grid container item xs={12} md={12} spacing={2}>
          <Grid item xs={12} md={12}>
            <TextField
              label="Địa chỉ"
              variant="outlined"
              fullWidth
              multiline
              maxRows={2}
              name="address"
              value={formik.values?.address}
              onChange={formik.handleChange}
              error={formik.errors.address && formik.touched.address}
              helperText={formik.errors.address}
            />
          </Grid>
        </Grid>
        <Grid container item xs={12} md={12} spacing={2}>
          <Grid item xs={4} md={4}>
            <TextField
              label="Số bảo hiểm y tế"
              variant="outlined"
              fullWidth
              name="numberMedicalInsurance"
              value={formik.values?.numberMedicalInsurance}
              onChange={formik.handleChange}
              error={
                formik.errors.numberMedicalInsurance &&
                formik.touched.numberMedicalInsurance
              }
              helperText={formik.errors.numberMedicalInsurance}
            />
          </Grid>
          <Grid item xs={4} md={4}>
            <LocalizationProvider dateAdapter={AdapterDayjs} fullWidth>
              <DatePicker
                label="Ngày cấp bảo hiểm y tế"
                name="issuedDateMedicalInsurance"
                inputFormat="DD/MM/YYYY"
                value={formik.values?.issuedDateMedicalInsurance || null}
                onChange={(value) => {
                  if (value) {
                    formik.setFieldValue(
                      "issuedDateMedicalInsurance",
                      new Date(value)
                    );
                  }
                }}
                renderInput={(params) => {
                  return (
                    <TextField
                      {...params}
                      format="DD/MM/YYYY"
                      type="date"
                      fullWidth
                      variant="outlined"
                      error={
                        formik.errors.issuedDateMedicalInsurance &&
                        formik.touched.issuedDateMedicalInsurance
                      }
                      helperText={formik.errors.issuedDateMedicalInsurance}
                    />
                  );
                }}
              />
            </LocalizationProvider>
          </Grid>
          <Grid item xs={4} md={4}>
            <TextField
              label="Nơi cấp bảo hiểm y tế"
              variant="outlined"
              fullWidth
              name="placeOfIssueMedicalInsurance"
              value={formik.values?.placeOfIssueMedicalInsurance}
              onChange={formik.handleChange}
              error={
                formik.errors.placeOfIssueMedicalInsurance &&
                formik.touched.placeOfIssueMedicalInsurance
              }
              helperText={formik.errors.placeOfIssueMedicalInsurance}
            />
          </Grid>
        </Grid>
        <Grid container item xs={12} md={12} spacing={2}>
          <Grid item xs={4} md={4}>
            <TextField
              label="Số bảo hiểm xã hội"
              variant="outlined"
              fullWidth
              name="numberSocialInsurance"
              value={formik.values?.numberSocialInsurance}
              onChange={formik.handleChange}
              error={
                formik.errors.numberSocialInsurance &&
                formik.touched.numberSocialInsurance
              }
              helperText={formik.errors.numberSocialInsurance}
            />
          </Grid>
          <Grid item xs={4} md={4}>
            <LocalizationProvider dateAdapter={AdapterDayjs} fullWidth>
              <DatePicker
                label="Ngày cấp bảo hiểm xã hội"
                name="issuedDateSocialInsurance"
                inputFormat="DD/MM/YYYY"
                value={formik.values?.issuedDateSocialInsurance || null}
                onChange={(value) => {
                  if (value) {
                    formik.setFieldValue(
                      "issuedDateSocialInsurance",
                      new Date(value)
                    );
                  }
                }}
                renderInput={(params) => {
                  return (
                    <TextField
                      {...params}
                      format="DD/MM/YYYY"
                      type="date"
                      fullWidth
                      variant="outlined"
                      error={
                        formik.errors.issuedDateSocialInsurance &&
                        formik.touched.issuedDateSocialInsurance
                      }
                      helperText={formik.errors.issuedDateSocialInsurance}
                    />
                  );
                }}
              />
            </LocalizationProvider>
          </Grid>
          <Grid item xs={4} md={4}>
            <TextField
              label="Nơi cấp bảo hiểm xã hội"
              variant="outlined"
              fullWidth
              name="placeOfIssueSocialInsurance"
              value={formik.values?.placeOfIssueSocialInsurance}
              onChange={formik.handleChange}
              error={
                formik.errors.placeOfIssueSocialInsurance &&
                formik.touched.placeOfIssueSocialInsurance
              }
              helperText={formik.errors.placeOfIssueSocialInsurance}
            />
          </Grid>
        </Grid>
        <Grid item xs={12} md={12}>
          <Autocomplete
            fullWidth
            freeSolo
            options={listCertificate}
            getOptionLabel={(option) => option.name}
            value={formik.values?.certificate}
            onChange={(event, value) =>
              formik.setFieldValue("certificate", value)
            }
            filterOptions={filterOptions}
            renderOption={(props, option, { selected }) => (
              <Box component="li" {...props}>
                <Checkbox
                  icon={icon}
                  checkedIcon={checkedIcon}
                  style={{ marginRight: 8 }}
                  checked={selected}
                />
                <span style={{ width: "10%" }}>{option.code}</span>{" "}
                <span style={{ width: "15%" }}>{option.name}</span>{" "}
                <span style={{ width: "25%" }}>{option.majors}</span>{" "}
                <span style={{ width: "10%" }}>
                  {moment(option.issuedDate).format("DD/MM/YYYY")}
                </span>{" "}
                <span style={{ width: "25%" }}>{option.grantedBy}</span>
              </Box>
            )}
            renderInput={(params) => (
              <TextField
                {...params}
                label="Bằng cấp"
                placeholder="Bằng cấp"
                error={formik.errors.certificate && formik.touched.certificate}
                helperText={formik.errors.certificate}
              />
            )}
          />
        </Grid>
        <Grid item xs={12} md={12}>
          <Autocomplete
            multiple
            fullWidth
            options={listLanguage}
            getOptionLabel={(option) => option?.name}
            value={formik.values?.languages}
            defaultValue={[]}
            onChange={(event, value) => {
              if (value) {
                formik.setFieldValue("languages", value);
              }
            }}
            filterOptions={filterOptions}
            renderOption={(props, option, { selected }) => (
              <Box component="li" {...props}>
                <Checkbox
                  icon={icon}
                  checkedIcon={checkedIcon}
                  style={{ marginRight: 8 }}
                  checked={selected}
                />
                <span style={{ width: "10%" }}>{option.code}</span>{" "}
                <span style={{ width: "15%" }}>{option.name}</span>{" "}
                <span style={{ width: "50%" }}>{option.description}</span>
              </Box>
            )}
            renderInput={(params) => (
              <TextField
                {...params}
                label="Chứng chỉ"
                placeholder="Chứng chỉ"
                error={formik.errors.languages && formik.touched.languages}
                helperText={formik.errors.languages}
              />
            )}
          />
        </Grid>
      </Grid>
      <div style={{ display: "flex", justifyContent: "flex-end" }}>
        <Button
          variant="contained"
          color="primary"
          onClick={() => formik.handleSubmit()}
          style={{ margin: "10px" }}
        >
          Lưu
        </Button>
      </div>
    </form>
  );
}
