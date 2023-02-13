import { styled } from '@mui/system';
import { MatxVerticalNav } from 'app/components';
import useSettings from 'app/hooks/useSettings';
import { navigations } from 'app/navigations';
import { useEffect } from 'react';
import { useState } from 'react';
import { Fragment } from 'react';
import Scrollbar from 'react-perfect-scrollbar';

const StyledScrollBar = styled(Scrollbar)(() => ({
  paddingLeft: '1rem',
  paddingRight: '1rem',
  position: 'relative',
}));

const SideNavMobile = styled('div')(({ theme }) => ({
  position: 'fixed',
  top: 0,
  left: 0,
  bottom: 0,
  right: 0,
  width: '100vw',
  background: 'rgba(0, 0, 0, 0.54)',
  zIndex: -1,
  [theme.breakpoints.up('lg')]: { display: 'none' },
}));

const Sidenav = ({ children }) => {
  const { settings, updateSettings } = useSettings();
  const [nav, setNav] = useState([]);

  const role = localStorage.getItem('role');

  useEffect(() => {
    let newArr = [];
    navigations.map((item) => {
      item.role.map((item1) => {
        if (item1 === role) {
          newArr.push(item);
        }
      });
    });
    setNav(newArr);
  }, [navigations]);

  const updateSidebarMode = (sidebarSettings) => {
    let activeLayoutSettingsName = settings.activeLayout + 'Settings';
    let activeLayoutSettings = settings[activeLayoutSettingsName];

    updateSettings({
      ...settings,
      [activeLayoutSettingsName]: {
        ...activeLayoutSettings,
        leftSidebar: {
          ...activeLayoutSettings.leftSidebar,
          ...sidebarSettings,
        },
      },
    });
  };

  return (
    <Fragment>
      <StyledScrollBar options={{ suppressScrollX: true }}>
        {children}
        <MatxVerticalNav items={nav} />
      </StyledScrollBar>

      <SideNavMobile onClick={() => updateSidebarMode({ mode: 'close' })} />
    </Fragment>
  );
};

export default Sidenav;
