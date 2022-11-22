import React,{useState,useEffect} from "react";
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import IconButton from '@mui/material/IconButton';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';


function HistoricoApostas(props){
  

  return(
      <div className = "Historico"> 
        <h1> {props.nome} </h1>
        <h2> Histórico de apostas </h2>
        <Stack direction="row" spacing={2}>
          <Button variant="disable">Simples</Button>
          <Button variant="disable" disable>Múltiplas</Button>
          <Fab color="primary" aria-label="add">
            <AddIcon/>
          </Fab>
        </Stack>
      </div>
  );
}

export default HistoricoApostas;
