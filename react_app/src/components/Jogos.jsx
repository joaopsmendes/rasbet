import { RowingSharp } from "@mui/icons-material";
import React,{useState,useEffect} from "react";
import Jogo from "./Jogo";


function Jogos(props) {
    //passar os desportos no props
    //passar desporto ativo no props
    
    const[jogos,setJogos]=useState({})
    const[aposta,setAposta]=useState([])



    const getJogos=async()=>{
        const response = await fetch('http://localhost:8080/jogos?' + new URLSearchParams({
                desporto: "futebol"})
            , {
            method: 'GET',
        });
        const data = await response.json();
        //const data =response.json();
        var result = Object.keys(data).map((key) => data[key]);
        console.log("RESULT");
        console.log(result);
        setJogos(result);

    }

    useEffect(()=>{    
        
        getJogos(); 
        //fetch('http://localhost:8080/api/jogos')
        //.then(response=>response.json())
        //.then(data=>setJogos(data))

    },[])


    const addAposta = (newAposta) => {
        console.log("addAposta");
        console.log(newAposta);
        var newApostaArray = aposta.slice();
        newApostaArray.filter((aposta)=>aposta.idOdd==newAposta.idOdd).length==0 && newApostaArray.push(newAposta);
        setAposta([...aposta, newAposta]);
    }


    return (
        <div>
            {/*
            <div className="Desportos">}
                    {props.desportos.map((desporto)=>(<p>{desporto}</p>))}
            </div>}}
            */}
                <div className="Jogos">
                        {jogos.length > 0 && jogos.map((jogo)=>(<Jogo addAposta={addAposta} key={jogo.idJogo} jogo={jogo}/>))}
                </div>
                <ul>
                    {aposta.length > 0 && aposta.map((aposta)=>(<p>{aposta.opcao}</p>))}
                </ul>
        </div>
    );
  }

export default Jogos;
