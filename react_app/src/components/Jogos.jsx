import React, { useState, useEffect } from "react";
import Jogo from "./Jogo";
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Paper from '@mui/material/Paper';
import Pagamento from "./Pagamento";
import Boletim from "./Boletim";




function Jogos(props) {
    //passar os desportos no props
    //passar desporto ativo no props

    const [jogos, setJogos] = useState({})




    const getJogos = async () => {
        const response = await fetch('http://localhost:8080/jogos?' + new URLSearchParams({
            desporto: props.desportoAtivo
        })
            , {
                method: 'GET',
            });
        const data = await response.json();
        console.log("DATA");
        console.log(data);
        //const data =response.json();
        var result = Object.keys(data).map((key) => data[key]);
        console.log("JOGOS");
        console.log(data)
        setJogos(result);

    }



    useEffect(() => {
        getJogos();
    }, [props.desportoAtivo])

    /*
        const addOdd = (newAposta) => {
            var newApostaArray = aposta.slice();
            console.log(newAposta)
            for (var i = 0; i < newApostaArray.length; i++) {
                console.log(newApostaArray[i])
                if (newApostaArray[i].desJogo === newAposta.desJogo && newApostaArray[i].tema === newAposta.tema) {
                    newApostaArray[i] = newAposta;
                    setAposta(newApostaArray);
                    console.log("ja existe");
                    return;
                }
            }
            newApostaArray.push(newAposta);
            setAposta(newApostaArray);
        }
    
        const removeOdd = (idOdd) => {
            console.log(idOdd);
            var newApostaArray = aposta.slice();
            newApostaArray = newApostaArray.filter((aposta) => aposta.idOdd !== idOdd);
            setAposta(newApostaArray);
        }
        */

    const handleClick = (odd) => {
        if(props.showBoletim){
            props.handleClick(odd);
        }
        else {
            let idJogo = odd.desJogo;
            console.log("JOGO");
            jogos.map((jogo) => {
                if (jogo.idJogo === idJogo) {
                    odd['titulo'] = jogo.titulo;
                    odd['idJogo'] = idJogo; 
                }
            });
            props.handleClick(odd);
        }
    }



    return (
        <div>
            <Grid container spacing={2}>
                <Grid item xs={12} md={9} xl={9}>
                    {jogos.length > 0 ?
                        jogos.map((jogo) => (<Jogo handleClick={handleClick} key={jogo.idJogo} jogo={jogo} />))
                        : <h1>Não existem jogos disponíveis neste momento</h1>}
                </Grid>
                {props.showBoletim &&
                    <Boletim user={props.user} login={props.login} apostas={props.aposta} setAposta={props.setAposta} />
                }
            </Grid>

        </div>
    );
}

export default Jogos;
