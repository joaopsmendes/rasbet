import React, { useState, useEffect } from "react";
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Transacoes from "./Transacoes";
import validator from 'validator'
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';


function AlterarInformacaoUser(props) {


  const [email, setEmail] = useState("")
  const [username, setUsername] = useState()
  const [morada, setMorada] = useState()
  const [password, setPassword] = useState()
  const [telemovel, setTelemovel] = useState()



  const handleSubmit = async (event) => {
    event.preventDefault();

    const body = { email: email }

    if (username) {
      body['username'] = username;
    }
    if (morada) {
      body['morada'] = morada;
    }
    if (telemovel) {
      body['telemovel'] = telemovel;
    }

    if (password) {
      body['password'] = password;
    }



    const response = await fetch('http://localhost:8080/changeProfile', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(body),
    });

    if (response.status === 200) {
      props.altera();
    }
    else {
      alert("Erro ao alterar informação!")
    }

  }



  useEffect(() => {
    const user = JSON.parse(sessionStorage.getItem('user'))
    setEmail(user)
  }, [props.update]);





  return (
    <div className="AlterarInformacaoUser">
      <Container maxWidth="sm">
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            fullWidth
            name="password"
            label="Palavra-Passe"
            onChange={(e) => setPassword(e.target.value)}
            type="password"
            id="password"
          />
          <TextField
            margin="normal"
            fullWidth
            name="username"
            label="Username"
            onChange={(e) => setUsername(e.target.value)}
            id="username"
          />
          <TextField
            margin="normal"
            fullWidth
            name="morada"
            label="Morada"
            onChange={(e) => setMorada(e.target.value)}
            id="morada"
          />
          <TextField
            margin="normal"
            fullWidth
            name="tel"
            label="Telemóvel"
            onChange={(e) => setTelemovel(e.target.value)}
            id="tel"
            type="tel"
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}>
            Alterar
          </Button>
        </Box>
      </Container>
    </div>

  );

}
export default AlterarInformacaoUser;