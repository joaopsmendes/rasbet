import React, { useState, useEffect } from "react";
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import Container from '@mui/material/Container';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
import AdbIcon from '@mui/icons-material/Adb';
import Dialogo from './Dialogo';
import Login from './Login';
import Registo from './Registo';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import { Grid, ToggleButton } from "@mui/material";
import Select, { SelectChangeEvent } from '@mui/material/Select';


function ResponsiveAppBar(props) {
  const [anchorElNav, setAnchorElNav] = useState(null);
  const [anchorElUser, setAnchorElUser] = useState(null);


  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };
  const handleChange = (event, newAlignment) => {
    if (newAlignment !== null) {
      props.setDesportoAtivo(newAlignment);
    }
  };

  const handleSettings = (event) => {
    let handler = props.settings[event.target.innerText];
    console.log(handler());
    handleCloseUserMenu();
  }



  return (
    <AppBar position="static">
      <Container maxWidth="false">
        <Toolbar disableGutters>
          <AdbIcon sx={{ display: { xs: 'none', md: 'flex' }, mr: 1 }} />
          <Typography
            variant="h6"
            noWrap
            component="a"
            href="/"
            sx={{
              mr: 2,
              display: { xs: 'none', md: 'flex' },
              fontFamily: 'monospace',
              fontWeight: 700,
              letterSpacing: '.3rem',
              color: 'inherit',
              textDecoration: 'none',
            }}
          >
            RASBET
          </Typography>

          <AdbIcon sx={{ display: { xs: 'flex', md: 'none' }, mr: 1 }} />
          <Box sx={{ flexGrow: 1, display: { md: 'flex', xs: 'flex' }, overflow: 'auto' }}>
            <ToggleButtonGroup
              value={props.desportoAtivo}
              exclusive
              onChange={handleChange}
            >
              {props.pages.map((page) => (
                <ToggleButton
                  variant="contained"
                  key={page}
                  value={page}
                  onClick={handleCloseNavMenu}
                  style={{ borderRadius: "20px", padding: ' 2% 15%', }}
                  sx={{
                    ml: 5, mr: 5, p: 2, color: 'white', display: 'block',
                    "&.Mui-selected, &.Mui-selected:hover": {
                      color: "#000000",
                      backgroundColor: '#FFFFFF'
                    },
                  }}
                >
                  {page}
                </ToggleButton>
              ))}
            </ToggleButtonGroup>
          </Box>

          {
            props.isLogin ?
              <Box sx={{ flexGrow: 0 }}>
                <Tooltip title="Open settings">
                  <IconButton onClick={handleOpenUserMenu} size="large" color="inherit" aria-label="menu" sx={{ p: 0 }}>
                    <MenuIcon />
                  </IconButton>
                </Tooltip>
                <Menu
                  sx={{ mt: '45px' }}
                  id="menu-appbar"
                  anchorEl={anchorElUser}
                  anchorOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                  }}
                  keepMounted
                  transformOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                  }}
                  open={Boolean(anchorElUser)}
                  onClose={handleCloseUserMenu}
                >
                  {Object.keys(props.settings).map((setting) => (
                    <MenuItem key={setting} onClick={handleSettings}>
                      <Typography textAlign="center">{setting}</Typography>
                    </MenuItem>
                  ))}
                </Menu>
              </Box>
              : <Box sx={{
                display: 'flex',
                alignItems: 'flex-start',
                flexDirection: 'row',
              }}>
                <Dialogo form={<Login login={props.login} />} title="Login" />
                <Dialogo form={<Registo login={props.login} />} title="Registo" />
              </Box>
          }
        </Toolbar>
      </Container>
    </AppBar>
  );
}
export default ResponsiveAppBar;