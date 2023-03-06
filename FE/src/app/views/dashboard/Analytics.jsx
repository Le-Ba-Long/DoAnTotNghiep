import { Card, Grid, styled, useTheme } from '@mui/material';
import { Fragment, useEffect } from 'react';
import LineChart from './shared/LineChart';
import ComparisonChart from './shared/ComparisonChart';
import DoughnutChart from './shared/Doughnut';
import EmployeeBirthdayInRange from './shared/EmployeeBirthdayInRange';
import EmployeesAboutToExpireContract from './shared/EmployeesAboutToExpireContract';
import {
  getPersonnelChangeReport,
  getMonthlyEmployeeCountReport,
  getEmployeeAllocationRatioByDepartment,
  getEmployeeBirthdayInRange,
  getEmployeesAboutToExpireContract,
  exportEmployeeBirthdayInRange,
  exportEmployeesAboutToExpireContract,
} from './AnalyticsService';
import TextField from '@mui/material/TextField';
import { toast } from 'react-toastify';
import Autocomplete from '@mui/material/Autocomplete';
import { months } from '../../constant';
import { Button, IconButton } from '@mui/material';
import FileDownloadIcon from '@mui/icons-material/FileDownload';
import Checkbox from '@mui/material/Checkbox';
import Tooltip from '@mui/material/Tooltip';
import useState from 'react-usestateref';
import { saveAs } from 'file-saver';

const ContentBox = styled('div')(({ theme }) => ({
  margin: '30px',
  [theme.breakpoints.down('sm')]: { margin: '16px' },
}));

const Title = styled('span')(() => ({
  fontSize: '1rem',
  fontWeight: '500',
  marginRight: '.5rem',
  textTransform: 'capitalize',
}));

const SubTitle = styled('span')(({ theme }) => ({
  fontSize: '0.875rem',
  color: theme.palette.text.secondary,
}));

