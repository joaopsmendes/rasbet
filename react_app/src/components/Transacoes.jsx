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
import HistoricoApostas from "./HistoricoApostas";
import Grid from '@mui/material/Grid';
import Dialogo from "./Dialogo";
import Registo from "./Registo";
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import { Gif } from "@mui/icons-material";
import CheckBoxOutlineBlankIcon from '@mui/icons-material/CheckBoxOutlineBlank';
import CheckBoxIcon from '@mui/icons-material/CheckBox';



function Transacoes(props) {


  const [deposito, setDeposito] = useState(false)
  const [levantamento, setLevantamento] = useState(false)
  const [montante, setMontante] = useState(1)
  const [promocoes, setPromocoes] = useState([])
  const [promoSelecionada, setPromoSelecionada] = useState(-1)


  //const paperStyle={padding:'50px 50px', width:600,bmargin:"50px auto", justification: 'center',alignItems: "center"}

  const handleClickDeposito = () => {
    setDeposito(!deposito);
    getListaPromocoes();
    setLevantamento(false);
  };

  const handleClickLevantamento = () => {
    setLevantamento(!levantamento);
    setDeposito(false);
  };


  const doDeposito = async (montante) => {
    const sessionId = JSON.parse(sessionStorage.getItem('sessionId'));
    const response = await fetch('http://localhost:8080/deposito', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        sessionId: sessionId,
        value: montante,
        promocao: promoSelecionada,
      }),
    });
    if (response.status === 200) {
      update();
    }
    else {
      alert("Erro ao depositar");
    }
  }

  const doLevantamento = async (montante) => {
    const sessionId = JSON.parse(sessionStorage.getItem('sessionId'));
    console.log(sessionId);
    const response = await fetch('http://localhost:8080/levantamento', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        sessionId: sessionId,
        value: montante,
      }),
    });
    if (response.status === 200) {
      update();
    }
    else {
      alert("Saldo insuficiente");
    }

  }

  const update = () => {
    setPromoSelecionada(-1);
    setMontante(1);
    props.getSaldo();
    setDeposito(false);
    setLevantamento(false);
  }




  const handleSubmit = async (event) => {
    event.preventDefault();
    if (deposito) {
      doDeposito(montante);
    }
    if (levantamento) {
      doLevantamento(montante);
    }
  }


  const getMax = () => {
    return deposito ? 1000 : props.saldo;
  }

  const montanteCondition = () => {
    return montante <= 0 || montante > getMax();
  }



  // TO CHECK
  const getListaPromocoes = async () => {
    const sessionId = JSON.parse(sessionStorage.getItem('sessionId'));
    const response = await fetch('http://localhost:8080/promosDeposito?' +
      new URLSearchParams({
        sessionId: sessionId,
      }));
    if (response.status === 200) {
      const promocoes = await response.json();
      console.log(promocoes);
      promocoes.forEach(promo => {
        promo.deposito = parseFloat(promo.deposito);
        promo.idPromocao = parseInt(promo.idPromocao);
        promo.freeBets = parseFloat(promo.freeBets);
      });

      setPromocoes(promocoes);
    }
    else {
      alert("Erro ao obter lista de promoções");
    }
  }

  const handleClickPromocao = async (event) => {
    if (event.currentTarget.value === promoSelecionada) {
      setPromoSelecionada(-1);
    }
    else setPromoSelecionada(event.currentTarget.value);
  }


  const changeMontante = (event) => {
    // get promo selecionada
    promocoes.forEach(promo => {
      if (promo.idPromocao == promoSelecionada) {
        console.log("Montante: " + event.target.value);
        if (event.target.value < promo.deposito) {
          setPromoSelecionada(-1);
          console.log("Montante menor que o minimo");
        }
      }
    });
    setMontante(event.target.value);
  }


  return (
    <div className="transacoes">
      <Grid container spacing={2}>
        <Grid item xs={6} >
          <Button sx={{ m: 3 }} color={deposito ? "primary" : "inherit"} onClick={handleClickDeposito} variant="contained" size="md" value="Deposito">Depósito</Button>
        </Grid>
        <Grid item xs={6} >
          <Button sx={{ m: 3 }} color={levantamento ? "primary" : "inherit"} onClick={handleClickLevantamento} variant="contained" size="md" value="Levantamento">Levantamento</Button>
        </Grid>
      </Grid>
      {
        deposito && promocoes.length > 0 &&
        <Container maxWidth="sm" >
          <h3>Promoções</h3>
          <Box size="md" sx={{ m: 3, border: 2, borderRadius: '10px' }} >
            { promocoes.map((promo) => {
                return (
                  <Grid container spacing={2}>
                    <Grid item xs={10} >
                      <p>Deposite {promo.deposito}€ e ganhe {promo.freeBets} freebets</p>
                    </Grid>
                    <Grid item xs={2} >
                      <IconButton value={promo.idPromocao} onClick={handleClickPromocao} color="primary" disabled={montante < (promo.deposito)} sx={{ mt: 1 }}  >
                        {promo.idPromocao == promoSelecionada ? <CheckBoxIcon /> : < CheckBoxOutlineBlankIcon />}
                      </IconButton>
                    </Grid>
                  </Grid>)
              }
              )}
          </Box>
        </Container >
      }
      {
        (deposito || levantamento) &&
        <Box component="form" onSubmit={handleSubmit} noValidate>
          <TextField margin="normal" required name="Montante" label="Montante" id="Montante"
            type=""
            defaultValue={1}
            error={montanteCondition()}
            helperText={montanteCondition() ? 'Valor inválido' : ' '}
            onChange={changeMontante}
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
    </div >
  );
}

export default Transacoes;