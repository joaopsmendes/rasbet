import React,{useState,useEffect} from "react";
import Button from '@mui/material/Button';
import ToggleButton from '@mui/material/ToggleButton';


function Odd(props) {

    const[valor,setValor]=useState();
    const[nome,setNome]=useState();
    const[idOdd,setIdOdd]=useState();
    
    useEffect(()=>{
        console.log("here");
        console.log(props.odd);
        setValor(props.odd[1].valor)
        setNome(props.odd[1].opcao)
        setIdOdd(props.odd[1].valor)
    },[])

    const [flag, setFlag] = React.useState(true)
    
    const handleClick = () => {
      setFlag(!flag);
    };
  

    return (
        <p>
            {nome}<br/>{valor}
          </p>
    );
  }

export default Odd;
