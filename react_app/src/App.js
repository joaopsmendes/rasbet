import './App.css';
import React, { useState, useEffect } from 'react';

import PageUtilizador from './components/PageUtilizador';
import ThemeProvider from '@mui/material/styles/ThemeProvider';
import { createTheme } from '@mui/material/styles';
import PageEspecialista from './components/Especialista/PageEspecialista';
import { Button, CssBaseline, GlobalStyles } from "@mui/material";


const defaultTheme = createTheme();

const theme = createTheme({
  components: {
    MuiToggleButton: {
      styleOverrides: {
        root: {
          "&.Mui-selected, &.Mui-selected:hover": {
            color: "#FFFFFF",
            backgroundColor: '#E67644'
          },
        }
      }
    },
    MuiSelect: {
      styleOverrides: {
        root: {
          "&.Mui-selected,": {
            color: "#FFFFFF",
          },
        }
      }
    },
    Box: {
      styleOverrides: {
        root: {
          bgcolor: "#FFFFFF"
        },
      }
    },
  },
  palette: {
    background: {
      default: "#FFFFFF"
    },
    type: 'light',
    primary: {
      main: '#1A3712',
    },
    secondary: {
      main: '#E67644',
    },
    neutral: {
      light: '#ffa726',
      main: '#FFFFFF',
      dark: '#ef6c00',
      contrastText: 'rgba(0, 0, 0, 0.87)'
    }
    ,
    white: {
      main: '#FFFFFF'
    }
  },
});



function App() {

  const [isUser, setUser] = useState(true);
  const [isAdmin, setAdmin] = useState(false);
  const [isEspecialista, setEspecialista] = useState(false);
  const [login, setLogin] = useState(false);
  const [username, setUsername] = useState("");


  useEffect(() => {
    if (sessionStorage.getItem('user') && !login) {
      const user = JSON.parse(sessionStorage.getItem('user'));
      const tipo = JSON.parse(sessionStorage.getItem('tipo'));
      handleLogin(user, tipo);  
    }
  }, []);


  const handleLogin = (user, tipo) => {
    setLogin(true);
    handleUser(tipo);
    sessionStorage.setItem('user', JSON.stringify(user));
    setUsername(user);
  }

  const handleLogout = () => {
    console.log("LOGOUT");
    setLogin(false);
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('tipo');
    setFalse();
    setUser(true);
    setUsername(null);
  }


  const setFalse = () => {
    setUser(false);
    setAdmin(false);
    setEspecialista(false);
  }

  function handleUser(tipo) {
    setUser(false);
    setAdmin(false);
    setEspecialista(false);
    if (tipo === "Apostador") setUser(true);
    if (tipo === "Especialista") setEspecialista(true);
    if (tipo === "Adminstrador") setAdmin(true);
    if (!sessionStorage.getItem('user')) {
      sessionStorage.setItem('tipo', JSON.stringify(tipo));
    }

  }


  return (
    <div className="App">
      <ThemeProvider theme={theme}>
        <CssBaseline />
        {isUser && <PageUtilizador user={username} isLogin={login} login={handleLogin} loggout={handleLogout} />}
        {isEspecialista && <PageEspecialista isLogin={login} loggout={handleLogout} />}
      </ThemeProvider>
    </div>
  );
}

export default App;
