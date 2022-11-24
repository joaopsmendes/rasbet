
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


function VerApostas(props){
    const[apostas, setApostas]=useState({})

    const getApostas=async(email)=>{
        const response = await fetch('http://localhost:8080/historicoAposta', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
          },
        body: JSON.stringify({
            "email": email, 
        }),
     });

        const data = await response.json();
    }

    useEffect(()=>{
        //getApostas(props.email);
    },[])

    return(
        <div className = "verApostas">
            {//apostas.length > 0 && apostas.map((aposta)=>(<verApostas key={aposta.idAposta} aposta={aposta}/>))
            }
         </div>   
    );
}


export default VerApostas;
