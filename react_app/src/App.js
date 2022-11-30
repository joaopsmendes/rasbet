import './App.css';
import React, { useState } from 'react';

import PageUtilizador from './components/PageUtilizador';
import ThemeProvider from '@mui/material/styles/ThemeProvider';
import { createTheme } from '@mui/material/styles';
import PageEspecialista from './components/Especialista/PageEspecialista';


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
    }
  },
  palette: {
    mode: 'light',
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
  },
});


function App() {

  const [isUser, setUser] = useState(false);
  const [isAdmin, setAdmin] = useState(false);
  const [isEspecialista, setEspecialista] = useState(true);





  return (
    <div className="App">
      <ThemeProvider theme={theme}>
        {isUser && <PageUtilizador setAdmin={setAdmin} setEspecialista={setEspecialista} />}
        {isEspecialista && <PageEspecialista />}
      </ThemeProvider>
    </div>
  );
}

export default App;
