import React,{useState,useEffect} from "react";


function Desportos(props) {
    //passar os desportos no props
    //passar desporto ativo no props
    
    const[desportos,setDesportos]=useState([])



    const getDesportos=async()=>{
        const response = await fetch('http://localhost:8080/desportos',{
            method: 'GET',
        });
        
        const data = await response.json();
        //array to map
        var result = Object.keys(data).map((key) => data[key]);
        console.log("DATA");
        console.log(result);
        setDesportos(result);
    }

    useEffect(()=>{    
        
        getDesportos();        
        //fetch('http://localhost:8080/api/desportos')
        //.then(response=>response.json())
        //.then(data=>setdesportos(data))


    },[])


    return (
        <div>
            {desportos.length > 0 && desportos.map((desporto)=>(<p>{desporto.modalidade}</p>))}  
        </div>
        );
    }

export default Desportos;
