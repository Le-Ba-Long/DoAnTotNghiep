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
import moment from "moment";
import MakeAppointment from "./MakeAppointment";
import RefuseDialog from "./RefuseDialog";
import { toast } from "react-toastify";
import { editCandidate } from "./CandidateProfileService";
import TextField from "@mui/material/TextField";
import "./Candidate.scss";

export default function CandidateProfileView(props) {
  const { open, handleClose, item } = props;

  const [candidate, setCandidate] = useState({});
  const [shouldOpenMakeApointmentDialog, setShouldOpenMakeApointmentDialog] =
    useState(false);
  const [shouldOpenRefuseDialog, setShouldOpenRefuseDialog] = useState(false);

  useEffect(() => {
    setCandidate(item);
  }, []);

  const handleMakeApointment = () => {
    editCandidate(candidate)
      .then((res) => {
        if (res.data.statusCode === 200) {
          toast.success("Hẹn lịch phỏng vấn thành công");
          handleClose();
        } else {
          toast.warning("Lỗi xác thực");
        }
      })
      .catch((err) => toast.error("Có lỗi xảy ra"));
  };

  const handleRefuse = () => {
    editCandidate(candidate)
      .then((res) => {
        if (res.data.statusCode === 200) {
          toast.success("Đã từ chối hồ sơ ứng viên này");
          handleClose();
        } else {
          toast.warning(res.data.message);
        }
      })
      .catch((err) => toast.error("Có lỗi xảy ra!"));
  };

  return (
    <>
      <Dialog open={open} fullWidth maxWidth={"md"}>
        <DialogTitle style={{ marginBlockEnd: 0, padding: "16px 24px 0" }}>
          Hồ sơ ứng viên
          <Box className="icon-close" onClick={handleClose}>
            <IconButton color="error">
              <CloseIcon />
            </IconButton>
          </Box>
        </DialogTitle>
        <DialogContent
          style={{ margin: "20px", fontSize: 15, overflowX: "hidden" }}
        >
          <Grid container>
            <Grid container spacing={1} style={{ marginTop: 5 }}>
              <Grid
                container
                item
                xs={8}
                md={8}
                spacing={1}
                style={{ height: 200 }}
              >
                <Grid item style={{ fontWeight: 600 }}>
                  Mã hồ sơ:
                </Grid>
                <Grid item xs={8} md={8}>
                  {candidate.code}
                </Grid>
                <Grid item style={{ fontWeight: 600 }}>
                  Họ và tên ứng viên:
                </Grid>
                <Grid item xs={8} md={8}>
                  {candidate.fullName}
                </Grid>
                <Grid item style={{ fontWeight: 600 }}>
                  Tuổi:
                </Grid>
                <Grid item xs={2} md={2}>
                  {candidate.age}
                </Grid>
                <Grid item style={{ fontWeight: 600 }}>
                  Ngày sinh:
                </Grid>
                <Grid item xs={6} md={6}>
                  {moment(candidate.dateOfBirth).format("DD/MM/YYYY")}
                </Grid>
                <Grid item style={{ fontWeight: 600 }}>
                  Số điện thoại:
                </Grid>
                <Grid item xs={9} md={9}>
                  {candidate.phone}
                </Grid>
                <Grid item style={{ fontWeight: 600 }}>
                  Email:
                </Grid>
                <Grid item xs={8} md={8}>
                  {candidate.email}
                </Grid>
              </Grid>
              <Grid container item xs={4} md={4} style={{ height: 200 }}>
                <Grid item xs={12} style={{ height: "100%" }}>
                  <img
                    src={candidate?.image}
                    alt=""
                    style={{
                      height: "100%",
                      float: "right",
                      border: "1px solid #ccc",
                      borderRadius: 10,
                    }}
                  />
                </Grid>
              </Grid>
              <Grid container item xs={12} md={12} spacing={1}>
                <Grid container item xs={12} md={12} spacing={1}>
                  <Grid item style={{ fontWeight: 600 }}>
                    Địa chỉ:
                  </Grid>
                  <Grid item xs={10} md={10}>
                    {candidate.address}
                  </Grid>
                </Grid>
                <Grid container item xs={6} md={6} spacing={1}>
                  <Grid item style={{ fontWeight: 600 }}>
                    Trình độ học vấn:
                  </Grid>
                  <Grid item xs={5} md={5}>
                    {candidate.education}
                  </Grid>
                </Grid>
                <Grid container item xs={10} md={10} spacing={1}>
                  <Grid item style={{ fontWeight: 600 }}>
                    Ngành:
                  </Grid>
                  <Grid item xs={10} md={10}>
                    {candidate.major}
                  </Grid>
                </Grid>
                <Grid container item xs={12} md={12} spacing={1}>
                  <Grid item style={{ fontWeight: 600 }}>
                    Ứng tuyển vị trí:
                  </Grid>
                  <Grid item xs={8} md={8}>
                    {item?.recruitDtos[0]?.titleRecruit || ""}
                  </Grid>
                </Grid>
                {candidate?.status === 18 ? (
                  <>
                    <Grid container item xs={12} md={12} spacing={1}>
                      <Grid item style={{ fontWeight: 600 }}>
                        Người hẹn:
                      </Grid>
                      <Grid item xs={8} md={8}>
                        {candidate?.interviewer}
                      </Grid>
                    </Grid>
                    <Grid container item xs={12} md={12} spacing={1}>
                      <Grid item style={{ fontWeight: 600 }}>
                        Thời gian hẹn:
                      </Grid>
                      <Grid item xs={8} md={8}>
                        {candidate?.interviewDate
                          ? moment(candidate?.interviewDate).format(
                              "DD/MM/YYYY hh:mm A"
                            )
                          : ""}
                      </Grid>
                    </Grid>
                  </>
                ) : candidate?.status === 6 || candidate?.status === 9 ? (
                  <Grid container item xs={12} md={12} spacing={2}>
                    <Grid item style={{ fontWeight: 600 }}>
                      Lý do:
                    </Grid>
                    <Grid item xs={8} md={8}>
                      {candidate?.refusalReason}
                    </Grid>
                  </Grid>
                ) : (
                  ""
                )}
                <Grid container item xs={12} md={12} spacing={1}>
                  <Grid item xs={12} style={{ fontWeight: 600 }}>
                    Mục tiêu nghề nghiệp:
                  </Grid>
                  <Grid item xs={12} md={12}>
                    <TextField
                      variant="standard"
                      fullWidth
                      className="font-15"
                      multiline
                      name="careerGoals"
                      value={item?.careerGoals || ""}
                      InputProps={{
                        readOnly: true,
                        disableUnderline: true,
                      }}
                    />
                  </Grid>
                </Grid>
                <Grid container item xs={12} md={12} spacing={1}>
                  <Grid item xs={12} style={{ fontWeight: 600 }}>
                    Kinh nghiệm làm việc:
                  </Grid>
                  <Grid item xs={12} md={12}>
                    <TextField
                      variant="standard"
                      fullWidth
                      className="font-15"
                      multiline
                      name="workingExperience"
                      value={item?.workingExperience || ""}
                      InputProps={{
                        readOnly: true,
                        disableUnderline: true,
                      }}
                    />
                  </Grid>
                </Grid>
                <Grid container item xs={12} md={12} spacing={1}>
                  <Grid item style={{ fontWeight: 600 }}>
                    Sở thích:
                  </Grid>
                  <Grid item xs={12} md={12}>
                    <TextField
                      variant="standard"
                      fullWidth
                      className="font-15"
                      multiline
                      name="hobby"
                      value={item?.hobby || ""}
                      InputProps={{
                        readOnly: true,
                        disableUnderline: true,
                      }}
                    />
                  </Grid>
                </Grid>
                <Grid container item xs={12} md={12} spacing={1}>
                  <Grid item xs={12} style={{ fontWeight: 600 }}>
                    Kỹ năng:
                  </Grid>
                  <Grid item xs={12} md={12}>
                    <TextField
                      variant="standard"
                      fullWidth
                      className="font-15"
                      multiline
                      name="skill"
                      value={item?.skill || ""}
                      InputProps={{
                        readOnly: true,
                        disableUnderline: true,
                      }}
                    />
                  </Grid>
                </Grid>
              </Grid>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            color="primary"
            onClick={() => setShouldOpenMakeApointmentDialog(true)}
            disabled={
              candidate?.status === 18 ||
              candidate?.status === 6 ||
              candidate?.status === 8 ||
              candidate?.status === 9 ||
              candidate?.status === 19
            }
          >
            Hẹn phỏng vấn
          </Button>
          <Button
            variant="contained"
            color="error"
            onClick={() => setShouldOpenRefuseDialog(true)}
            disabled={
              candidate?.status === 18 ||
              candidate?.status === 6 ||
              candidate?.status === 8 ||
              candidate?.status === 9 ||
              candidate?.status === 19
            }
          >
            Từ chối
          </Button>
          <Button variant="contained" color="secondary" onClick={handleClose}>
            Hủy
          </Button>
        </DialogActions>
      </Dialog>
      {shouldOpenMakeApointmentDialog && (
        <MakeAppointment
          open={shouldOpenMakeApointmentDialog}
          candidate={candidate}
          handleDialogClose={() => setShouldOpenMakeApointmentDialog(false)}
          handleClose={handleClose}
          setCandidate={setCandidate}
          handleMakeApointment={handleMakeApointment}
        />
      )}
      {shouldOpenRefuseDialog && (
        <RefuseDialog
          open={shouldOpenRefuseDialog}
          candidate={candidate}
          handleDialogClose={() => setShouldOpenRefuseDialog(false)}
          setCandidate={setCandidate}
          handleRefuse={handleRefuse}
        />
      )}
    </>
  );
}
