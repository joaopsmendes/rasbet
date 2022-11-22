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
//import Apostas from "./Apostas";


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

  const paperStyle={padding:'80px 50px', width:600,bmargin:"20px auto"}
  const paperStyle2={padding:'40px 40px', width:600,bmargin:"20px auto"}
  
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
                    <ToggleButton size="string" value="Multipla">Múltipla</ToggleButton>

            </ToggleButtonGroup>
          <Fab size="small" color="inherit" aria-label="add">
            >
          </Fab>
          
          <Paper elevation={2} style={paperStyle2}>
            {
            //<Apostas/>
            }
          </Paper>
          
        </Stack>
        </Paper>
        </Container>
      </div>
      
  );
}

export default HistoricoApostas;
