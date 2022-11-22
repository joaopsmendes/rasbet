import React,{useState,useEffect} from "react";
import Odd from "./Odd";


function Aposta(props) {

    const[odds,setOdds]=useState([]);
    const[nome,setNome]=useState('');


    useEffect(()=>{
        let arrayOdds = Object.keys(props.aposta[1].mapOdd).map((key) => [key,props.aposta[1].mapOdd[key]]);
        setOdds(arrayOdds)
        setNome(props.aposta[0])
    },[]);

    return (
        <div className="Aposta">
            <div className="Container">
                {
                    odds.length> 0 && odds.map((odd)=>(<Odd odd={odd}/>))
                }
            </div>
        </div>
    );
  }

export default Aposta;
