import React,{useState,useEffect} from "react";
import Odd from "./Odd";
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import { ToggleButton } from "@mui/material";





function ApostaJogo(props) {
  
    const[odds,setOdds]=useState([]);
    const[nome,setNome]=useState('');
    const [alignment, setAlignment] = useState();



    useEffect(()=>{
        let arrayOdds = Object.keys(props.aposta[1].mapOdd).map((key) => [key,props.aposta[1].mapOdd[key]]);
        setOdds(arrayOdds)
        setNome(props.aposta[0])
    },[]);





    const handleChange = (event, newAlignment) => {
      setAlignment(newAlignment);
    };
  

    return (        
            <ToggleButtonGroup
              fullWidth={true}
              color="warning"
              value={alignment}
              exclusive
              onChange={handleChange}
                    >
                    {
                    odds.length> 0 && odds.map((odd)=>(<ToggleButton  value={odd[0]}><Odd key={odd[0]} odd={odd}/></ToggleButton>))
                    }
            </ToggleButtonGroup>
   
    );
  }

export default ApostaJogo;
