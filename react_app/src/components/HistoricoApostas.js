import React,{useState,useEffect} from "react";
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import IconButton from '@mui/material/IconButton';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import { ToggleButton } from "@mui/material";
import VerApostas from "./VerApostas";

function HistoricoApostas(props){
  const button = document.getElementById('button');
  
  const [flag, setFlag] = React.useState(true)
  const [flag1, setFlag1] = React.useState(true)
  const [alignment, setAlignment] = useState();
  
  const handleClickFlag = () => {
    setFlag(!flag);
  };

  const handleClickFlag1 = () => {
    setFlag1(!flag1);
  };

  //const paperStyle={padding:'50px 50px', width:600,bmargin:"50px auto", justification: 'center',alignItems: "center"}
  const paperStyle={height: "50%", display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center"}
  const paperStyle2={padding:'10% 10%', width:"80%", bmargin:"20px auto",flexDirection: "column", alignItems: "center",  justifyContent: "center"}
  
  const handleChange = (event, newAlignment) => {
    setAlignment(newAlignment);
  };

  return(
    
      <div className = "Historico"> 
      <Container>
      <Paper elevation={3} style={paperStyle}>
        <h1> {props.nome} </h1>
        <h2> Histórico de apostas </h2>
        <Stack direction="row" spacing={2}>
          <ToggleButtonGroup
              fullWidth={true}
              color="warning"
              value={alignment}
              exclusive
              onChange={handleChange}
                    >
                    <ToggleButton size="string" value="Simples">Simples</ToggleButton>
                    

            </ToggleButtonGroup>
            <ToggleButtonGroup
              fullWidth={true}
              color="warning"
              value={alignment}
              exclusive
              onChange={handleChange}
                    >
                    <ToggleButton size="string" value="Multipla">Múltipla</ToggleButton>

            </ToggleButtonGroup>
          <Fab size="small" color="inherit" aria-label="add">
            >
          </Fab>
          

          
        </Stack>
        <Paper elevation={3} style={paperStyle2} >
            {
            <VerApostas/>
            }
          </Paper>
        </Paper>
        </Container>
      </div>
      
  );
}

export default HistoricoApostas;
