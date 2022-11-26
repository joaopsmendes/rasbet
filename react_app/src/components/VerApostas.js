
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
        console.log(email)
        const response = await fetch('http://localhost:8080/historicoAposta?' + new URLSearchParams({
                userId: email})
            , {
            method: 'GET',
        });


        const data = await response.json();
        setApostas(data);
        console.log(data);
    }

    useEffect(()=>{
        const user = JSON.parse(sessionStorage.getItem('user'));
        getApostas(user);
    },[])

    return(
        <div className = "verApostas">
            {
            apostas.length > 0 && apostas.map((aposta)=>(<p>{aposta.idAposta}</p>))
            }
         </div>   
    );
}


export default VerApostas;
