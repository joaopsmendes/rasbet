import * as React from 'react';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Stack from '@mui/material/Stack';
import TextField from '@mui/material/TextField';
import CloseIcon from '@mui/icons-material/Close';
import IconButton from '@mui/material/IconButton';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import DialogActions from '@mui/material/DialogActions';
import DialogContentText from '@mui/material/DialogContentText';

function AlteracaoOdd(props) {
  const [open, setOpen] = React.useState([]);
  const [valor,setValor] = React.useState(1.01);




  const dialogoAlterarOdd = (open, texto,textField,buttons) => {
    return (
      <Dialog open={open} >
        <DialogTitle>Alteração de Odd - Jogo Ativo</DialogTitle>
        <DialogContent>
          <DialogContentText>
            {texto}
          </DialogContentText>
          {textField}
          <DialogActions>
            {buttons}
          </DialogActions>
        </DialogContent>
      </Dialog>
    );
  };


  React.useEffect(() => {
    setOpen([true, false, false]);
  }, []);



  const getTitulo = () => {
    return props.jogo.titulo;
  }

  const button = (texto, onClick) => {
    return <Button variant="contained" color="inherit" sx={{ border: 1, borderRadius: '10px' }} onClick={onClick}>{texto}</Button>
  }

  const suspenderJogo = async () => {
    const response = await fetch('http://localhost:8080/suspenderJogo', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ idJogo: props.jogo.desJogo })
    });
    setOpen([false, true, false]);

  }

  const ativarJogo = async () => {
    const response = await fetch('http://localhost:8080/ativarJogo', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ idJogo: props.jogo.desJogo })
    });
  }

  const handleAlterarOdd = async () => {
    alterarOdd(props.jogo.idOdd);
    setOpen([false, false, true]);
  }


  const setAlignment = (alignment) => {
    return null;
  }


  const cancelarAlteracao = () => {
    setOpen([true, false, false]);
    ativarJogo();
  }


  const action = (event) => {
    console.log(event.target.value);
  }

  const finish = () => {
    ativarJogo();
    props.setMaster(false);
    props.update();
  }


  const alterarOdd = async (idOdd) => {
    const response = await fetch('http://localhost:8080/alterarOdd', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ idOdd: idOdd, valor: valor })
    });
  }



return (
  <div>
    {
      open[0] && props.jogo && dialogoAlterarOdd(open[0],
        "O jogo " + getTitulo() + " encontra-se ativo. Pretende suspender e prosseguir com a alteração da Odd?",null,
        [button("Sim", () => { suspenderJogo() }),
        button("Não", () => { props.setMaster(false) })])

    }
    {
      open[1] && dialogoAlterarOdd(open[1], "O jogo encontra-se Suspenso. Pode inserir a nova odd",
        <TextField margin="normal" required
          name="Odd"
          label={props.jogo.opcao}
          id="Montante"
          sx={{ mr: 2 }}
          onChange={e => setValor(e.target.value)}
          type="number"
          autoFocus
          inputProps={{ min: 1.01, step: 0.01 }}
          error={valor < 1.01}
          helperText={valor < 1.01 ? 'Valor inválido' : ''}
          defaultValue={valor} />,
        [ <Button disabled={valor < 1.01} variant="contained" color="inherit" sx={{ border: 1, borderRadius: '10px' }} onClick={() => { handleAlterarOdd() }}>Alterar</Button> ,
        button("Cancelar", () => { cancelarAlteracao() })])
    }
    {
      open[2] && dialogoAlterarOdd(open[2], "Odd alterada com sucesso. Pretende REATIVAR o jogo?",null,
        [button("Sim", () => { finish() }),
        button("Não", () => { setOpen([false, true, false]) })])
    }
  </div>
);
}

export default AlteracaoOdd