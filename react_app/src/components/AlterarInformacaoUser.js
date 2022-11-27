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
import Grid from '@mui/material/Grid';
import Deposito from "./Deposito";
import Dialogo from "./Dialogo";
import Registo from "./Registo";
import Levantamento from "./Levantamento";
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import Transacoes from "./Transacoes";


function AlterarInformacaoUser(props){
    
    const [alignment, setAlignment] = useState();
    const[saldo, setSaldo]=useState(0)
    const [deposito,setDeposito]=useState(false)
    const [levantamento,setLevantamento]=useState(false)
    const [montante,setMontante]=useState(1)
    

    //const paperStyle={padding:'50px 50px', width:600,bmargin:"50px auto", justification: 'center',alignItems: "center"}
    const paperStyle={height: "50%", display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center"}
    








    const changeProfile = async(email) =>{
      const response = await fetch('http://localhost:8080/changeProfile?' + new URLSearchParams({
        userId: email})
        ,{
          method: 'GET',
      });
    }


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
      },[]);
  

    return(     
     <div className = "AlterarInformacaoUser"> 
      <Container>
        <h1> {props.nome} </h1>
        <h2> Saldo: {saldo} </h2> 
        <Transacoes getSaldo={getSaldo} saldo={saldo}/>

            <HistoricoApostas/>
            
            {/** aceder a base de dados para alterar a informação de um perfil */}
        </Container>
      </div>
      
        );

  }
export default AlterarInformacaoUser;