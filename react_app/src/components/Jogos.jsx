import { RowingSharp } from "@mui/icons-material";
import React,{useState,useEffect} from "react";
import Jogo from "./Jogo";


function Jogos(props) {
    //passar os desportos no props
    //passar desporto ativo no props
    
    const[jogos,setJogos]=useState({})



    const getJogos=async()=>{
        const response = fetch('http://localhost:8080/jogos?' + new URLSearchParams({
                desporto: "futebol"})
            , {
            method: 'GET',
        });
        const data = await(await response).json();
        console.log(data);
        var result = Object.keys(data).map((key) => data[key]);
        console.log(result.length);
        console.log(result);


        setJogos(result);

    }

    useEffect(()=>{    
        
        getJogos();



        
        //fetch('http://localhost:8080/api/jogos')
        //.then(response=>response.json())
        //.then(data=>setJogos(data))


    },[])


    return (
        <div>
            {/*
            <div className="Desportos">}
                    {props.desportos.map((desporto)=>(<p>{desporto}</p>))}
            </div>}}
            */}
            {}
                <div className="Jogos">
                        {jogos.length > 0 && jogos.map((jogo)=>(<Jogo jogo={jogo}/>))}
                </div>
        
        </div>
    );
  }

export default Jogos;
