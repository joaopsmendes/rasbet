import React, { useState, useEffect } from "react";
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import IconButton from '@mui/material/IconButton';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import { Divider, Grid, ToggleButton } from "@mui/material";
import Apostas from "./Apostas";
import Box from '@mui/material/Box';


function HistoricoApostas(props) {

  const [ativas, setAtivas] = useState(true);
  const [finalizadas, setFinalizadas] = useState(false);
  const [simples, setSimples] = useState(true)
  const [multipla, setMultipla] = useState(false)
  const [update, setUpdate] = useState(false)


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

  useEffect(() => {
  }, [simples,ativas])





  return (

    <div className="Historico">
      <Container maxWidth="lg" sx={{ p: 2 }}>
        <Box sx={{ border: 2, borderRadius: '20px' }} >
          <h2> Histórico de apostas </h2>
          <Container maxWidth="xs" >
            <Grid container spacing={2} >
              <Grid item xs={6} >
                <Button fullWidth onClick={handleAtivas} sx={{ mt: '2%', mb: '2%' }} variant="contained" size="md" color={ativas ? "primary" : "inherit"} value="Ativas">Ativas</Button>
              </Grid>
              <Grid item xs={6} >
                <Button fullWidth onClick={handleFinalizadas} sx={{ mt: '2%', mb: '2%' }} variant="contained" size="md" color={finalizadas ? "primary" : "inherit"} value="Ativas"> Finalizadas</Button>
              </Grid>
            </Grid>
          </Container>
          <Divider />
          <Container maxWidth="xs" >
            <Grid container spacing={2} >
              <Grid item xs={6} >
                <Button fullWidth onClick={handleSimples} sx={{ mt: '2%', mb: '2%' }} variant="contained" size="xs" color={simples ? "primary" : "inherit"} value="Simples">Simples</Button>
              </Grid>
              <Grid item xs={6} >
                <Button fullWidth onClick={handleMultipla} sx={{ mt: '2%', mb: '2%' }} variant="contained" size="xs" color={multipla ? "primary" : "inherit"} value="Multipla">Múltipla</Button>
              </Grid>
            </Grid>
          </Container>
          <Divider />
          <Apostas filtro={props.filtro} dataInicial={props.dataInicial} dataFinal={props.dataFinal} ativas={ativas} simples={simples} />
        </Box>
      </Container>
    </div>

  );
}

export default HistoricoApostas;
