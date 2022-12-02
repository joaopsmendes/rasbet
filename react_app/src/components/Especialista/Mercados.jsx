

import React, { useState, useEffect } from "react";
import Mercado from "./Mercado";

function Mercados(props) {

    const [mercados, setMercados] = useState([]);

    useEffect(() => {
        console.log("Mercados");
        let mercadosArray = Object.keys(props.mercados).map((key) => [key, props.mercados[key]]);
        console.log(mercadosArray[0])
        setMercados(mercadosArray);
    }, [])



    const addJogo = (mercado) => {
        props.addJogo(props.idJogo,mercado);
    }


    return (
        <div className="Jogo">
            {mercados.length > 0 && mercados.map((mercado) =>
                <Mercado key={mercado[0]} mercado={mercado} addJogo={addJogo} jogos={props.jogos}/>
            )}
        </div>
    );
}

export default Mercados;
