import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Box from '@mui/material/Box';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Stack from '@mui/material/Stack';
import TextField from '@mui/material/TextField';
import CloseIcon from '@mui/icons-material/Close';
import IconButton from '@mui/material/IconButton';


function AlteracaoOdd() {
  const [open, setOpen] = React.useState(false);
  const [open1, setOpen1] = React.useState(false);
  const [open2, setOpen2] = React.useState(false);
  const [odd, setOdd] = React.useState(1);


  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleClickOpen1 = () => {
    setOpen1(true);
  };

  const handleClose1 = () => {
    setOpen1(false);
  };

  const handleClickOpen2 = () => {
    setOpen2(true);
  };

  const handleClose2 = () => {
    setOpen2(false);
  };

  const closeAll = () => {
    setOpen(false);
    setOpen1(false);
    setOpen2(false);
  }

  {/**  const getSaldo=async()=>{
        const response = await fetch('http://localhost:8080/jogos?' + new URLSearchParams({
                desporto: "futebol"})
            , {
            method: 'GET',
        });
        const data = await response.json();
        //const data =response.json();
        var result = Object.keys(data).map((key) => data[key]);
        console.log("RESULT");
        console.log(result);
        setJogos(result);

    } */}

  const alteraOdd = async () => {
    const response = await fetch('http://localhost:8080/alterarOdd', {
      method: 'GET',
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
      <Button variant="text" onClick={handleClickOpen}>
        Alteração da Odd
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <IconButton onClick={handleClose}>
          <CloseIcon />
        </IconButton>
        <DialogTitle>ALTERAÇÃO DE ODD - JOGO ATIVO</DialogTitle>
        <DialogContent>
          <h1> </h1> {/*alterar o estado do jogo, pedido a base de dados*/}
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
          <h1> </h1> {/*alterar o estado do jogo, pedido a base de dados*/}
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
          <h1> Pretende REATIVAR o jogo? </h1> {/*alterar o estado do jogo, pedido a base de dados*/}
          <Button variant="Contained" onClick={closeAll}>SIM</Button>
          <Button variant="Contained" onClick={handleClose2}>NÂO</Button>
        </DialogContent>
      </Dialog>
    </div>
  );
}

export default AlteracaoOdd