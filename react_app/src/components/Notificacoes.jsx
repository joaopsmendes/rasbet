import React, { useState, useEffect } from "react";
import Button from '@mui/material/Button';
import { Box, Container, Divider } from "@mui/material";
import Grid from "@mui/material/Grid";
import IconButton from '@mui/material/IconButton';
import Badge from '@mui/material/Badge';
import NotificationsIcon from '@mui/icons-material/Notifications';
import Popover from '@mui/material/Popover';
import DeleteIcon from '@mui/icons-material/Delete';





function Notificacoes(props) {
  const [notificacoes, setNotificacoes] = useState([]);


  const [anchorEl, setAnchorEl] = React.useState(null);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const open = Boolean(anchorEl);
  const idPop = open ? 'simple-popover' : undefined;

  const setNotificaoVista = async (idNotificacao) => {
    const sessionId = JSON.parse(sessionStorage.getItem('sessionId'));
    let response = await fetch("http://localhost:8080/vistaNotificacao", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        sessionId: sessionId,
        notificacao: idNotificacao,
      }),
    });
    if (response.status == 200) {
      console.log("Notificacao vista");
    }
  }


  const removeNotificacao = async (idNotificacao) => {
    const sessionId = JSON.parse(sessionStorage.getItem('sessionId'));
    let response = await fetch("http://localhost:8080/removeNotificacao", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        sessionId: sessionId,
        notificacao: idNotificacao,
      }),
    });
    if (response.status == 200) {
      console.log("Notificacao removida");
    }
  }

  
  const handleClickRemove = async (event) => {
    let idNotificacao = event.currentTarget.value;
    removeNotificacao(idNotificacao);
    await new Promise(r => setTimeout(r, 100));
    getNotifications();
  }


  const handleClickVista = async (event) => {
    let idNotificacao = event.currentTarget.value;
    setNotificaoVista(idNotificacao);
    await new Promise(r => setTimeout(r, 100));
    getNotifications();
  }



  const notificacao = () => {
    return (
      <Popover
        id={idPop}
        open={open}
        anchorEl={anchorEl}
        onClose={handleClose}
        sx={{ p: 2 }}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'left',
        }}
      >
        {notificacoes.length > 0 ? notificacoes.map((notificacao) => (
          <Container sx={{ m: 2 }}>
            <Grid container spacing={2}>
              <Grid item xs={9}>
                <Button sx={{ border: 2, borderColor: notificacao.vista ? "standard" : "secondary.main" }} value={notificacao.idNotificacao} color="inherit" fullWidth variant="contained" onClick={handleClickVista}>
                  {notificacao.conteudo}
                </Button>
              </Grid>
              <Grid item xs={3}>
                <IconButton value={notificacao.idNotificacao} onClick={handleClickRemove} >
                  <DeleteIcon  />
                </IconButton>
              </Grid>
            </Grid>
          </Container>
        ))
          : <div>Não tem Notificacoes</div>
        }
      </Popover>
    )
  }

  const getNotifications = async () => {
    const sessionId = JSON.parse(sessionStorage.getItem('sessionId'));

    const response = await fetch('http://localhost:8080/notificacoes?' + new URLSearchParams({
      sessionId: sessionId
    })
      , {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        }
      });
    const data = await response.json();
    console.log("NOTIFICACOES");
    console.log(data);
    setNotificacoes(data);
  }


  useEffect(() => {
    getNotifications();


    const interval = setInterval(() => {
      getNotifications();
    }, 10000);

    return () => clearInterval(interval);
  }, []);

  const numberUnseenNotifications =
    notificacoes.filter((notificacao) => {
      return notificacao.vista == false;
    }).length;




  return (
    <div className="Notificacoes">
      <Box sx={{ flexGrow: 0, m: 1 }}>
        <IconButton onClick={handleClick} size="large" color="inherit" >
          <Badge badgeContent={numberUnseenNotifications} color="secondary">
            <NotificationsIcon />
          </Badge>
        </IconButton>
        {notificacao()}
      </Box>
    </div>
  )
}
export default Notificacoes;