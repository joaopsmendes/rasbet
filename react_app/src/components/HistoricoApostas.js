/*import React, { useEffect, useState } from 'react';
import { makeStyles } from '@mui/styles';
import TextField from '@mui/material/TextField';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper'
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';

const useStyles = makeStyles((theme) => ({
    root: {
      '& > *': {
        margin: theme.spacing(1),
       
      },
    },
  }));



function HistoricoApostas(){
    const paperStyle ={padding: '50px 20px', width:500,margin:"20px auto"} 
    //const classes = useStyles();

    const name = ''

    const handleClick=(e)=>{
        
    }
    return (

        <Container>
            <Paper elevation={3} style={paperStyle}>
            <h1 style={{color:"blue"}}><u> {name} </u></h1>
            <h2> Histórico de Apostas </h2>
        <form className={classes.root} noValidate autoComplete="off">
          <Button variant="contained" color="secondary" onClick={handleClick}>
            Simples
          </Button>
          <Button variant="contained" color="secondary" onClick={handleClick}>
            Múltiplas
          </Button>
        </form>

        </Paper>
        </Container>
      );
}

export default HistoricoApostas;
*/