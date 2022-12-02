import React,{useState,useEffect} from "react";
import Button from '@mui/material/Button';
import ToggleButton from '@mui/material/ToggleButton';


function Odd(props) {

    const[valor,setValor]=useState();
    const[nome,setNome]=useState();
    
    useEffect(()=>{
        console.log("Odd");
        console.log(props.odd);
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
