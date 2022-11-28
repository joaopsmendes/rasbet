import './App.css';
import React, { useState } from 'react';

import PageUtilizador from './components/PageUtilizador';
import CssBaseline from '@mui/material/CssBaseline';

function App() {
  
  const [isUser,setUser]=useState(true);
  const [isAdmin,setAdmin]=useState(false);
  const [isEspecialista,setEspecialista]=useState(false);





  return (
    <div className="App">
      <CssBaseline />
      {isUser && <PageUtilizador setAdmin={setAdmin} setEspecialista={setEspecialista} />}
      
    </div>
  );
}

export default App;
