import React, { useState, useEffect } from "react";
import ApostaJogo from "./ApostaJogo";
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';


function Jogo(props) {

    const [apostas, setApostas] = useState([]);
    const [time, setTime] = useState();
    const [id, setID] = useState('');


    const formatarData = (data) => {
        let today = new Date();
        let dataFormatada = new Date(data);
        let dia = dataFormatada.getDate();
        let mes = dataFormatada.getMonth() + 1;
        let ano = dataFormatada.getFullYear();
        let hora = dataFormatada.getHours();
        let minuto = dataFormatada.getMinutes();
        if (today.getDate() == dia && today.getMonth() + 1 == mes && today.getFullYear() == ano) {
            return "Hoje às " + hora + ":" + minuto;
        }
        if (today.getDate() + 1 == dia && today.getMonth() + 1 == mes && today.getFullYear() == ano) {
            return "Amanhã às " + hora + ":" + minuto;
        }
        return dia + "/" + mes + "/" + ano + " " + hora + ":" + minuto;
    }

    useEffect(() => {
        setID(props.jogo.idJogo)
        setTime(formatarData(props.jogo.data));
        let apostasArray = Object.keys(props.jogo.apostas).map((key) => [key, props.jogo.apostas[key]]);
        setApostas(apostasArray);
        console.log("Jogo");
        console.log(props.jogo);
    }, [])





    return (
        <div className="Jogo">
            <Box size="md" sx={{ m: '2%', p: '1%', border: 2, borderRadius: '30px' }} >
                <Container sx={{ maxWidth: '80%', margin: 'auto' }} maxWidth="false">
                    <h2>{props.jogo.titulo}</h2>
                    <p>{time}</p>
                    <div className="Container">
                        {
                            apostas.length > 0 && apostas.map((aposta) => (<ApostaJogo handleClick={props.handleClick} removeOdd={props.removeOdd} addOdd={props.addOdd} key={aposta.tema} aposta={aposta} />))
                        }
                    </div>
                </Container>
            </Box>
        </div>
    );
}

export default Jogo;
