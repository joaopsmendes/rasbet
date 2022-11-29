import React, { useState, useEffect } from "react";
import Jogo from "./Jogo";
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Paper from '@mui/material/Paper';
import Pagamento from "./Pagamento";
import { Container } from "@mui/material";
import { flexbox } from '@mui/system';




function Jogos(props) {
    //passar os desportos no props
    //passar desporto ativo no props

    const [jogos, setJogos] = useState({})
    const [aposta, setAposta] = useState([])
    const [montante, setMontante] = useState(1)
    const [isSimples, setIsSimples] = useState(true)
    const [pagamento, setPagamento] = useState(false)




    const getJogos = async () => {
        const response = await fetch('http://localhost:8080/jogos?' + new URLSearchParams({
            desporto: "futebol"
        })
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

    useEffect(() => {

        getJogos();
        //fetch('http://localhost:8080/api/jogos')
        //.then(response=>response.json())
        //.then(data=>setJogos(data))

    }, [])


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



    const getTypesApostas = () => {

        return <h1>{(aposta.length == 1 ? "Simples" : "Múltipla")}</h1>;
    }


    const getCotaTotal = () => {
        let soma = 1;
        for (var i = 0; i < aposta.length; i++) {
            soma *= aposta[i].valor;
        }
        return soma.toFixed(2);
    }

    const getMontanteTotal = () => {
        return (getCotaTotal() * montante).toFixed(2);
    }

    const handlePagamento = () => {
        setPagamento(true);
    }

    const montanteField = () => {
        return (
            <TextField margin="normal" required fullWidth name="Montante" label="Montante" id="Montante"
                sx={{mr: 2}} 
                type="number"
                defaultValue={1}
                onChange={(e) => setMontante(e.target.value)}
                error={montante <= 0}
                helperText={montante <= 0 ? 'Valor inválido' : ' '}
                InputProps={{ inputProps: { min: 0.01 } }} />
        );
    }

    const doAposta = async () => {

        console.log("DO APOSTA");
        console.log(props.user);
        const user = JSON.parse(sessionStorage.getItem('user'));
        console.log(user);

        if (user === null) {
            alert("Tem de fazer login para apostar");
            return;
        }

        const odds = aposta.map((odd) => odd.idOdd);
        const response = await fetch('http://localhost:8080/aposta', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                userId: user,
                valor: montante,
                odds: odds,
            }),
        });
        if (response.status === 200) {
            alert("Aposta feita com sucesso");
        } else {
            alert("Erro ao fazer a aposta");
        }
        console.log(response);
    }



    return (
        <div>
            {/*
            <div className="Desportos">}
                    {props.desportos.map((desporto)=>(<p>{desporto}</p>))}
            </div>}}
            */}
            {pagamento && <Pagamento valor={montante} setPagamento={setPagamento} pagamento={pagamento} submit={doAposta} />}
            <Grid container spacing={2} alignItems="center" justifyContent="center">
                <Grid item xs={10} md={9} xl={9}>
                    <Box sx={{ m: 5, border: 2, borderRadius: '10%', width: '90%' }}>
                        {jogos.length > 0 && jogos.map((jogo) => (<Jogo removeOdd={removeOdd} addOdd={addOdd} key={jogo.idJogo} jogo={jogo} />))}
                    </Box>
                </Grid>
                {aposta.length > 0 ?
                    <Grid item xs={12} md={3} xl={3}>
                        <Box size="md" sx={{ m: 3, border: 2, borderRadius: '10%', alignItems: "center", justifyContent: "center", width: '90%' }} >
                            <Grid item xs={12}>
                                <div>{getTypesApostas()}</div>
                                <div>{aposta.length > 0 && aposta.map((aposta) => (<p>{aposta.nome}: {aposta.opcao}<br /><br /> Cota : {aposta.valor} </p>))}</div>
                                <hr/>
                                <Grid container spacing={2} alignItems="center" justifyContent="center">
                                    <Grid item xs={6}>
                                        <p>Cota: {getCotaTotal()}</p>
                                    </Grid>
                                    <Grid item xs={5}>
                                        {montanteField()}
                                    </Grid>
                                    <Grid item xs={1}/>
                                        
                                </Grid>
                                <Grid container spacing={2} alignItems="center" justifyContent="center">
                                    <Grid item xs={6}>
                                        <p>Total de Ganhos <br/></p><b style={{color:"#E67644"}}>{getMontanteTotal()}</b>
                                    </Grid>
                                    <Grid item xs={5}>
                                        <Button color="secondary" sx={{ borderRadius: '30px' }} disabled={montante <= 0} onClick={handlePagamento} variant="contained">
                                            <b>Apostar</b>
                                        </Button>
                                    </Grid>
                                    <Grid item xs={1}/>


                                </Grid>
                            </Grid>

                        </Box>
                    </Grid>
                    : null
                }
            </Grid>

        </div>
    );
}

export default Jogos;
