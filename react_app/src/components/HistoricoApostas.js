import React, { useState, useEffect } from "react";
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import IconButton from '@mui/material/IconButton';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import { Divider, ToggleButton } from "@mui/material";
import Apostas from "./Apostas";
import Box from '@mui/material/Box';


function HistoricoApostas(props) {

  const [ativas, setAtivas] = useState(true);
  const [finalizadas, setFinalizadas] = useState(false);
  const [simples, setSimples] = React.useState(true)
  const [multipla, setMultipla] = React.useState(false)


  const handleAtivas = () => {
    setAtivas(true);
    setFinalizadas(false);
  }

  const handleFinalizadas = () => {
    setAtivas(false);
    setFinalizadas(true);
  }

  const handleSimples = () => {
    setSimples(true);
    setMultipla(false);
  }

  const handleMultipla = () => {
    setSimples(false);
    setMultipla(true);
  }
  

  return (

    <div className="Historico">
      <Container maxWidth="lg">
        <Box sx={{ border: 3, borderRadius: '15%',  margin: 'auto' }} >
          <h2> Histórico de apostas </h2>
            <Button onClick={handleAtivas} sx={{ m: 2 }} variant="contained" size="md" color={ativas ? "primary" : "inherit"} value="Ativas">Ativas</Button>
            <Button onClick={handleFinalizadas} sx={{ m: 2 }} variant="contained" size="md" color={finalizadas ? "primary" : "inherit"} value="Ativas"> Finalizadas</Button>
            <Divider />
            
            <Button onClick={handleSimples} sx={{ m: 2 }} variant="contained" size="md" color={simples ? "primary" : "inherit"} value="Simples">Simples</Button>
            <Button onClick={handleMultipla} sx={{ m: 2 }} variant="contained" size="md" color={multipla ? "primary" : "inherit"} value="Multipla">Múltipla</Button>
          {
            <Apostas ativas={ativas} simples={simples}/>
          }
        </Box>
      </Container>
    </div>

  );
}

export default HistoricoApostas;
