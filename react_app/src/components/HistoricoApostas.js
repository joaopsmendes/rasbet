import React, { useState, useEffect } from "react";
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import IconButton from '@mui/material/IconButton';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import { ToggleButton } from "@mui/material";
import Apostas from "./Apostas";
import Box from '@mui/material/Box';


function HistoricoApostas(props) {

  const [simples, setSimples] = React.useState(false)
  const [multipla, setMultipla] = React.useState(false)




  return (

    <div className="Historico">
      <Container maxWidth="lg">
        <Box sx={{ border: 3, borderRadius: '15%',  margin: 'auto' }} >
          <h2> Histórico de apostas </h2>
          <Button sx={{ m: 2 }} variant="contained" size="md" color={simples ? "primary" : "inherit"} value="Simples">Simples</Button>
          <Button sx={{ m: 2 }} variant="contained" size="md" color={multipla ? "primary" : "inherit"} value="Multipla">Múltipla</Button>
          {
            <Apostas simples={setSimples} multipla={setMultipla} />
          }
        </Box>
      </Container>
    </div>

  );
}

export default HistoricoApostas;
