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

    const getHistoricoTransacao = async (email) => {

        const response = await fetch('http://localhost:8080/historicoTransacoes?' + new URLSearchParams({
            userId: email
        })
            , {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
        const data = await response.json();
        console.log(data);
        data.forEach(element => {
            element['data'] = new Date(Date.parse(element['data']));
        });

        data.sort((a, b) => a['data'] - b['data']);
        setTransacoes(data);

    }


    useEffect(() => {
        const user = JSON.parse(sessionStorage.getItem('user'));
        getHistoricoTransacao(user);
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
        if (operacao === 'DEPOSITO') {
            return 'Depósito';
        } else if (operacao === 'LEVANTAMENTO') {
            return 'Levantamento';
        } else if (operacao === 'APOSTA') {
            return 'Aposta';
        } else if (operacao === 'CRIACAO_CONTA') {
            return 'Criação de conta';
        }
    }

    const getValor = (transacao) => {
        let valor = transacao.saldo + transacao.freebets;
        return (valor > 0 ? "+" + valor : valor) + "€";
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

                                <TableRow
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell align="right">{dateString(transacao.data)}</TableCell>
                                    <TableCell align="right">{getOpercaoNome(transacao.tipo)}</TableCell>
                                    <TableCell align="right">{getValor(transacao)}</TableCell>
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