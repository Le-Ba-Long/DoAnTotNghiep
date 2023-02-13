import React, { createContext, useEffect, useReducer } from 'react';
import jwtDecode from 'jwt-decode';
import axios from 'axios.js';
import { MatxLoading } from 'app/components';
import { API } from '../constant';
import { toast } from 'react-toastify';

const initialState = {
  isAuthenticated: false,
  isInitialised: false,
  user: null,
};

const isValidToken = (accessToken) => {
  if (!accessToken) {
    return false;
  }
  const decodedToken = jwtDecode(accessToken);
  const currentTime = Date.now() / 1000;
  return decodedToken.exp > currentTime;
};

const setSession = (accessToken) => {
  if (accessToken) {
    localStorage.setItem('accessToken', accessToken);
    axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
  } else {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('role');
    localStorage.removeItem('account');
    delete axios.defaults.headers.common['Authorization'];
  }
};

const reducer = (state, action) => {
  switch (action.type) {
    case 'INIT': {
      const { isAuthenticated, user } = action.payload;
      return {
        ...state,
        isAuthenticated,
        isInitialised: true,
        user,
      };
    }
    case 'LOGIN': {
      const { user } = action.payload;
      return {
        ...state,
        isAuthenticated: true,
        user,
      };
    }
    case 'LOGOUT': {
      return {
        ...state,
        isAuthenticated: false,
        user: null,
      };
    }
    case 'REGISTER': {
      const { user } = action.payload;

      return {
        ...state,
        isAuthenticated: true,
        user,
      };
    }
    default: {
      return { ...state };
    }
  }
};

const AuthContext = createContext({
  ...initialState,
  method: 'JWT',
  login: () => Promise.resolve(),
  logout: () => {},
});

export const AuthProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  const login = async (username, password) => {
    const response = await axios
      .post(API + '/api/auth/login', {
        username,
        password,
      })
      // .then((res) => {
      //   toast.success('Đăng nhập thành công');
      // })
      .catch((err) => {
        if (err.code === 400) {
          toast.error(err.error);
        } else {
          toast.error('Có lỗi xảy ra!');
        }
      });

    if (response?.data?.code === 200) {
      toast.success('Đăng nhập thành công');
    }
    const { accessToken, name, roles } = response.data;

    const obj = {
      username: username,
      password: password,
    };
    localStorage.setItem('role', roles[0].authority);
    localStorage.setItem('account', JSON.stringify(obj));

    setSession(accessToken);

    dispatch({
      type: 'LOGIN',
      payload: {
        user: {
          name,
          role: roles[0].authority,
        },
      },
    });
  };

  // const register = async (email, username, password) => {
  //   const response = await axios.post('/api/auth/register', {
  //     email,
  //     username,
  //     password,
  //   });

  //   const { accessToken, user } = response.data;

  //   setSession(accessToken);

  //   dispatch({
  //     type: 'REGISTER',
  //     payload: {
  //       user,
  //     },
  //   });
  // };

  const logout = () => {
    setSession(null);
    dispatch({ type: 'LOGOUT' });
  };

  useEffect(() => {
    (async () => {
      try {
        const accessToken = window.localStorage.getItem('accessToken');
        const account = JSON.parse(localStorage.getItem('account'));
        if (accessToken && isValidToken(accessToken)) {
          setSession(accessToken);
          const response = await axios.post(API + '/api/auth/checkToken', account);
          const user = response.data;
          dispatch({
            type: 'INIT',
            payload: {
              isAuthenticated: true,
              user: { ...user, role: user.roles[0].authority },
            },
          });
        } else {
          dispatch({
            type: 'INIT',
            payload: {
              isAuthenticated: false,
              user: null,
            },
          });
        }
      } catch (err) {
        console.error(err);
        dispatch({
          type: 'INIT',
          payload: {
            isAuthenticated: false,
            user: null,
          },
        });
      }
    })();
  }, []);

  if (!state.isInitialised) {
    return <MatxLoading />;
  }

  return (
    <AuthContext.Provider
      value={{
        ...state,
        method: 'JWT',
        login,
        logout,
        // register,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
