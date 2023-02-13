import { LoadingButton } from '@mui/lab';
import { Card, Checkbox, Grid, TextField } from '@mui/material';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import { Box, styled, useTheme } from '@mui/system';
import { Paragraph } from 'app/components/Typography';
import useAuth from 'app/hooks/useAuth';
import { Formik } from 'formik';
import { useState } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import * as Yup from 'yup';

const FlexBox = styled(Box)(() => ({ display: 'flex', alignItems: 'center' }));

const JustifyBox = styled(FlexBox)(() => ({ justifyContent: 'center' }));

const ContentBox = styled(Box)(() => ({
  height: '100%',
  padding: '32px',
  position: 'relative',
  background: 'rgba(0, 0, 0, 0.01)',
}));

const JWTRoot = styled(JustifyBox)(() => ({
  background: '#1A2038',
  minHeight: '100% !important',
  '& .card': {
    maxWidth: 800,
    minHeight: 400,
    margin: '1rem',
    display: 'flex',
    borderRadius: 12,
    alignItems: 'center',
  },
}));

// inital login credentials
const initialValues = {
  // userName: 'jason@ui-lib.com',
  // password: 'dummyPass',
  // remember: true,
  userName: 'longkubi123',
  password: '123456',
};

// form field validation schema
const validationSchema = Yup.object().shape({
  password: Yup.string().min(6, 'Mật khẩu ít nhất 6 ký tự!').required('Bạn chưa nhập mật khẩu!'),
  userName: Yup.string().required('Bạn chưa nhập tên đăng nhập!'),
});

const JwtLogin = () => {
  const theme = useTheme();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const { login } = useAuth();

  const handleFormSubmit = async (values) => {
    setLoading(true);
    try {
      const res = await login(values.userName, values.password);
      navigate('/');
    } catch (e) {
      setLoading(false);
    }
  };

  return (
    <JWTRoot>
      <Dialog open={true} fullWidth maxWidth={'sm'}>
        <DialogContent>
          <Grid container>
            <Grid item sm={6} xs={12}>
              <JustifyBox height="100%" sx={{ width: '100%' }}>
                <img
                  src="https://images.squarespace-cdn.com/content/v1/5d13cec9626d0b0001fd119c/1562269721664-C4QGYNS07KD61WGN7WQI/EcoTech+Logo+2.png"
                  width="100%"
                  alt=""
                />
              </JustifyBox>
            </Grid>

            <Grid item sm={6} xs={12}>
              <ContentBox>
                <Formik
                  onSubmit={handleFormSubmit}
                  initialValues={initialValues}
                  validationSchema={validationSchema}
                >
                  {({ values, errors, touched, handleChange, handleBlur, handleSubmit }) => (
                    <form
                      onSubmit={handleSubmit}
                      style={{
                        display: 'flex',
                        flexDirection: 'column',
                        justifyContent: 'center',
                        height: '100%',
                      }}
                    >
                      <TextField
                        fullWidth
                        size="small"
                        type="text"
                        name="userName"
                        label="Tên đăng nhập"
                        variant="outlined"
                        onBlur={handleBlur}
                        value={values.userName}
                        onChange={handleChange}
                        helperText={touched.userName && errors.userName}
                        error={Boolean(errors.userName && touched.userName)}
                        sx={{ mb: 3 }}
                      />

                      <TextField
                        fullWidth
                        size="small"
                        name="password"
                        type="password"
                        label="Mật khẩu"
                        variant="outlined"
                        onBlur={handleBlur}
                        value={values.password}
                        onChange={handleChange}
                        helperText={touched.password && errors.password}
                        error={Boolean(errors.password && touched.password)}
                        sx={{ mb: 1.5 }}
                      />

                      <Box style={{ display: 'flex', justifyContent: 'center' }}>
                        <LoadingButton
                          type="submit"
                          color="primary"
                          loading={loading}
                          variant="contained"
                          sx={{ my: 2 }}
                          style={{ width: '30%' }}
                        >
                          Login
                        </LoadingButton>
                      </Box>
                    </form>
                  )}
                </Formik>
              </ContentBox>
            </Grid>
          </Grid>
        </DialogContent>
      </Dialog>
    </JWTRoot>
  );
};

export default JwtLogin;
