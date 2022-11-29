import React,{useState,useEffect} from "react";
import ApostaJogo from "./ApostaJogo";
import Container from '@mui/material/Container';


function Jogo(props) {

    const[apostas,setApostas]=useState([]);
    const[time,setTime]=useState();
    const[id,setID]=useState('');

    useEffect(()=>{
        setID(props.jogo.idJogo)
        const date = new Date(props.jogo.data);
        setTime(date.toLocaleString());
        let apostasArray = Object.keys(props.jogo.apostas).map((key) => [key,props.jogo.apostas[key]]); 
        setApostas(apostasArray);
        console.log("Jogo");
        console.log(props.jogo);
    },[])





    return (
        <div className="Jogo">
            <Container sx={{ maxWidth:'80%' , margin: 'auto'}}maxWidth="false">
                <h2>{props.jogo.titulo}</h2>
                <div className="Container">
                        {
                            apostas.length > 0 && apostas.map((aposta)=>(<ApostaJogo removeOdd={props.removeOdd} addOdd={props.addOdd} key={aposta.tema} aposta={aposta}/>))
                        }
                </div>
            </Container>
        </div>
    );
  }

export default Jogo;
