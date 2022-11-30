import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import CloseIcon from '@mui/icons-material/Close';
import IconButton from '@mui/material/IconButton';
import Grid from '@mui/material/Grid';
import MBWAY from './MBWay';





function Pagamento(props) {
  const [open, setOpen] = React.useState(false);
  const [mbway, setMbway] = React.useState(false);
  const [saldo, setSaldo] = React.useState(false);
  const [montanteSaldo, setMontanteSaldo] = React.useState(0);
  const [montanteFreeBets, setMontanteFreeBets] = React.useState(0);
  const [montanteOutros, setMontanteOutros] = React.useState(0);

  const [freeBets, setFreeBets] = React.useState(false);
  const [outro, setOutro] = React.useState(false);

  const [saldoAtual, setSaldoAtual] = React.useState(0);
  const [freebetsAtual, setFreebetsAtual] = React.useState(0);





  const getSaldo = async (email) => {
    console.log(email)
    const response = await fetch('http://localhost:8080/saldo?' + new URLSearchParams({
      userId: email
    })
      , {
        method: 'GET',
      });

    const data = await response.json();
    //setSaldo(data.get('saldo'));
    //setFreeBets(data.get('freeBets'));
    console.log(data);
    setSaldoAtual(data['saldo']);
    setFreebetsAtual(data['freebets']);

  }

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    props.setPagamento(false);
  };

  const handleClickSaldo = () => {
    setSaldo(!saldo);
    setOutro(false);
  };

  const handleClickFreeBets = () => {
    setFreeBets(!freeBets);
    setOutro(false);
  };

  const handleClickOutro = () => {
    setOutro(!outro);
    setSaldo(false);
    setFreeBets(false);
  };


  const handleMbway = () => {
    setMbway(true);
  };

  const handleCloseMbway = () => {
    setMbway(false);
  };

  React.useEffect(() => {
    setOpen(true);
    const user = JSON.parse(sessionStorage.getItem('user'));
    getSaldo(user);
  }, [props.pagamento]);


  const montanteField = (nome, bool, montante, setter, atual) => {
    return (
      <TextField requiredname={nome} fullWidth label={nome} id={nome}
        type="number"
        defaultValue={montante}
        disabled={!bool}
        onChange={(e) => setter(Number(e.target.value))}
        error={valorAPagar() < 0 || montante > atual}
        helperText={valorAPagar() < 0  || montante > atual ? 'Valor inválido' : ' '}
        InputProps={{ inputProps: { min: 0.01 } }} />
    );
  }

  const handleSubmit = () => {
    
  }



  const valorPago = () => {
    let valor = 0;
    if (saldo) {
      valor += montanteSaldo;
      console.log(valor);

    }
    if (freeBets) {
      valor += montanteFreeBets;
      console.log(valor);
    }
    if (outro) {
      valor += montanteOutros;
      console.log(valor);

    }
    return valor;
  }

  const valorAPagar = () => {
    return props.valor - valorPago();
  }


  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <DialogContent>
          <h3>Valor a pagar : {valorAPagar()}</h3>
          <h3>Saldo Atual : {saldoAtual - montanteSaldo}</h3>
          <h3>FreeBets Atual : {freebetsAtual- montanteFreeBets}</h3>

          <Grid container spacing={2}>
            <Grid item xs={6}>
              <Button disabled={!saldo && valorAPagar() == 0} fullWidth variant="contained" color={saldo ? "primary" : "inherit"} onClick={handleClickSaldo}>
                Saldo
              </Button>
            </Grid>
            <Grid item xs={6}>
              {montanteField("Saldo", saldo, montanteSaldo, setMontanteSaldo, saldoAtual)}
            </Grid>
            <Grid item xs={6}>
              <Button disabled={!freeBets && valorAPagar() == 0} fullWidth variant="contained" color={freeBets ? "primary" : "inherit"} onClick={handleClickFreeBets} >
                FreeBets
              </Button>
            </Grid>
            <Grid item xs={6}>
              {montanteField("FreeBets", freeBets, montanteFreeBets, setMontanteFreeBets, freebetsAtual)}
            </Grid>
            <Grid item xs={12}>
              <Button disabled={!outro && valorAPagar() == 0} fullWidth variant="contained" color={outro ? "primary" : "inherit"} onClick={handleClickOutro} >
                Outro
              </Button>
            </Grid>
          </Grid>
          {outro &&
            <Grid container spacing={2}>
              <Grid item xs={4}>
                <Button fullWidth sx={{ mt: 2 }} color="inherit" variant="contained">MULTIBANCO</Button>
              </Grid>
              <Grid item xs={4}>
                <Button fullWidth sx={{ mt: 2 }} color="inherit" variant="contained" onClick={handleMbway}>MBWAY</Button>
              </Grid>
              <Grid item xs={4}>
                <Button fullWidth sx={{ mt: 2 }} color="inherit" variant="contained">VISA</Button>
              </Grid>
            </Grid>
          }
          <Button  disabled={valorAPagar() != 0} sx={{ mt: 2 }}fullWidth variant="contained" color={valorAPagar() == 0  ? "primary" : "inherit"} onClick={props.submit}>Pagar</Button>

        </DialogContent>
      </Dialog>
      {mbway && <MBWAY mbway={mbway} close={handleCloseMbway} />}

    </div >
  );
}

export default Pagamento;

