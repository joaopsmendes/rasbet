import React, { useState, useEffect } from "react";
import IconButton from '@mui/material/IconButton';
import Button from '@mui/material/Button';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { ConstructionOutlined } from "@mui/icons-material";
import { Box, Container } from "@mui/material";


function HistoricoTransacoes(props) {
    {/* const goBack = () => {
    window.history.back();
    }*/}

    const [date, setDate] = useState();
    const [descricao, setDescricao] = useState();
    const [operacao, setOperacao] = useState();
    const [saldo, setSaldo] = useState(0);
    const [levantamento, setLevantamento] = useState();
    const [deposito, setDeposito] = useState();
    const [transacoes, setTransacoes] = useState([]);

    let valorSaldo = 0;
    let valorFreebets = 0;

    const getHistoricoTransacao = async () => {
        const sessionId = JSON.parse(sessionStorage.getItem('sessionId'));
        console.log(sessionId);
        const response = await fetch('http://localhost:8080/historicoTransacoes?' + new URLSearchParams({
            sessionId: sessionId
        })
            , {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
        const data = await response.json();
        console.log("HISTORICO TRANSACOES");
        console.log(data);
        data.forEach(element => {
            element['data'] = new Date(Date.parse(element['data']));
        });
        data.sort((a, b) => a['data'] - b['data']);
        setTransacoes(data);

    }


    useEffect(() => {
        getHistoricoTransacao();
    }, [])

    const rows = []

    const goBack = () => {
        //history.back();
        return false;
    }

    const dateString = (date) => {
        var year = date.getFullYear();
        var mes = date.getMonth() + 1;
        var dia = date.getDate();
        return dia + "-" + mes + "-" + year;
    }

    const saldoAposMovimento = (movimento) => {
        console.log(movimento['saldo']);
        valorSaldo += movimento['saldo'];
        return valorSaldo + "€";
    }

    const freebetsAposMovimento = (movimento) => {
        valorFreebets += movimento['freebets'];
        return valorFreebets + "f";
    }

    const getOpercaoNome = (operacao) => {
        operacao = String(operacao);
        operacao = operacao.replace("_", ' ');
        operacao = operacao.toLowerCase();
        operacao = operacao.charAt(0).toUpperCase() + operacao.slice(1);
        return operacao;
    }

    const getValor = (transacao) => {
        let valor = transacao.saldo + transacao.freebets;
        return (valor > 0 ? "+" + valor : valor) + "€";
    }

    const filterByDate = (transacao) => {
        if (!props.filtro) return true;
        if (props.dataInicial === undefined || props.dataFinal === undefined) {
            return true;
        }
        else {
            let dataJogo = new Date(transacao.data);
            dataJogo.setHours(0, 0, 0, 0);
            let dataInicialDate = new Date(props.dataInicial);
            let dataFinalDate = new Date(props.dataFinal);
            return dataJogo >= dataInicialDate && dataJogo <= dataFinalDate;
        }
    }

    const isOutro = (transacao) => {
        return transacao.saldo === 0 && transacao.freebets === 0;
    }


    return (
        <Container maxWidth="xl" sx={{ p: '1%' }}>
            <Box sx={{border: 2, borderRadius: '10px'}}>
                <h1>Historico de Transações</h1>
                <TableContainer component={Box} sx={{mb: '5%',mt:'5%'}}>
                    <Table  size="small" aria-label="a dense table">
                        <TableHead>
                            <TableRow>
                                <TableCell align="right">Data</TableCell>
                                <TableCell align="right">Descrição</TableCell>
                                <TableCell align="right">Operação</TableCell>
                                <TableCell align="right">Saldo após movimento</TableCell>
                                <TableCell align="right">Freebets após movimento</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {/** preencher a tabela */}
                            {transacoes.map((transacao) => (
                                filterByDate(transacao) &&
                                <TableRow
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell align="right">{dateString(transacao.data)}</TableCell>
                                    <TableCell align="right">{getOpercaoNome(transacao.tipo) }</TableCell>
                                    <TableCell align="right">{!isOutro(transacao) ? getValor(transacao) : "OUTRO"}</TableCell>
                                    <TableCell align="right">{saldoAposMovimento(transacao)}</TableCell>
                                    <TableCell align="right">{freebetsAposMovimento(transacao)}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>

            </Box>
        </Container>
    );
}

export default HistoricoTransacoes;