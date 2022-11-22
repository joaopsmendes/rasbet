import React,{useState,useEffect} from "react";
import Aposta from "./Aposta";

function Jogo(props) {

    const[apostas,setApostas]=useState([]);
    const[time,setTime]=useState();
    const[id,setID]=useState('');

    useEffect(()=>{
        setID(props.jogo.idJogo)
        const date = new Date(props.jogo.data);
        setTime(date.toLocaleString());
        let apostasArray = Object.keys(props.jogo.apostas).map((key) => [key,props.jogo.apostas[key]]); 
        console.log("APOSTAS ARRAY");
        console.log(apostasArray);
        setApostas(apostasArray);
    },[])

    return (
        <div className="Jogo">
            <h3>{time}</h3>
            <div className="Container">
                <ul>
                    {
                        apostas.length > 0 && apostas.map((aposta)=>(<Aposta key={aposta.tema} aposta={aposta}/>))
                    }
                </ul>
            </div>
        </div>
    );
  }

export default Jogo;
