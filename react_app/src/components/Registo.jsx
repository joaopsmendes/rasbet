import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import validator from 'validator'
import FormControl from '@mui/material/FormControl';



 function Registo(props) {


  const [email, setEmail] = React.useState('');
  const [NIF, setNIF] = React.useState('');

  const handleSubmit = async (event) => {
    console.log("HERE");
    event.preventDefault();

    const data = new FormData(event.currentTarget);
    const password = data.get('password');
    const datanascimento = data.get('date');
    console.log(datanascimento);
    const nif = data.get('nif');
    const nome = data.get('username');
    const morada = data.get('morada');
    const telemovel = data.get('tel');

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
    if (response.status === 200) {
      props.login(email);
      
    }
    else{
      alert("Erro ao registar");
    }

  };

  const validateEmail = (email) => {
    if (validator.isEmail(email)) {
      return true;
    }
    return false;
  }

  const validateNIF = (NIF) => {
    if (validator.isNumeric(NIF) && NIF.length == 9) {
      return true;
    }
    return false;
  }

  const allValid = () => {
    if (validateEmail(email) && validateNIF(NIF)) {
      return true;
    }
    return false;
  }


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
          <Box  component="form" onSubmit={handleSubmit}  sx={{ mt: 1 }}>        
            <TextField
              margin="normal"
              required
              fullWidth
              error={!(email =='' || validateEmail(email))}
              helperText={!(email =='' || validateEmail(email)) ? 'E-mail inválido' : ''}
              onChange={(e) => setEmail(e.target.value)}
              id="email"
              label="E-mail"
              name="email"
              type="email"
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
              onChange={(e) => setNIF(e.target.value)}
              error={!(NIF == '' || validateNIF(NIF))}
              helperText={!(NIF == '' || validateNIF(NIF)) ? 'NIF inválido' : ''}
            />

            <TextField
              margin="normal"
              required
              fullWidth
              name="tel"
              label="Telemóvel"
              id="tel"
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