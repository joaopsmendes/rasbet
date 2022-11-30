import React,{useState,useEffect} from "react";
import Button from '@mui/material/Button';
import ToggleButton from '@mui/material/ToggleButton';


function Odd(props) {

    const[valor,setValor]=useState();
    const[nome,setNome]=useState();
    const[idOdd,setIdOdd]=useState();
    
    useEffect(()=>{
        setValor(props.odd.price)
        setNome(props.odd.name)
    },[])

    
    return (
        <p>
            {nome}<br/>{valor}
        </p>
    );
  }

export default Odd;
