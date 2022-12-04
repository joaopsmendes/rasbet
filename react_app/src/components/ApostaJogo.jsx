import React, { useState, useEffect } from "react";
import Odd from "./Odd";
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import ToggleButton from '@mui/material/ToggleButton';
import Box from '@mui/material/Box';
import { Container, Grid } from "@mui/material";




function ApostaJogo(props) {

  const [odds, setOdds] = useState([]);
  const [nome, setNome] = useState('');
  const [alignment, setAlignment] = useState();



  useEffect(() => {
    let arrayOdds = props.aposta[1].listOdd;
    console.log(arrayOdds);
    setOdds(arrayOdds)
    setNome(props.aposta[0])
  }, []);

  useEffect(() => {
    if (props.apostas) {
      for (let i = 0; i < props.apostas.length; i++) {
        let idOdd = props.apostas[i].idOdd;
        for (let j = 0; j < odds.length; j++) {
          if (odds[j].idOdd == idOdd) {
            return;
          }
        }
      }
      setAlignment(null);
    }
  }, [props.apostas]);






  const handleChange = (event, newAlignment) => {

    let other = newAlignment;

    if (newAlignment == null) {
      other = alignment;
    }
    odds.map((odd) => {
      if (odd.idOdd == other) {
        odd["nome"] = nome;
        props.handleClick(odd);
      }
    })
    setAlignment(props.setAlignment(newAlignment));
  };


  const addOdd = (newAposta) => {
    newAposta["nome"] = nome;
    props.addOdd(newAposta);
  }

  const removeOdd = (newAposta) => {
    props.removeOdd(newAposta);
  }



  return (
    <Container>
      <Box sx={{ display: 'flex', flexDirection: 'column', m: '2%', p: '1%', border: { md: `1.5px dashed grey`, xs: 'none' }, borderRadius: '20px' }}>
        <Grid container spacing={2}>
          <Grid item xs={12} md={2}>
            <h2>{nome}</h2>
          </Grid>
          <Grid item xs={12} md={10}>
            <ToggleButtonGroup
              fullWidth
              color="warning"
              value={alignment}
              exclusive
              onChange={handleChange}
            >
              {
                odds.length > 0 && odds.map((odd) => (<ToggleButton fullWidth style={{ outlineWidth: '2px', outlineStyle: 'solid', margin: '1%', borderRadius: "10px", padding: '1%' }} value={odd.idOdd}><Odd key={odd.idOdd} odd={odd} /></ToggleButton>))
              }
            </ToggleButtonGroup>
          </Grid>
        </Grid>
      </Box>
    </Container>

  );
}

export default ApostaJogo;
