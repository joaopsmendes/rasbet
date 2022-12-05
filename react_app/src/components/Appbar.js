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
import logo from '../rasbet.png';
import Link from "@mui/material/Link";
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import NativeSelect from '@mui/material/NativeSelect';
import { Select } from "@mui/material";
import NotificationsIcon from '@mui/icons-material/Notifications';
import Favoritos from "./Favoritos";

function ResponsiveAppBar(props) {
  const [anchorElNav, setAnchorElNav] = useState(null);
  const [anchorElUser, setAnchorElUser] = useState(null);
  const [fav, setFav] = useState(false);
  const [not, setNot] = useState(false);



  const getNotifications = async (email) => {
    console.log(email)
    const response = await fetch('http://localhost:8080/getNotifications?' + new URLSearchParams({
      userId: email
    })
      , {
        method: 'GET',
      });

    console.log("Notificações");
    let data = await response.json();
    console.log(data);
    setNot(data['not']);
  }

  const getFavoritos = async (email) => {
    console.log(email)
    const response = await fetch('http://localhost:8080/getNotifications?' + new URLSearchParams({
      userId: email
    })
      , {
        method: 'GET',
      });

    console.log("Notificações");
    let data = await response.json();
    console.log(data);
    setFav(data['fav']);
  }

  const handleOpenNotifications = (event) => {
    //setAnchorElUser(event.currentTarget);
    //getFavoritos(props.user.email);
  };


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

  const handleChangeSelect = (event) => {
    props.setDesportoAtivo(event.target.value);
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
          <Link href="/">
            <Box
              component="img"
              sx={{
                display: 'flex',
                maxHeight: { xs: 80, md: 167 },
                maxWidth: { xs: 80, md: 250 },
              }}
              alt="The house from the offer."
              src={logo}
            /></Link>
          <Box sx={{ flexGrow: 1, display: { md: 'flex', xs: 'none' }, overflow: 'auto' }}>
            {props.showDesportos &&
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
                    color="secondary"
                    sx={{
                      ml: 5, mr: 5, p: 2, display: 'block', color: "#FFFFFF",
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
            }
          </Box>
          <Box sx={{ flexGrow: 1, display: { md: 'none', xs: 'flex' }, overflow: 'auto' }}>
            {props.showDesportos &&
              <FormControl fullWidth>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  value={props.desportoAtivo}
                  onChange={handleChangeSelect}
                >
                  {props.pages.map((page) => (
                    <MenuItem value={page} color="secondary">{String(page).toUpperCase()}</MenuItem>
                  ))}
                </Select>
              </FormControl>
            }
          </Box>
          {props.showFavoritos &&
            <Box >
              <Favoritos
                participantes={props.favoritos}
                setFavoritos={props.setFavoritos}
                favoritos={props.favoritos}
                desporto={props.desportoAtivo}
                showFavoritos={true} />
            </Box>
          }
          {
            props.isLogin && props.showFavoritos &&
            <Box sx={{ flexGrow: 0 }}>
              <Tooltip title="Open notifications">
                <IconButton onClick={handleOpenNotifications} size="large" color="inherit" aria-label="notifications" sx={{ p: 0 }}>
                  <NotificationsIcon />
                </IconButton>
              </Tooltip>
            </Box>
          }
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
                flexDirection: { xs: 'column', md: 'row' },
                justifyContent: 'center',
                alignItems: 'center'
              }}>
                <Dialogo form={<Login login={props.login} />} title="Iniciar Sessão" />
                <Dialogo form={<Registo login={props.login} />} title="Registo" />
              </Box>
          }
        </Toolbar>
      </Container>
    </AppBar >
  );
}
export default ResponsiveAppBar;