import React,{useState,useEffect} from "react";
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
import { ToggleButton } from "@mui/material";

//const pages = ['Products', 'Pricing', 'Blog'];
const settings = ['Perfil', 'Histórico',  'Terminar Sessão'];

function ResponsiveAppBar(props) {
  const [anchorElNav, setAnchorElNav] = useState(null);
  const [anchorElUser, setAnchorElUser] =useState(null);
  const [alignment, setAlignment] = useState();


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
    setAlignment(newAlignment);
  };

  const handleSettings = (event) => {
    if(event.target.innerText === 'Histórico'){
      props.historico();
    }
    if(event.target.innerText === 'Perfil'){
      props.perfil();
    }
    if(event.target.innerText === 'Terminar Sessão'){
      props.logout();
    }
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

          <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>

          </Box>
          <AdbIcon sx={{ display: { xs: 'flex', md: 'none' }, mr: 1 }} />
          <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
          <ToggleButtonGroup
              fullWidth={true}
              color="warning"
              value={alignment}
              exclusive
              onChange={handleChange}
              >
            {props.pages.map((page) => (
              <ToggleButton
                variant="contained"
                key={page}
                value={page}
                onClick={handleCloseNavMenu}
                sx={{ my: 2, color: 'white', display: 'block' }}
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
              <IconButton onClick={handleOpenUserMenu} size="large"  color="inherit" aria-label="menu" sx={{ p: 0}}>
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
              {settings.map((setting) => (
                <MenuItem key={setting} onClick={handleSettings}>
                  <Typography textAlign="center">{setting}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>
          :<div>
            <Dialogo form={<Login login={props.login}/>} title="Login"/>
            <Dialogo form={<Registo/>} title="Registo"/>
           </div>
           }
        </Toolbar>
      </Container>
    </AppBar>
  );
}
export default ResponsiveAppBar;