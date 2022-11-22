import React,{useState,useEffect} from "react";

function Odd(props) {

    const[valor,setValor]=useState();
    const[nome,setNome]=useState();
    const[idOdd,setIdOdd]=useState();
    
    useEffect(()=>{
        console.log("here");
        console.log(props.odd);
        setValor(props.odd[1].valor)
        setNome(props.odd[1].opcao)
        setIdOdd(props.odd[0])
    },[])

    return (
        <div className="Odd">
            <button className="OddButton">{nome}<br/>{valor}</button>
        </div>
    );
  }

export default Odd;
