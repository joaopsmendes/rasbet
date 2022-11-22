import React,{useState,useEffect} from "react";
import Aposta from "./Aposta";

function Jogo(props) {

    const[apostas,setApostas]=useState([]);
    const[time,setTime]=useState('');
    const[id,setID]=useState('');

    useEffect(()=>{
        setID(props.jogo.idJogo)
        setTime(props.jogo.data)
        let apostasArray = Object.keys(props.jogo.apostas).map((key) => [key,props.jogo.apostas[key]]); 
        console.log(apostasArray);
        setApostas(apostasArray);
    },[])

    return (
        <div className="Jogo">
            <h2>{time}</h2>
            <div className="Container">
                {
                    apostas.length > 0 && apostas.map((aposta)=>(<Aposta aposta={aposta}/>))
                }
            </div>
        </div>
    );
  }

export default Jogo;
