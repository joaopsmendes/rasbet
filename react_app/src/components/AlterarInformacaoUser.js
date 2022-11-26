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
import HistoricoApostas from "./HistoricoApostas";


function AlterarInformacaoUser(props){
    const button = document.getElementById('button');
    
    const [flag, setFlag] = React.useState(true)
    const [flag1, setFlag1] = React.useState(true)
    const [alignment, setAlignment] = useState();
    const[saldo, setSaldo]=useState(0)
    
    const handleClickFlag = () => {
      setFlag(!flag);
    };
  
    const handleClickFlag1 = () => {
      setFlag1(!flag1);
    };
  
    //const paperStyle={padding:'50px 50px', width:600,bmargin:"50px auto", justification: 'center',alignItems: "center"}
    const paperStyle={height: "50%", display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center"}
    
    const handleChange = (event, newAlignment) => {
      setAlignment(newAlignment);
    };


   

    const getSaldo = async(email)=>{
        console.log(email)
        const response = await fetch('http://localhost:8080/getSaldo?' + new URLSearchParams({
          userId: email})
      , {
      method: 'GET',
  });

  console.log("SALDO");
  console.log(response);
  const data = await response.json();
  console.log(data);
  setSaldo(data);
}

useEffect(()=>{
  const user = JSON.parse(sessionStorage.getItem('user'));
  getSaldo(user);
},[])
  
    return(
       
      <div className = "AlterarInformacaoUser"> 
      <Container>
      <Paper elevation={3} style={paperStyle}>
        <h1> {props.nome} </h1>
        <h2> Saldo: {saldo} </h2> 
        <Stack direction="row" spacing={2}>
          <ToggleButtonGroup
              fullWidth={true}
              color="warning"
              value={alignment}
              exclusive
              onChange={handleChange}
                    >
                    <ToggleButton size="string" value="Levantar">Levantar</ToggleButton>
            </ToggleButtonGroup>
            <ToggleButtonGroup
              fullWidth={true}
              color="warning"
              value={alignment}
              exclusive
              onChange={handleChange}
                    >
                    <ToggleButton size="string" value="Depositar">Depositar</ToggleButton>
            </ToggleButtonGroup>
            <h1> Consultar Histórico de apostas </h1> {/* butao para abrir o histórico de apostas */}
            <HistoricoApostas/>
            
            {/** aceder a base de dados para alterar a informação de um perfil */}
        </Stack>
        </Paper>
        </Container>
      </div>
      
        );
}


export default AlterarInformacaoUser;