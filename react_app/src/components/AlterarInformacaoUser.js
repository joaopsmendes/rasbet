import React, { useState, useEffect } from "react";
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Transacoes from "./Transacoes";
import validator from 'validator'
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';


function AlterarInformacaoUser(props) {


  const [email, setEmail] = useState("")
  const [nome, setNome] = useState("")
  const [morada, setMorada] = useState("")
  const [password, setPassword] = useState("")
  const [telemovel, setTelemovel] = useState("")

  //const paperStyle={padding:'50px 50px', width:600,bmargin:"50px auto", justification: 'center',alignItems: "center"}
  const paperStyle = { height: "50%", display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center" }



  const handleSubmit = async (email) => {

    
    const body = { email: email }

    if (nome !== '') {
      body['nome'] = nome;
    }
    if (morada !== '') {
      body['morada'] = morada
    }
    if (telemovel !== '') {
      body['telemovel'] = telemovel
    }
    if (email !== '') {
      body['email'] = email
    }
    if (password !== '') {
      body['password'] = password
    }
    

    const response = await fetch('http://localhost:8080/changeProfileInfo?' + new URLSearchParams({
      userId: email
    })
      , {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: body,
      });

  }


  const validateEmail = (email) => {
    if (validator.isEmail(email)) {
      return true;
    }
    return false;
  }

  


  useEffect(() => {
    const user = JSON.parse(sessionStorage.getItem('user'));
    setEmail(user);
  }, []);


  return (
    <div className="AlterarInformacaoUser">
      <Container maxWidth="sm">
      <Box sx={{ width: '10%' }} component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
        <TextField  name="Email" label="Email" id="Email"
          fullWidth
          type="email"
          value={email}
          error={!validateEmail(email)}
          helperText={!validateEmail(email) ? 'Valor inválido' : ' '}
          onChange={(e) => setEmail(e.target.value)} />
        <TextField  name="nome" label="Nome" id="nome"
          fullWidth
          type="text"
          value={nome}
          error={nome.length > 45}
          helperText={nome.length > 45 ? 'Número máximo de caratéres atingido' : ' '}
          onChange={(e) => setNome(e.target.value)} />
        <Button
          type="submit"
          variant="contained"
          sx={{ m: 3, mb: 2 }}
        >
          Alterar
        </Button>
      </Box>
      </Container>
    </div>

  );

}
export default AlterarInformacaoUser;