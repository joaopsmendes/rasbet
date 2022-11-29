import './App.css';
import React, { useState } from 'react';

import PageUtilizador from './components/PageUtilizador';
import CssBaseline from '@mui/material/CssBaseline';
import ThemeProvider from '@mui/material/styles/ThemeProvider';
import { createTheme } from '@mui/material/styles';


const defaultTheme = createTheme();

const theme = createTheme({
  components: {
    MuiButton: {
      variants: [
        {
          props: { variant: 'dashed' },
          style: {
            textTransform: 'none',
            border: `2px dashed ${defaultTheme.palette.primary.main}`,
            color: defaultTheme.palette.primary.main,
          },
        },
      ]
    },
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
  },
});


function App() {

  const [isUser, setUser] = useState(true);
  const [isAdmin, setAdmin] = useState(false);
  const [isEspecialista, setEspecialista] = useState(false);





  return (
    <div className="App">
      <ThemeProvider theme={theme}>
        {isUser && <PageUtilizador setAdmin={setAdmin} setEspecialista={setEspecialista} />}
      </ThemeProvider>
    </div>
  );
}

export default App;
