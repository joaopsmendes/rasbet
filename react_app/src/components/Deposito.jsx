import {useState }from 'react';
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

 function Deposito(props) {

  const [montante, setMontante] = useState(0.01)

  const handleSubmit = async (event) => {
    event.preventDefault();


  };

  return (
    <div>
          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>        
          <TextField margin="normal" required fullWidth name="Montante" label="Montante" id="Montante" 
            type="number"
            defaultValue={1}
            error={montante <= 0}
            helperText={montante <= 0 ? 'Valor inválido' : ' '}
            onChange={(e)=>setMontante(e.target.value)}
            InputProps={{ inputProps: { min: 0.01 } }} />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Deposito
            </Button>
          </Box> 
      </div>
            
  );
}


export default Deposito;