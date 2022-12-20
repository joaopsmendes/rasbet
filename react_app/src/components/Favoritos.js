import React, { useState, useEffect } from "react";
import ApostaJogo from "./ApostaJogo";
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import Checkbox from '@mui/material/Checkbox';
import FavoriteBorder from '@mui/icons-material/FavoriteBorder';
import Favorite from '@mui/icons-material/Favorite';
import { Grid } from "@mui/material";
import Popover from '@mui/material/Popover';
import Button from '@mui/material/Button';
import IconButton from "@mui/material/IconButton";

function Favoritos(props) {



  const [anchorEl, setAnchorEl] = React.useState(null);

  const handleClick = (event) => {
      setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
      setAnchorEl(null);
  };

  const open = Boolean(anchorEl);
  const idPop = open ? 'simple-popover' : undefined;


  const favoritar = () => {
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
        {props.participantes.length > 0 ? props.participantes.map((participante) => {
          return (<div>
            {participante}{fav(participante)}
          </div>
          )
        })
        : <div>Não tem favoritos</div>
        }
      </Popover>
    )
  }


  const addFavorito = async (nome) => {
    const sessionId = JSON.parse(sessionStorage.getItem('sessionId'));
    const response = await fetch('http://localhost:8080/addFavorito', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        id: sessionId,
        value: nome,
        desporto: props.desporto
      }),
    });
  }

  const removeFavorito = async (nome) => {
    const sessionId = JSON.parse(sessionStorage.getItem('sessionId'));

    const response = await fetch('http://localhost:8080/removeFavorito', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        sessionId: sessionId,
        value: nome,
        desporto: props.desporto
      }),
    });

  }

  const handleClickFav = (event) => {
    let obj = props.favoritos.slice();
    let participante = event.currentTarget.value;
    if (isInFav(participante)) {
      console.log("REMOVE");
      removeFavorito(participante);
      obj = obj.filter((item) => item !== participante);
    } else {
      console.log("ADD");
      addFavorito(participante);
      obj.push(event.currentTarget.value);
    }
    props.setFavoritos(obj);
  }

  const isInFav = (participante) => {
    for (let i = 0; i < props.favoritos.length; i++) {
      if (props.favoritos[i] === participante) {
        return true;
      }
    }
    return false;
  }



  const fav = (participante) => {
    return (
      <IconButton color="primary" value={participante} aria-label="add to favorites" onClick={handleClickFav}>
        {isInFav(participante) ? <Favorite /> : <FavoriteBorder />}
      </IconButton>
    );
  }


  return (

    <Grid container spacing={2}>
      {props.showFavoritos &&
        <Grid item xs={2}>
          <IconButton onClick={handleClick} color="primary">
            <FavoriteBorder color="secondary"/>
          </IconButton>
          {favoritar()}
        </Grid>
      }
    </Grid>


  );
}

export default Favoritos;
