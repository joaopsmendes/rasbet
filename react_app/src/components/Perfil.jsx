import React, { useState, useEffect } from "react";
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Transacoes from "./Transacoes";
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';



import AlterarInformacaoUser from "./AlterarInformacaoUser";
import { getOptionsFromChildren } from "@mui/base";


function Perfil(props) {

  const [saldo, setSaldo] = useState(0)
  const [freeBets, setFreeBets] = useState(0)
  const [alterarInfo, setAlterarInfo] = useState(false)
  const [user, setUser] = useState('')
  const [info, setInfo] = useState('')


  const getSaldo = async (email) => {
    console.log(email)
    const response = await fetch('http://localhost:8080/saldo?' + new URLSearchParams({
      userId: email
    })
      , {
        method: 'GET',
      });

    console.log("SALDO");
    let data = await response.json();
    console.log(data);
    setSaldo(data['saldo']);
    setFreeBets(data['freebets']);
  }

  const handleAlterarInfo = () => {
    setAlterarInfo(!alterarInfo);
  }


  const getInfo = async (email) => {

    const response = await fetch('http://localhost:8080/info?' + new URLSearchParams({
      userId: email
    })
      , {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        }
      });
    const data = await response.json();
    setInfo(data);
    console.log(data);
  }


  useEffect(() => {
    const username = JSON.parse(sessionStorage.getItem('user'));
    getSaldo(username);
    getInfo(username);
    setUser(username);
  }, [alterarInfo]);


  return (
    <div className="Peril">
      <Container maxWidth="xl">
        <h2> Bem vindo, {info['username']} </h2>
        <Box sx={{mb: 2, p: 2, flexGrow: 1,display: 'flex',flexDirection: 'row' ,justifyContent: 'space-between', border: 1}}>
        
            <div><h3> NIF </h3><p>{info['nif']} </p></div>
            <div><h3> Telemovel </h3><p>{info['telemovel']} </p></div>
            <div><h3> Data de Nascimento</h3> <p>{info['date']} </p></div>
            <div><h3> Morada </h3><p>{info['morada']} </p></div>
        </Box>
        <Box sx={{ border: 1 }}>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <h2> Saldo: {saldo}€ </h2>
            </Grid>
            <Grid item xs={12} sm={6}>
              <h2> FreeBets: {freeBets}€</h2>
            </Grid>
          </Grid>
          <Transacoes getSaldo={getSaldo} saldo={saldo} />
        </Box>
        <Button sx={{ mt: 10, mb: 4 }} color={alterarInfo ? "primary" : "inherit"} onClick={handleAlterarInfo} variant="contained" size="md" value="AlterarInfo">Alterar Informações</Button>
        {alterarInfo && <AlterarInformacaoUser update={alterarInfo} user={user} altera={handleAlterarInfo} />}
      </Container>
    </div>

  );

}
export default Perfil;