

import React, { useState, useEffect } from "react";
import Container from '@mui/material/Container';
import ApostaJogo from "./ApostaJogo"
import { Button } from "@mui/material";
import Box from '@mui/material/Box';
import Divider from "@mui/material/Divider";

function Mercado(props) {

    const [nome, setNome] = useState();
    const [apostas, setApostas] = useState([]);

    useEffect(() => {
        setNome(props.mercado[0]);
        setApostas(props.mercado[1]);
    }, [])


    const addJogo = () => {  
        props.addJogo(nome);
    }


    return (
        <div className="Jogo">
            <h2>{nome}</h2>
            <Box sx={{ p: 1, m: 1 }}>
                {
                    apostas.length > 0 && apostas.map((aposta) =>
                        <div>
                            <ApostaJogo key={aposta.tema} name={aposta.tema} odds={aposta.odds} />
                            <Button  onClick={addJogo} sx={{ m: 1 }} variant="contained" color="inherit" >Adicionar Jogo</Button>
                            <Divider/>
                        </div>
                    )}
            </Box>
        </div>
    );
}

export default Mercado;