const Analytics = () => {
  const { palette } = useTheme();

  const [personnelChangeReport, setPersonnelChangeReport] = useState([]);
  const [monthlyEmployeeCountReport, setMonthlyEmployeeCountReport] = useState([]);
  const [employeeAllocationRatioByDepartment, setEmployeeAllocationRatioByDepartment] = useState(
    []
  );
  const [listEmployeeBirthdayInRange, setListEmployeeBirthdayInRange] = useState([]);
  const [startMonthObj, setStartMonthObj] = useState({ name: 'Tháng 1', value: 1 });
  const [endMonthObj, setEndMonthObj] = useState({ name: 'Tháng 2', value: 2 });
  const [reload, setReload] = useState(false);
  const [arrExpireContract, setArrExpireContract, arrExpireContractRef] = useState([1]);
  const [listEmployeesAboutToExpireContract, setListEmployeesAboutToExpireContract] = useState([]);
  const [loading1, setLoading1] = useState(false);
  const [loading2, setLoading2] = useState(false);

  useEffect(() => {
    getPersonnelChangeReport()
      .then((res) => {
        if (res?.data?.statusCode === 200) {
          setPersonnelChangeReport(res?.data?.data);
        } else {
          toast.warning(res?.data?.message);
        }
      })
      .catch((err) => toast.error('Có lỗi xảy ra'));

    getMonthlyEmployeeCountReport()
      .then((res) => {
        if (res?.data?.statusCode === 200) {
          setMonthlyEmployeeCountReport(res?.data?.data);
        } else {
          toast.warning(res?.data?.message);
        }
      })
      .catch((err) => toast.error('Có lỗi xảy ra'));

    getEmployeeAllocationRatioByDepartment()
      .then((res) => {
        if (res?.data?.statusCode === 200) {
          setEmployeeAllocationRatioByDepartment(res?.data?.data);
        } else {
          toast.warning(res?.data?.message);
        }
      })
      .catch((err) => toast.error('Có lỗi xảy ra'));
  }, []);

  useEffect(() => {
    setLoading1(true);
    getEmployeeBirthdayInRange({
      startMonth: startMonthObj?.value,
      endMonth: endMonthObj?.value,
    })
      .then((res) => {
        if (res?.data?.statusCode === 200) {
          setLoading1(false);
          setListEmployeeBirthdayInRange(res?.data?.data);
        } else {
          setLoading1(false);
          toast.warning(res?.data?.message);
        }
      })
      .catch((err) => toast.error('Có lỗi xảy ra'));
  }, [reload]);

  useEffect(() => {
    setLoading2(true);
    getEmployeesAboutToExpireContract(arrExpireContract)
      .then((res) => {
        if (res?.data?.statusCode === 200) {
          setLoading2(false);
          setListEmployeesAboutToExpireContract(res?.data?.data);
        } else {
          setLoading2(false);
          toast.warning(res?.data?.message);
        }
      })
      .catch((err) => toast.error('Có lỗi xảy ra'));
  }, [arrExpireContract.length]);

  const hanldeChangeCheckBox = (event) => {
    const checkboxId = Number(event.target.id);
    const isChecked = event.target.checked;
    let newSelectedCheckboxes = [...arrExpireContract];
    if (isChecked) {
      newSelectedCheckboxes.push(checkboxId);
    } else {
      if (newSelectedCheckboxes.length === 1) {
        event.preventDefault();
        return;
      }
      newSelectedCheckboxes = newSelectedCheckboxes.filter((id) => id !== checkboxId);
    }
    setArrExpireContract(newSelectedCheckboxes);
  };

  const handleExportFileExcel = (method) => {
    if (method === 1) {
      exportEmployeeBirthdayInRange({
        startMonth: startMonthObj?.value,
        endMonth: endMonthObj?.value,
      })
        .then((res) => {
          toast.success('Xuất file Excel thành công');
          let blob = new Blob([res.data], {
            type: `application/vnd.ms-excel`,
          });
          saveAs(
            blob,
            `Danh sách nhân viên sinh nhật tháng (${startMonthObj?.value} - ${endMonthObj?.value}) .xlsx`
          );
        })
        .catch((err) => {
          toast.error('Xuất file thất bại');
        });
    } else if (method === 2) {
      exportEmployeesAboutToExpireContract(arrExpireContract)
        .then((res) => {
          toast.success('Xuất file Excel thành công');
          let blob = new Blob([res.data], {
            type: `application/vnd.ms-excel`,
          });
          saveAs(blob, 'Danh sách nhân viên có hợp đồng sắp hết hạn.xlsx');
        })
        .catch((err) => {
          toast.error('Xuất file thất bại');
        });
    }
  };

  return (
    <Fragment>
      <ContentBox className="analytics">
        <Grid container spacing={3}>
          <Grid item lg={12} md={12} sm={12} xs={12}>
            <Card sx={{ px: 3, py: 2, mb: 3 }}>
              <Title>Tỉ lệ nhân sự phân bổ theo các phòng ban năm 2023</Title>
              <SubTitle>Công ty Oceantech</SubTitle>

              <DoughnutChart
                height="500px"
                color={[
                  '#F5EAEA',
                  '#FFB84C',
                  '#F16767',
                  '#A459D1',
                  '#4D455D',
                  '#E96479',
                  '#F5E9CF',
                  '#7DB9B6',
                  '#F9F54B',
                  '#8BF5FA',
                  '#F2CD5C',
                  '#A7727D',
                ]}
                data={employeeAllocationRatioByDepartment}
              />
            </Card>
          </Grid>
          <Grid item lg={6} md={6} sm={12} xs={12}>
            <Card sx={{ px: 3, py: 2, mb: 3 }}>
              <Title>Biến động nhân sự năm 2023</Title>
              <SubTitle>Công ty Oceantech</SubTitle>
              <LineChart
                height="350px"
                color={[palette.primary.main, palette.secondary.main]}
                data={personnelChangeReport}
              />
            </Card>
          </Grid>

          <Grid item lg={6} md={6} sm={12} xs={12}>
            <Card sx={{ px: 3, py: 2, mb: 3 }}>
              <Title>Số lượng nhân sự năm 2023</Title>
              <SubTitle>Công ty Oceantech</SubTitle>

              <ComparisonChart
                height="350px"
                color={[palette.primary.dark]}
                data={monthlyEmployeeCountReport[0]}
              />
            </Card>
          </Grid>
          <Grid item lg={12} md={12} sm={12} xs={12}>
            <Card sx={{ px: 3, py: 2, mb: 3 }}>
              <Title>Danh sách nhân viên có hợp đồng sắp hết hạn</Title>
              <Grid container width="100%" style={{ margin: '20px 0' }}>
                <Grid item xs={3.5} style={{ border: '1px solid #f1c40f' }}>
                  <Checkbox
                    defaultChecked
                    id="1"
                    checked={arrExpireContract.includes(1)}
                    onChange={hanldeChangeCheckBox}
                    sx={{
                      color: '#f1c40f',
                      '&.Mui-checked': {
                        color: '#f1c40f',
                      },
                    }}
                  />
                  <span style={{ color: '#f1c40f' }}>Sắp hết hạn</span>
                </Grid>
                <Grid item xs={3.5} style={{ border: '1px solid #ff9e43', margin: '0 5px' }}>
                  <Checkbox
                    id="2"
                    checked={arrExpireContract.includes(2)}
                    onChange={hanldeChangeCheckBox}
                    sx={{
                      color: '#ff9e43',
                      '&.Mui-checked': {
                        color: '#ff9e43',
                      },
                    }}
                  />
                  <span style={{ color: '#ff9e43' }}>Đến hạn</span>
                </Grid>
                <Grid item xs={3.5} style={{ border: '1px solid #f44336' }}>
                  <Checkbox
                    id="3"
                    checked={arrExpireContract.includes(3)}
                    onChange={hanldeChangeCheckBox}
                    sx={{
                      color: '#f44336',
                      '&.Mui-checked': {
                        color: '#f44336',
                      },
                    }}
                  />
                  <span style={{ color: '#f44336' }}>Quá hạn</span>
                </Grid>
                {/* <Grid item xs={3.5}></Grid> */}
                <Grid item xs={0.5} style={{ marginLeft: '10px' }}>
                  <Tooltip title="Xuất file Excel" onClick={() => handleExportFileExcel(2)}>
                    <IconButton color="primary">
                      <FileDownloadIcon />
                    </IconButton>
                  </Tooltip>
                </Grid>
              </Grid>
              <EmployeesAboutToExpireContract
                listEmployeesAboutToExpireContract={listEmployeesAboutToExpireContract}
                loading={loading2}
              />
            </Card>
          </Grid>
          <Grid item lg={12} md={12} sm={12} xs={12}>
            <Card sx={{ px: 3, py: 2, mb: 3 }}>
              <Title>Danh sách nhân viên sinh nhật trong tháng</Title>
              <Grid container width="100%" style={{ margin: '20px 0' }}>
                <Grid item xs={2}>
                  <Autocomplete
                    options={months}
                    getOptionLabel={(option) => option.name}
                    value={startMonthObj}
                    onChange={(event, newValue) => setStartMonthObj(newValue)}
                    fullWidth
                    size="small"
                    renderInput={(params) => (
                      <TextField {...params} variant="outlined" fullWidth label="Tháng" />
                    )}
                  />
                </Grid>
                <Grid item style={{ lineHeight: 3, margin: '0 5px' }}>
                  đến
                </Grid>
                <Grid item xs={2}>
                  <Autocomplete
                    options={months}
                    getOptionLabel={(option) => option.name}
                    value={endMonthObj}
                    onChange={(event, newValue) => setEndMonthObj(newValue)}
                    fullWidth
                    size="small"
                    renderInput={(params) => (
                      <TextField {...params} variant="outlined" fullWidth label="Tháng" />
                    )}
                  />
                </Grid>
                <Grid item style={{ marginLeft: '10px' }}>
                  <Button variant="contained" color="primary" onClick={() => setReload(!reload)}>
                    Lọc
                  </Button>
                </Grid>
                <Grid item xs={6}></Grid>
                <Grid item xs={0.5}>
                  <Tooltip title="Xuất file Excel">
                    <IconButton color="primary" onClick={() => handleExportFileExcel(1)}>
                      <FileDownloadIcon />
                    </IconButton>
                  </Tooltip>
                </Grid>
              </Grid>
              <EmployeeBirthdayInRange
                listEmployeeBirthdayInRange={listEmployeeBirthdayInRange}
                loading={loading1}
              />
            </Card>
          </Grid>
        </Grid>
      </ContentBox>
    </Fragment>
  );
};

export default Analytics;
