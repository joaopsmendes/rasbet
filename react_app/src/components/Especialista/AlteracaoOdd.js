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




  const dialogoAlterarOdd = (open, texto, buttons) => {
    return (
      <Dialog open={open} >
        <DialogTitle>Alteração de Odd - Jogo Ativo</DialogTitle>
        <DialogContent>
          <DialogContentText>
            {texto}
          </DialogContentText>
          <DialogActions>
            {buttons}
          </DialogActions>
        </DialogContent>
      </Dialog>
    );
  };


  React.useEffect(() => {
    setOpen([true,false,false]);
  }, []);



  const getTitulo = () => {
    return props.jogo.titulo;
  }

  const button = (texto, onClick) => {
    return <Button variant="contained" color="inherit" sx={{ border: 1, borderRadius: '10px' }} onClick={onClick}>{texto}</Button>
  }

  //TODO
  const alterarOdd = async (odd) => {
    const response = await fetch('http://localhost:8080/alterarOdd', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(odd)
    });
  }

  //TODO
  const alterarEstadoJogo = async (idJogo, estado) => {
    const response = await fetch('http://localhost:8080/alterarEstadoJogo', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ idJogo: idJogo, estado: estado })
    });
  }

  const handleAlterarOdd = async () => {
    //alterarOdd(props.jogo);
    setOpen([false, false, true]);
  }



  const ativarJogo = async () => {
    alterarEstadoJogo(props.jogo.idJogo, 'ativo');
  }

  const setAlignment = (alignment) => {
    return null;
  }


  const cancelarAlteracao = () => {
    setOpen([true, false, false]);
    //ativarJogo();
  }


  const action = (event) => {
    console.log(event.target.value);
  }

  const suspenderJogo = async () => {
    //alterarEstadoJogo(props.jogo.idJogo, 'suspenso');
    setOpen([false, true, false]);
  }


  const alteraOdd = async () => {
    const response = await fetch('http://localhost:8080/alterarOdd', {
      method: 'POST',
    });

    const data = await response.json();
  }

  const alterarEstado = async (idJogo, estado) => {
    const response = await fetch('http://localhost:8080/alterarEstado', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        idJogo: idJogo,
        estado: estado
      }),

    });

    const data = await response.json();
  }

  return (
    <div>
      {/*}
      <Button variant="text" onClick={handleClickOpen}>
        Alteração da Odd
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <IconButton onClick={handleClose}>
          <CloseIcon />
        </IconButton>
        <DialogTitle>ALTERAÇÃO DE ODD - JOGO ATIVO</DialogTitle>
        <DialogContent>
          <h1>  </h1> {/*alterar o estado do jogo, pedido a base de dados*
          <Button variant="Contained" onClick={handleClickOpen1}>SIM</Button>
          <Button variant="Contained" onClick={handleClose} >NÂO</Button>
        </DialogContent>
      </Dialog>

      <Dialog open={open1} onClose={handleClose}>
        <IconButton onClick={handleClose1}>
          <CloseIcon />
        </IconButton>
        <DialogTitle>ALTERAÇÃO DE ODD - JOGO ATIVO</DialogTitle>
        <DialogContent>
          <h1> </h1> {/*alterar o estado do jogo, pedido a base de dados*
          <TextField
            onChange={(e) => setOdd(e.target.value)}
            size="sm"
            type="number"
            placeholder="Inserir Odd"
            error={odd < 1}
            defaultValue={odd}
            helperText={odd < 1 ? 'Valor inválido' : ' '}
            InputProps={{ inputProps: { min: 1 } }} />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            disabled={odd < 1}
            sx={{ mt: 3, mb: 2 }}
            onClick={handleClickOpen2}
          >
            Alterar
          </Button>
        </DialogContent>
      </Dialog>

      <Dialog open={open2} onClose={handleClose}>
        <IconButton onClick={handleClose2}>
          <CloseIcon />
        </IconButton>
        <DialogTitle>ALTERAÇÃO DE ODD - JOGO ATIVO</DialogTitle>
        <DialogContent>
          <h1> Odd alterada com sucesso.</h1>
          <h1> Pretende REATIVAR o jogo? </h1> {/*alterar o estado do jogo, pedido a base de dados*
          <Button variant="Contained" onClick={closeAll}>SIM</Button>
          <Button variant="Contained" onClick={handleClose2}>NÂO</Button>
        </DialogContent>
      </Dialog>
      */}
      {
        open[0] && props.jogo && dialogoAlterarOdd(open[0],
          "O jogo " + getTitulo() + " encontra-se ativo. Pretende suspender e prosseguir com a alteração da Odd?",
          [button("Sim", () => { suspenderJogo() }),
          button("Não", () => { props.setMaster(false)})])

      }
      {
        open[1] && dialogoAlterarOdd(open[1], <div>O jogo encontra-se Suspenso. Pode inserir a nova odd.
          <TextField margin="normal" required
            name="Odd"
            label={props.jogo.opcao}
            id="Montante"
            sx={{ mr: 2 }}
            type="number"
            autoFocus
            defaultValue={1} /></div>,
          [button("Alterar", () => { handleAlterarOdd() }),
          button("Cancelar", () => { cancelarAlteracao() })])
      }
      {
        open[2] && dialogoAlterarOdd(open[2], "Odd alterada com sucesso. Pretende REATIVAR o jogo?",
          [button("Sim", () => { props.setMaster(false) }),
          button("Não", () => { setOpen([false, true, false]) })])
      }


    </div>
  );
}

export default AlteracaoOdd