import React,{useState,useEffect} from "react";
import Odd from "./Odd";
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import { getAccordionDetailsUtilityClass, ToggleButton } from "@mui/material";





function ApostaJogo(props) {
  
    const[odds,setOdds]=useState([]);
    const[nome,setNome]=useState('');
    const [alignment, setAlignment] = useState();



    useEffect(()=>{
        console.log("ApostaJogo");
        console.log(props.aposta);
        let arrayOdds = Object.keys(props.aposta[1].mapOdd).map((key) => [key,props.aposta[1].mapOdd[key]]);
        setOdds(arrayOdds)
        setNome(props.aposta[0])
    },[]);





    const handleChange = (event, newAlignment) => {
      setAlignment(newAlignment);
    };


    const addAposta = (newAposta) => {
      console.log(typeof newAposta);
      newAposta["nome"] = nome;
      props.addAposta(newAposta);
    }

    
    const handleClick = (event) => {
      odds.map((odd)=>{if(odd[1].idOdd==event.target.value){addAposta(odd[1])}})
    }

    return (        
            <ToggleButtonGroup
              fullWidth={true}
              color="warning"
              value={alignment}
              exclusive
              onChange={handleChange}
                    >
                    {
                    odds.length> 0 && odds.map((odd)=>(<ToggleButton onClick={handleClick} value={odd[1].idOdd}><Odd key={odd[0]} odd={odd}/></ToggleButton>))
                    }
            </ToggleButtonGroup>
   
    );
  }

export default ApostaJogo;
