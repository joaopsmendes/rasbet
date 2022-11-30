

import React, { useState, useEffect } from "react";
import ApostaJogo from "./ApostaJogo";
import Container from '@mui/material/Container';


function Mercado(props) {

    const [mercados, setMercados] = useState([]);

    useEffect(() => {
        console.log("Mercados");
        console.log(props.mercados);
        let mercadosArray = Object.keys(props.mercados).map((key) => [key, props.mercados[key]]);
        setMercados(mercadosArray);
    }, [])





    return (
        <div className="Jogo">
            {mercados.length > 0 && mercados.map((mercado) =>
                <div>
                    <h1>{mercado[0]}</h1>
                    <ApostaJogo key={mercado[1].tema} odds={mercado[1].oods }/>
                </div>)}
        </div>
    );
}

export default Mercado;
