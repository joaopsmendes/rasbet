import React, { useState, useEffect } from "react";
import Odd from "./Odd";
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import { getAccordionDetailsUtilityClass, ToggleButton } from "@mui/material";





function ApostaJogo(props) {

  const [odds, setOdds] = useState([]);
  const [nome, setNome] = useState('');
  const [alignment, setAlignment] = useState();



  useEffect(() => {
    console.log(props.aposta);
    let arrayOdds = Object.keys(props.aposta[1].mapOdd).map((key) => [key, props.aposta[1].mapOdd[key]]);
    setOdds(arrayOdds)
    setNome(props.aposta[0])
  }, []);





  const handleChange = (event, newAlignment) => {
    if (newAlignment != null) {
      odds.map((odd) => { if (odd[1].idOdd == newAlignment) { addOdd(odd[1]) } })
    }
    else {
      odds.map((odd) => { if (odd[1].idOdd == alignment) { removeOdd(odd[1].idOdd) } })
    }
    setAlignment(newAlignment);
    console.log("alignment");
    console.log(alignment);
    console.log(newAlignment);

  };


  const addOdd = (newAposta) => {
    newAposta["nome"] = nome;
    props.addOdd(newAposta);
  }

  const removeOdd = (newAposta) => {
    props.removeOdd(newAposta);
  }



  return (
  <div className="ApostaJogo">
    <h3>{nome}</h3>
    <ToggleButtonGroup
      fullWidth
      color="warning"
      value={alignment}
      exclusive
      onChange={handleChange}
    >
      {
        odds.length > 0 && odds.map((odd) => (<ToggleButton  style={{outlineWidth: '2px', outlineStyle: 'solid', margin: '1%',borderRadius: "10px"}} sx={{ m: 4, }}  value={odd[1].idOdd}><Odd key={odd[0]} odd={odd} /></ToggleButton>))
      }
    </ToggleButtonGroup>
  </div>

  );
}

export default ApostaJogo;
