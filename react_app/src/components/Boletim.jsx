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
import DeleteIcon from '@mui/icons-material/Delete';



function Boletim(props) {
    //passar os desportos no props
    //passar desporto ativo no props

    const [montante, setMontante] = useState(1)
    const [pagamento, setPagamento] = useState(false)



    useEffect(() => {

    }, [props.apostas])



    const getTypesApostas = () => {

        return <h1>{(props.apostas.length == 1 ? "Simples" : "Múltipla")}</h1>;
    }


    const getCotaTotal = () => {
        let soma = 1;
        for (var i = 0; i < props.apostas.length; i++) {
            soma *= props.apostas[i].valor;
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
                sx={{ mr: 2 }}
                type="number"
                autoFocus
                defaultValue={1}
                onChange={(e) => setMontante(e.target.value)}
                error={montante <= 0 || montante > 10000}
                helperText={montante <= 0 || montante > 10000 ? 'Valor inválido' : ' '}
                InputProps={{ inputProps: { min: 0.01, max: 10000 } }} />
        );
    }

    const doAposta = async (montanteSaldo, montanteFreeBets, setSucesso) => {
        const sessionId = JSON.parse(sessionStorage.getItem('sessionId'));

        const ganhos = getMontanteTotal();
        if (sessionId === null) {
            alert("Tem de fazer login para apostar");
            return;
        }

        const odds = props.apostas.map((odd) => odd.idOdd);
        const response = await fetch('http://localhost:8080/aposta', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                sessionId: sessionId,
                valor: montante,
                saldo: montanteSaldo,
                freebets: montanteFreeBets,
                odds: odds,
                ganhoPossivel: ganhos,

            }),
        });
        if (response.status === 200) {
            setSucesso(true);
            props.setAposta([]);
        } else {
            alert("Erro ao fazer a aposta");
        }
        console.log(response);
    }

    const getTituloJogo = (idJogo) => {
        for (var i = 0; i < props.jogos.length; i++) {
            if (props.jogos[i].idJogo === idJogo) {
                return props.jogos[i].titulo;
            }
        }
    }


    return (

        <Grid item xs={12} md={3} xl={3}>

            {pagamento && <Pagamento valor={montante} setPagamento={setPagamento} pagamento={pagamento} submit={doAposta} />}
            {
                props.apostas.length > 0 ?
                    <Box size="md" sx={{ m: 3, border: 2, borderRadius: '20px' }} >
                        <Grid item xs={12}>
                            <div>{getTypesApostas()}</div>
                            <div>{props.apostas.length > 0 && props.apostas.map((aposta) =>
                            (
                                <Grid container spacing={2}>
                                    <Grid item xs={10} >
                                        <b>{getTituloJogo(aposta.desJogo)}</b>
                                    </Grid>
                                    <Grid item xs={2}>
                                        <DeleteIcon onClick={() => props.handleClick(aposta) } />
                                    </Grid>
                                    <Grid item xs={6}>
                                        {aposta.nome}: {aposta.opcao}
                                    </Grid>
                                    <Grid item xs={6}>
                                        Cota : {aposta.valor}
                                    </Grid>
                                </Grid>
                            ))}
                            </div>
                            <hr />
                            <Grid container spacing={2} alignItems="center" justifyContent="center">
                                <Grid item xs={6}>
                                    <p>Cota: {getCotaTotal()}</p>
                                </Grid>
                                <Grid item xs={5}>
                                    {montanteField()}
                                </Grid>
                                <Grid item xs={1} />
                            </Grid>
                            <Grid container spacing={2} alignItems="center" justifyContent="center">
                                <Grid item xs={6}>
                                    <p>Total de Ganhos <br /></p><b style={{ color: "#E67644" }}>{getMontanteTotal()}</b>
                                </Grid>
                                <Grid item xs={5}>
                                    <Button color="secondary" sx={{ borderRadius: '30px' }} disabled={montante <= 0 || !props.login} onClick={handlePagamento} variant="contained">
                                        <b>{props.login ? "Apostar" : "Login Necessário"}</b>
                                    </Button>
                                </Grid>
                                <Grid item xs={1} />
                            </Grid>
                        </Grid>

                    </Box>
                    : null
            }
        </Grid >

    );
}

export default Boletim;
