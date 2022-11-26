import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';


const theme = createTheme();

 function Registo(props) {
  const handleSubmit = async (event) => {
    event.preventDefault();

    /*
    const data = new FormData(event.currentTarget);
    const email = data.get('E-mail');
    const password = data.get('Palavra-Pass');
    const datanascimento = data.get('Data de Nascimento');
    const nif = data.get('NIF');


    const response = await fetch('http://localhost:8080/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
          },
        body: JSON.stringify({
            "email": email, 
            "password": password,
            "date": datanascimento,
            "nif": nif,
            "nome":nome,
            "morada":morada,
            "telemovel":telemovel
        }),
    });
    */
    

  };

  return (
    <div>
    {/*}
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Registo
          </Typography>
          */
        }
          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>        
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="E-mail"
              name="email"
              type="email"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Palavra-Passe"
              type="password"
              id="password"
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="username"
              label="Username"
              id="username"
            />

          <TextField
              margin="normal"
              required
              fullWidth
              name="date"
              id="date"
              label="Data de Nascimento"
              type="date"
              defaultValue={new Date().toLocaleDateString()}
              InputLabelProps={{
                shrink: true,
              }}
            />

            <TextField
              margin="normal"
              required
              fullWidth
              name="morada"
              label="Morada"
              id="morada"
            />

              <TextField
              margin="normal"
              required
              fullWidth
              name="nif"
              label="NIF"
              id="nif"
            />

            <TextField
              margin="normal"
              required
              fullWidth
              name="telemovel"
              label="Telemóvel"
              id="telemovel"
              type="tel"
            />

            

            
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Registar
            </Button>
          </Box> 
      </div>
            
  );
}


export default Registo;