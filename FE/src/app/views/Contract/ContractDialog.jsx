import React, { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import CloseIcon from '@mui/icons-material/Close';
import Grid from '@mui/material/Grid';
import moment from 'moment';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import './Contract.scss';
import { TextField } from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import InputAdornment from '@mui/material/InputAdornment';

export default function ContractDialog(props) {
  const { open, item, handleCloseDialog, setItem } = props;
  const formik = useFormik({
    initialValues: {
      code: item?.employee ? item.code : '',
      nameLeader: item?.employee ? item.nameLeader : '',
      postionLeader: item?.employee ? item.postionLeader : '',
      signingDate: item?.employee ? item.signingDate : null,
      contractEffect: item?.employee ? item.contractEffect : null,
      basicSalary: item?.employee ? item.basicSalary : '',
      hourlyRate: item?.employee ? item.hourlyRate : '',
    },
    enableReinitialize: true,
    validateOnChange: false,
    validateOnBlur: false,
    validationSchema: Yup.object({
      code: Yup.string()
        .matches(/^MaHĐ[0-9]{4}$/, 'Mã hợp đồng chưa đúng format VD:(MaHĐ9999)')
        .required('Vui lòng nhập trường này'),
      nameLeader: Yup.string().required('Vui lòng nhập trường này'),
      postionLeader: Yup.string().required('Vui lòng nhập trường này'),
      signingDate: Yup.date()
        .nullable()
        .typeError('Sai định dạng ngày!')
        .required('Vui lòng nhập trường này'),
      contractEffect: Yup.date()
        .nullable()
        .typeError('Sai định dạng ngày!')
        .required('Vui lòng nhập trường này'),
      basicSalary: Yup.number()
        .typeError('Số lương phải là số!')
        .required('Vui lòng nhập trường này'),
      hourlyRate: Yup.number()
        .typeError('Số lương phải là số!')
        .required('Vui lòng nhập trường này'),
    }),
    onSubmit: (values) => {
      values.employee = item;
      values.status = 4;
      values.coefficientSalary = 1.5;
      setItem({ ...item, contract: values, status: 12 });
    },
  });

  const handlePrint = () => {
    var oldstr = document.body.innerHTML;
    document.body.innerHTML = document.getElementById('contract').innerHTML;
    window.print();
    window.location.reload();
    document.body.innerHTML = oldstr;
  };

  return (
    <Dialog open={open} fullWidth maxWidth={'md'}>
      <DialogTitle style={{ marginBlockEnd: 0, padding: '16px 24px 0' }}>
        <Box className="icon-close" onClick={handleCloseDialog}>
          <IconButton color="error">
            <CloseIcon />
          </IconButton>
        </Box>
      </DialogTitle>
      <form onSubmit={formik.handleSubmit} onError={(errors) => console.log(errors)}>
        <DialogContent style={{ padding: '0 20px' }} id="contract">
          <Grid
            container
            spacing={2}
            style={{ fontFamily: '"Times New Roman", Times, serif', fontSize: '18px' }}
          >
            <Grid item container xs={12}>
              <Grid
                item
                container
                xs={12}
                style={{ fontSize: '25px', textTransform: "uppercase'", fontWeight: 'bold' }}
                alignItems="center"
                justifyContent="center"
              >
                Cộng hòa xã hội chủ nghĩa Việt Nam
              </Grid>
              <Grid
                item
                container
                xs={12}
                style={{ fontSize: '22px' }}
                alignItems="center"
                justifyContent="center"
              >
                Độc lập - Tự do - Hạnh phúc
              </Grid>
              <Grid
                item
                container
                xs={12}
                style={{ fontSize: '22px', fontWeight: 'bold' }}
                alignItems="center"
                justifyContent="center"
              >
                *****
              </Grid>
              <Grid item container xs={10} spacing={1} className="pd-60">
                <Grid item className="mr-10">
                  Mã hợp đồng:
                </Grid>
                <Grid item xs={3} md={3}>
                  <TextField
                    variant="standard"
                    fullWidth
                    name="code"
                    value={formik.values?.code}
                    onChange={formik.handleChange}
                    InputProps={{
                      readOnly: item?.employee,
                    }}
                    error={formik.errors.code && formik.touched.code}
                    helperText={formik.errors.code}
                  />
                </Grid>
              </Grid>
            </Grid>
            <Grid item container xs={12}>
              <Grid
                item
                container
                xs={12}
                style={{
                  fontSize: '30px',
                  textTransform: 'uppercase',
                  fontWeight: 'bold',
                }}
                alignItems="center"
                justifyContent="center"
              >
                {' '}
                Hợp đồng lao động
              </Grid>
              <Grid item container xs={2}></Grid>
              <Grid
                item
                container
                xs={8}
                style={{
                  marginBottom: 20,
                  textAlign: 'center',
                }}
                alignItems="center"
                justifyContent="center"
              >
                (Ban hành theo Thông tư số 21/2003/TT-BLĐTBXH ngày 22/9/2003 của Bộ Lao động –
                Thương binh và Xã hội)
              </Grid>
            </Grid>
            <Grid item container xs={12} className="pd-60" spacing={1} alignItems="center">
              <Grid item xs={12} md={12}>
                Chúng tôi, một bên là:
              </Grid>
              <Grid item container xs={12} md={12}>
                <Grid item className="mr-10" style={{ padding: '2px 0' }}>
                  Ông/Bà:
                </Grid>
                <Grid item xs={6} md={6}>
                  <TextField
                    variant="standard"
                    fullWidth
                    className="name"
                    name="nameLeader"
                    value={formik.values?.nameLeader}
                    onChange={formik.handleChange}
                    InputProps={{
                      readOnly: item?.employee,
                    }}
                    error={formik.errors.nameLeader && formik.touched.nameLeader}
                    helperText={formik.errors.nameLeader}
                  />
                </Grid>
              </Grid>
              <Grid item container xs={12} md={12}>
                <Grid item className="mr-10">
                  Chức vụ:
                </Grid>
                <Grid item xs={6} md={6}>
                  <TextField
                    variant="standard"
                    fullWidth
                    name="postionLeader"
                    value={formik.values?.postionLeader}
                    onChange={formik.handleChange}
                    InputProps={{
                      readOnly: item?.employee,
                    }}
                    error={formik.errors.postionLeader && formik.touched.postionLeader}
                    helperText={formik.errors.postionLeader}
                  />
                </Grid>
              </Grid>
              <Grid item container xs={12} md={12}>
                <Grid item className="mr-10">
                  Đại diện cho công ty:
                </Grid>
                <Grid item xs={6} md={6} style={{ textTransform: 'uppercase' }}>
                  Công ty TNHH Oceantech
                </Grid>
              </Grid>
              <Grid item container xs={12} md={12}>
                <Grid item className="mr-10">
                  Địa chỉ công ty:
                </Grid>
                <Grid item xs={9} md={9}>
                  Số 467 Nguyễn Trãi, Thanh Xuân Nam, Thanh Xuân, Hà Nội
                </Grid>
              </Grid>
              <Grid item xs={12} md={12}>
                Và một bên là:
              </Grid>
              <Grid item container xs={12} md={12}>
                <Grid item className="mr-10">
                  Ông/Bà:
                </Grid>
                <Grid item xs={6} md={6} style={{ textTransform: 'uppercase', fontWeight: 'bold' }}>
                  {item?.employee ? item?.employee?.fullName : item?.fullName}
                </Grid>
              </Grid>
              <Grid item container xs={12} md={12}>
                <Grid item className="mr-10">
                  Số CCCD:
                </Grid>
                <Grid item xs={2} md={2} style={{ textTransform: 'uppercase', fontWeight: 'bold' }}>
                  {item?.employee ? item?.employee?.numberIdentityCard : item?.numberIdentityCard}
                </Grid>
                <Grid item className="mr-10">
                  Ngày cấp:
                </Grid>
                <Grid item xs={2} md={2}>
                  {item?.employee
                    ? moment(item?.employee?.issuedDateIdentityCard).format('DD/MM/YYYY')
                    : moment(item?.issuedDateIdentityCard).format('DD/MM/YYYY')}
                </Grid>
                <Grid item className="mr-10">
                  Nơi cấp:
                </Grid>
                <Grid item xs={3} md={3}>
                  {item?.employee
                    ? item?.employee?.placeOfGrantIdentityCard
                    : item?.placeOfGrantIdentityCard}
                </Grid>
              </Grid>
              <Grid item container xs={12} md={12}>
                <Grid item className="mr-10">
                  Địa chỉ:
                </Grid>
                <Grid item xs={10} md={10}>
                  {item?.employee ? item?.employee?.address : item?.address}
                </Grid>
              </Grid>
              <Grid item container xs={12} md={12}>
                Thỏa thuận ký kết hợp đồng lao động và cam kết làm đúng những điều khoản sau đây:
              </Grid>
              <Grid item container xs={12} md={12}>
                <Grid item className="mr-10">
                  <ul className="dashed">
                    <span style={{ fontWeight: 'bold' }}>
                      Điều 1: Thời hạn và công việc hợp đồng
                    </span>
                    <li>
                      <span className="mr-10">Bắt đầu từ</span>
                      <LocalizationProvider dateAdapter={AdapterDayjs} fullWidth>
                        <DatePicker
                          name="signingDate"
                          inputFormat="DD/MM/YYYY"
                          value={formik.values?.signingDate || null}
                          onChange={(value) => {
                            if (value) {
                              formik.setFieldValue('signingDate', new Date(value));
                            }
                          }}
                          readOnly={item?.employee}
                          renderInput={(params) => {
                            return (
                              <TextField
                                {...params}
                                format="DD/MM/YYYY"
                                type="date"
                                variant="standard"
                                error={formik.errors.signingDate && formik.touched.signingDate}
                                helperText={formik.errors.signingDate}
                              />
                            );
                          }}
                        />
                      </LocalizationProvider>
                      <span className="ml-10  mr-10">đến hết</span>
                      <LocalizationProvider dateAdapter={AdapterDayjs} fullWidth>
                        <DatePicker
                          name="contractEffect"
                          inputFormat="DD/MM/YYYY"
                          value={formik.values?.contractEffect || null}
                          onChange={(value) => {
                            if (value) {
                              formik.setFieldValue('contractEffect', new Date(value));
                            }
                          }}
                          readOnly={item?.employee}
                          renderInput={(params) => {
                            return (
                              <TextField
                                {...params}
                                format="DD/MM/YYYY"
                                type="date"
                                variant="standard"
                                error={
                                  formik.errors.contractEffect && formik.touched.contractEffect
                                }
                                helperText={formik.errors.contractEffect}
                              />
                            );
                          }}
                        />
                      </LocalizationProvider>
                    </li>
                    <li>Địa điểm làm việc tại Công ty TNHH Oceantech</li>
                    <li>
                      <span className="mr-10">Chức vụ:</span>
                      {item?.employee
                        ? item?.employee?.positions[0]?.name
                        : item?.positions[0]?.name}
                    </li>
                    <li>
                      <span className="mr-10">Phòng ban:</span>
                      {item?.employee ? item?.employee?.department?.name : item?.department?.name}
                    </li>
                  </ul>
                </Grid>
                <Grid item className="mr-10" style={{ marginBottom: 20 }}>
                  <ul className="dashed">
                    <span style={{ fontWeight: 'bold' }}>Điều 2: Chế độ làm việc</span>
                    <li>
                      Thời giờ làm việc 08 giờ/ngày, 44 giờ/tuần. Sẵn sàng làm thêm giờ vào ngày
                      nghỉ tuỳ công việc.
                    </li>
                    <li>
                      Được cấp phát những dụng cụ làm việc gồm: Tùy theo yêu cầu công việc, người
                      lao động được cấp phát dụng cụ cần thiết.
                    </li>
                    <li>
                      Sau khi kí hợp đồng, người lao động sẽ thử việc với mức lương 80% mức lương cơ
                      bản
                    </li>
                  </ul>
                </Grid>
                <Grid item className="mr-10">
                  <ol className="number">
                    <span style={{ fontWeight: 'bold' }}>
                      Điều 3: Nghĩa vụ và quyền lợi của người lao động
                    </span>
                    <li style={{ fontWeight: 'bold' }}>
                      <span> Quyền lợi</span>
                    </li>
                    <ul className="dashed">
                      <li>Phương tiện đi lại làm việc : Tự túc.</li>
                      <li>
                        <span className="mr-10"> Mức lương cơ bản:</span>
                        <TextField
                          variant="standard"
                          name="basicSalary"
                          value={
                            formik.values?.basicSalary
                              ? formik.values?.basicSalary
                                  .toString()
                                  .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',')
                              : ''
                          }
                          onChange={(event) => {
                            formik.setFieldValue(
                              'basicSalary',
                              event.target.value.replace(/,/g, '')
                            );
                          }}
                          InputProps={{
                            maxLength: 10,
                            endAdornment: <InputAdornment position="end">VND</InputAdornment>,
                            readOnly: item?.employee,
                          }}
                          error={formik.errors.basicSalary && formik.touched.basicSalary}
                          helperText={formik.errors.basicSalary}
                        />
                      </li>
                      <li>
                        <span className="mr-10">Số tiền tính cho 1h làm thêm:</span>
                        <TextField
                          variant="standard"
                          name="hourlyRate"
                          value={
                            formik.values?.hourlyRate
                              ? formik.values?.hourlyRate
                                  .toString()
                                  .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',')
                              : ''
                          }
                          onChange={(event) => {
                            formik.setFieldValue(
                              'hourlyRate',
                              event.target.value.replace(/,/g, '')
                            );
                          }}
                          InputProps={{
                            maxLength: 10,
                            endAdornment: <InputAdornment position="end">VND</InputAdornment>,
                            readOnly: item?.employee,
                          }}
                          error={formik.errors.hourlyRate && formik.touched.hourlyRate}
                          helperText={formik.errors.hourlyRate}
                        />
                      </li>
                      <li>Hình thức trả lương : Tiền mặt hoặc chuyển khoản.</li>
                      <li>Được trả lương : 01 lần vào ngày 01 đến 05 hàng tháng.</li>
                      <li>Tiền thưởng : Theo quy chế của Công ty.</li>
                      <li>Chế độ nâng lương : Theo quy chế của Công ty.</li>
                      <li>
                        Chế độ nghỉ ngơi (nghỉ hàng tuần, phép năm, lễ tết...): Theo chế độ quy định
                        của Nhà nước và quy chế của Công ty.
                      </li>
                      <li>
                        Bảo hiểm xã hội, bảo hiểm y tế, bảo hiểm thất nghiệp: Theo quy định của Nhà
                        nước;
                      </li>
                      <li>
                        Chế độ đào tạo: Theo quy chế của Công ty. Trong trường hợp nhân viên được cử
                        đi đào tạo thì nhân viên đó phải hoàn thành khoá học đúng thời hạn, phải cam
                        kết sẽ phục vụ lâu dài cho Công ty sau khi kết thúc khoá học và được hưởng
                        nguyên lương trong thời gian học, các quyền lợi khác như người đi làm. Nếu
                        sau khi kết thúc khóa đào tạo mà nhân viên không tiếp tục hợp tác với Công
                        ty thì nhân viên phải hoàn trả lại phí đào tạo và các khoản chế độ đã được
                        nhận trong thời gian đào tạo.
                      </li>
                      <li>Những thỏa thuận khác: Theo quy chế của Công ty.</li>
                    </ul>
                    <li style={{ fontWeight: 'bold' }}>
                      <span> Nghĩa vụ:</span>
                    </li>
                    <ul className="dashed">
                      <li>Hoàn thành những công việc đã cam kết trong hợp đồng lao động.</li>
                      <li>
                        Chấp hành lệnh điều hành sản xuất - kinh doanh, nội quy kỷ luật lao động, an
                        toàn lao động ...
                      </li>
                      <li>Bồi thường vi phạm và vật chất: theo quy định của Công ty.</li>
                    </ul>
                  </ol>
                </Grid>
                <Grid item className="mr-10">
                  <ol className="number">
                    <span style={{ fontWeight: 'bold' }}>
                      Điều 4: Nghĩa vụ và quyền hạn của người sử dụng lao động
                    </span>
                    <li style={{ fontWeight: 'bold' }}>
                      <span> Nghĩa vụ:</span>
                    </li>
                    <ul className="dashed">
                      <li>
                        Bảo đảm việc làm và thực hiện đầy đủ những điều đã cam kết trong hợp đồng
                        lao động.
                      </li>
                      <li>
                        Thanh toán đầy đủ, đúng thời hạn các chế độ và quyền lợi cho người lao động
                        theo hợp đồng lao động, thỏa ước lao động tập thể (nếu có).
                      </li>
                    </ul>
                    <li style={{ fontWeight: 'bold' }}>
                      <span> Quyền hạn:</span>
                    </li>
                    <ul className="dashed">
                      <li>
                        Điều hành người lao động hoàn thành công việc theo hợp đồng (bố trí, điều
                        chuyển, tạm ngừng việc).
                      </li>
                      <li>
                        Tạm hoãn, chấm dứt hợp đồng lao động, kỷ luật người lao động theo quy định
                        của pháp luật, thỏa ước lao động tập thể (nếu có) và nội quy lao động của
                        doanh nghiệp.
                      </li>
                    </ul>
                  </ol>
                </Grid>
                <Grid item className="mr-10">
                  <ul className="dashed">
                    <span style={{ fontWeight: 'bold' }}>Điều 5: Điều khoản thi hành</span>
                    <li>
                      Những vấn đề về lao động không ghi trong hợp đồng lao động này thì áp dụng qui
                      định của thỏa ước tập thể, trường hợp chưa có thỏa ước tập thể thì áp dụng quy
                      định của pháp luật lao động.
                    </li>
                    <li>
                      Hợp đồng lao động được làm thành 02 bản có giá trị ngang nhau, mỗi bên giữ 01
                      bản và có hiệu lực từ{' '}
                      {formik.values?.signingDate
                        ? moment(formik.values?.signingDate).format('DD/MM/YYYY')
                        : '.../.../...'}
                      . Khi hai bên ký kết phụ lục hợp đồng lao động thì nội dung của phụ lục hợp
                      đồng lao động cũng có giá trị như các nội dung của bản hợp đồng lao động này.
                    </li>
                  </ul>
                </Grid>
              </Grid>
            </Grid>
            <Grid
              item
              container
              xs={12}
              className="pd-60"
              spacing={1}
              alignItems="center"
              style={{ marginBottom: 10 }}
            >
              <Grid item xs={7}></Grid>
              <Grid item xs={5} style={{ marginTop: '10px' }}>
                Hà Nội, ngày {new Date(formik.values.signingDate).getDate()} tháng{' '}
                {new Date(formik.values?.signingDate).getMonth() + 1} năm{' '}
                {new Date(formik.values?.signingDate).getFullYear()}
              </Grid>
            </Grid>
            <Grid
              item
              container
              xs={12}
              className="pd-60"
              spacing={0.5}
              alignItems="center"
              style={{ marginBottom: 100 }}
            >
              <Grid item xs={2}></Grid>
              <Grid item xs={4} style={{ fontWeight: 'bold' }}>
                Người lao động
              </Grid>
              <Grid item xs={2}></Grid>
              <Grid item xs={4} style={{ fontWeight: 'bold' }}>
                Người sử dụng lao động
              </Grid>
              <Grid item xs={2}></Grid>
              <Grid item xs={4} style={{ fontStyle: 'italic' }}>
                (Ký, ghi rõ họ tên)
              </Grid>
              <Grid item xs={1}></Grid>
              <Grid item xs={5} style={{ fontStyle: 'italic', textAlign: 'center' }}>
                (Ký, ghi rõ họ tên, đóng dấu)
              </Grid>
            </Grid>
            <Grid
              item
              container
              xs={12}
              className="pd-60"
              spacing={0.5}
              alignItems="center"
              justifyContent="center"
              style={{ marginBottom: 20 }}
            >
              <Grid item xs={2}></Grid>
              <Grid item xs={4} style={{ fontWeight: 'bold' }}>
                {item?.employee ? item?.employee?.fullName : item?.fullName}
              </Grid>
              <Grid item xs={2}></Grid>
              <Grid item xs={4} style={{ fontWeight: 'bold', textAlign: 'center' }}>
                {formik.values?.nameLeader}
              </Grid>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <>
            <Button variant="contained" color="secondary" onClick={handleCloseDialog}>
              Hủy
            </Button>
            {item?.employee ? (
              <>
                <Button variant="contained" color="primary" onClick={handlePrint}>
                  In
                </Button>
              </>
            ) : (
              <Button type="submit" variant="contained" color="primary">
                Tạo hợp đồng
              </Button>
            )}
          </>
        </DialogActions>
      </form>
    </Dialog>
  );
}
