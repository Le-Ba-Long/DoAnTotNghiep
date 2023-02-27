import React, { useState, useEffect } from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import Box from "@mui/material/Box";
import IconButton from "@mui/material/IconButton";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import CloseIcon from "@mui/icons-material/Close";
import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import {
  addCandidate,
  editCandidate,
  getListRecruit,
} from "./CandidateProfileService";
import Autocomplete from "@mui/material/Autocomplete";
import { useFormik } from "formik";
import * as Yup from "yup";
import { toast } from "react-toastify";
import { uploadImage } from "app/constant";
import "./Candidate.scss";
import AddIcon from "@mui/icons-material/Add";

export default function CandidateProfileDialog(props) {
  const { open, handleClose, item } = props;

  const [listRecruit, setListRecruit] = useState([]);
  const [file, setFile] = useState(undefined);

  const formik = useFormik({
    initialValues: {
      code: item.id ? item?.code : "",
      fullName: item.id ? item?.fullName : "",
      age: item.id ? item?.age : "",
      imageName: item.id ? item?.imageName : "",
      education: item.id ? item?.education : "",
      major: item.id ? item?.major : "",
      dateOfBirth: item.id ? item?.dateOfBirth : null,
      email: item.id ? item?.email : "",
      phone: item.id ? item?.phone : "",
      address: item.id ? item?.address : "",
      recruitDtos: item.id ? item?.recruitDtos : [],
      status: item.id ? item?.status : "",
      careerGoals: item.id ? item?.careerGoals : "",
      workingExperience: item.id ? item?.workingExperience : "",
      hobby: item.id ? item?.hobby : "",
      skill: item.id ? item?.skill : "",
    },
    enableReinitialize: true,
    validateOnChange: false,
    validateOnBlur: false,
    validationSchema: Yup.object({
      code: Yup.string()
        .matches(/^MaHS[0-9]{4}$/, "Mã hồ sơ chưa đúng format VD:(MaHS9999)")
        .required("Vui lòng nhập trường này"),
      fullName: Yup.string().required("Vui lòng nhập trường này"),
      age: Yup.number()
        .typeError("Tuổi phải là số!")
        .required("Vui lòng nhập trường này"),
      education: Yup.string().required("Vui lòng nhập trường này"),
      major: Yup.string().required("Vui lòng nhập trường này"),
      dateOfBirth: Yup.date()
        .nullable()
        .typeError("Sai định dạng ngày!")
        .required("Vui lòng nhập trường này"),
      email: Yup.string()
        .matches(
          /^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[A-Za-z]+$/,
          "Đây không phải là email!"
        )
        .required("Vui lòng nhập Email!"),
      phone: Yup.string()
        .matches(/(84|0[3|5|7|8|9])+([0-9]{8})\b/, "Số điện thoại không hợp lệ")
        .required("Vui lòng nhập Số điện thoại!"),
      recruitDtos: Yup.array().nullable().required("Vui lòng nhập trường này"),
      address: Yup.string().required("Vui lòng nhập trường này"),
      careerGoals: Yup.string().required("Vui lòng nhập trường này"),
      workingExperience: Yup.string().required("Vui lòng nhập trường này"),
      hobby: Yup.string().required("Vui lòng nhập trường này"),
      skill: Yup.string().required("Vui lòng nhập trường này"),
    }),
    onSubmit: (values) => {
      values.id = item?.id;
      values.status = 17;
      handleAdd(values);
    },
  });

  useEffect(() => {
    getListRecruit()
      .then((res) => {
        if (res.data.statusCode === 200) {
          setListRecruit(res.data.data);
        } else {
          toast.warning(res.data.message);
        }
      })
      .catch((err) => toast.error("Có lỗi!"));
  }, []);
  console.log(formik.values?.recruitDtos);
  const handleAdd = (values) => {
    if (values.id) {
      editCandidate(values)
        .then((res) => {
          toast.success("Sửa thông tin hồ sơ thành công");
          handleClose();
        })
        .catch((err) => toast.error("Có lỗi xảy ra!"));
    } else {
      addCandidate(values)
        .then((res) => {
          if (res.data.statusCode === 200) {
            toast.success("Thêm hồ sơ thành công");
            handleClose();
          } else {
            toast.warning(res.data.message);
          }
        })
        .catch((err) => toast.error("Có lỗi xảy ra!"));
    }
  };

  return (
    <Dialog open={open} fullWidth maxWidth={"md"}>
      <DialogTitle style={{ marginBlockEnd: 0, padding: "16px 24px 0" }}>
        {item.id ? "Sửa hồ sơ" : "Thêm hồ sơ"}
        <Box className="icon-close" onClick={handleClose}>
          <IconButton color="error">
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>
      <form
        onSubmit={formik.handleSubmit}
        onError={(errors) => console.log(errors)}
      >
        <DialogContent style={{ padding: "0 20px" }}>
          <Grid container spacing={2} style={{ marginTop: 5 }}>
            <Grid container item xs={12} md={12} spacing={2}>
              <Grid item xs={3} md={3}>
                <TextField
                  label="Mã hồ sơ"
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
              <Grid item xs={2} md={2}>
                <TextField
                  label="Tuổi"
                  variant="outlined"
                  fullWidth
                  name="age"
                  value={formik.values?.age}
                  onChange={formik.handleChange}
                  error={formik.errors.age && formik.touched.age}
                  helperText={formik.errors.age}
                />
              </Grid>
              <Grid item xs={4} md={4}>
                <TextField
                  label="Ảnh ứng viên"
                  type="text"
                  style={{ width: "82%" }}
                  name="imageName"
                  value={formik.values?.imageName}
                  // InputLabelProps={{ shrink: true }}
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
              <Grid item xs={4} md={4}>
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
                  label="Chuyên nghành"
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
                            formik.errors.dateOfBirth &&
                            formik.touched.dateOfBirth
                          }
                          helperText={formik.errors.dateOfBirth}
                        />
                      );
                    }}
                  />
                </LocalizationProvider>
              </Grid>
              <Grid container item xs={12} md={12} spacing={2}>
                <Grid item xs={4} md={4}>
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
                <Grid item xs={4} md={4}>
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
                <Grid item xs={4} md={4}>
                  <Autocomplete
                    options={listRecruit}
                    getOptionLabel={(option) => option.titleRecruit}
                    value={formik.values?.recruitDtos[0]}
                    onChange={(event, newValue) =>
                      formik.setFieldValue("recruitDtos", [newValue])
                    }
                    componentsProps={{ paper: { elevation: 8 } }}
                    fullWidth
                    renderInput={(params) => (
                      <TextField
                        {...params}
                        variant="outlined"
                        fullWidth
                        label="Vị trí ứng tuyển"
                        error={
                          formik.errors.recruitDtos &&
                          formik.touched.recruitDtos
                        }
                        helperText={formik.errors.recruitDtos}
                      />
                    )}
                  />
                </Grid>
              </Grid>
              <Grid item xs={12} md={12}>
                <TextField
                  label="Địa chỉ"
                  variant="outlined"
                  fullWidth
                  name="address"
                  value={formik.values?.address}
                  onChange={formik.handleChange}
                  error={formik.errors.address && formik.touched.address}
                  helperText={formik.errors.address}
                />
              </Grid>
              <Grid item xs={12} md={12}>
                <TextField
                  label="Mục tiêu nghề nghiệp"
                  variant="outlined"
                  fullWidth
                  multiline
                  name="careerGoals"
                  value={formik.values?.careerGoals}
                  onChange={formik.handleChange}
                  error={
                    formik.errors.careerGoals && formik.touched.careerGoals
                  }
                  helperText={formik.errors.careerGoals}
                />
              </Grid>
              <Grid item xs={12} md={12}>
                <TextField
                  label="Kinh nghiệm làm việc"
                  variant="outlined"
                  fullWidth
                  multiline
                  name="workingExperience"
                  value={formik.values?.workingExperience}
                  onChange={formik.handleChange}
                  error={
                    formik.errors.workingExperience &&
                    formik.touched.workingExperience
                  }
                  helperText={formik.errors.workingExperience}
                />
              </Grid>
              <Grid item xs={6} md={6}>
                <TextField
                  label="Sở thích"
                  variant="outlined"
                  fullWidth
                  multiline
                  name="hobby"
                  value={formik.values?.hobby}
                  onChange={formik.handleChange}
                  error={formik.errors.hobby && formik.touched.hobby}
                  helperText={formik.errors.hobby}
                />
              </Grid>
              <Grid item xs={6} md={6}>
                <TextField
                  label="Kỹ năng"
                  variant="outlined"
                  fullWidth
                  multiline
                  name="skill"
                  value={formik.values?.skill}
                  onChange={formik.handleChange}
                  error={formik.errors.skill && formik.touched.skill}
                  helperText={formik.errors.skill}
                />
              </Grid>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button variant="contained" color="secondary" onClick={handleClose}>
            Hủy
          </Button>
          <Button type="submit" variant="contained" color="primary">
            {item.id ? "Lưu" : "Thêm"}
          </Button>
        </DialogActions>
      </form>
    </Dialog>
  );
}
