import React,{useState,useEffect} from "react";
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import IconButton from '@mui/material/IconButton';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import { ToggleButton } from "@mui/material";
import HistoricoApostas from "./HistoricoApostas";
import Grid from '@mui/material/Grid';
import Deposito from "./Deposito";
import Dialogo from "./Dialogo";
import Registo from "./Registo";
import Levantamento from "./Levantamento";
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';



function Transacoes(props) {
    

    const [deposito,setDeposito]=useState(false)
    const [levantamento,setLevantamento]=useState(false)
    const [montante,setMontante]=useState(1)
    

    //const paperStyle={padding:'50px 50px', width:600,bmargin:"50px auto", justification: 'center',alignItems: "center"}
    const paperStyle={height: "50%", display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center"}



    const handleClickDeposito = () => {
      setDeposito(!deposito);
      setLevantamento(false);
    };

    const handleClickLevantamento = () => {
      setLevantamento(!levantamento);
      setDeposito(false);
    };


    const doDeposito = async(user,montante)=>{
      console.log("DO DEPOSITO");
      console.log(montante);
      const response = await fetch('http://localhost:8080/deposito', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json',
          },
          body: JSON.stringify({
              id: user,
              value: montante,
          }),
      });
      if (response.status === 200) {
        props.getSaldo(user);
      }
      else{
        alert("Erro ao depositar");
      }

    }

    const doLevantamento = async(user,montante)=>{
      const response = await fetch('http://localhost:8080/levantamento', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            id: user,
            value: montante,
        }),
    });
    if (response.status === 200) {
      props.getSaldo(user);
    }
    else{
      alert("Saldo insuficiente");
    }

    }


   const handleSubmit = async (event) => {
    event.preventDefault();
    const user = JSON.parse(sessionStorage.getItem('user'));
    if(deposito){
      doDeposito(user,montante);
    }
    if(levantamento){
      doLevantamento(user,montante);
    }
}


    const getMax = () =>{
      return deposito ? 1000 : props.saldo;
    }
    
    const montanteCondition = () =>{
      return montante <= 0 || montante > getMax();
    }


    return (
        <div className="transacoes">
            <Grid container spacing={2}>
                <Grid item xs={6} >
                    <Button color={deposito ? "primary" : "inherit"} onClick={handleClickDeposito} variant="contained" size="" value="Deposito">Depósito</Button>
                </Grid>
                <Grid item xs={6} >
                    <Button color={levantamento ? "primary" : "inherit"} onClick={handleClickLevantamento} variant="contained" size="md" value="Levantamento">Levantamento</Button>
                </Grid>
            </Grid>

            {(deposito || levantamento) &&
                <Box component="form" onSubmit={handleSubmit} noValidate>
                    <TextField margin="normal" required name="Montante" label="Montante" id="Montante"
                        type="number"
                        defaultValue={1}
                        error={montanteCondition()}
                        helperText={montanteCondition() ? 'Valor inválido' : ' '}
                        onChange={(e) => setMontante(e.target.value)}
                        InputProps={{ inputProps: { min: 0.01, max: getMax() } }} />
                    <Button
                        disabled={montanteCondition()}
                        type="submit"
                        variant="contained"
                        sx={{ mt: 3, mb: 2, ml: 3 }}
                    >
                        Submeter
                    </Button>
                </Box>
      }
      </div>
    );
}

export default Transacoes;