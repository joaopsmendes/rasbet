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

    let valor = 0;

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
        data['levantamento'].forEach(element => {
            element['data'] = new Date(Date.parse(element['data']));
            element['operacao'] = "Levantamento";
        });

        data['deposito'].forEach(element => {
            element['data'] = new Date(Date.parse(element['data']));
            element['operacao'] = "Depósito";
        });

        let array = data['levantamento'].concat(data['deposito']);
        array.sort((a, b) => a['data'] - b['data']);
        setTransacoes(array);

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
        var mes =  date.getMonth()+1;
        var dia =  date.getDate();
        return dia+"-"+mes+"-"+year;
    }

    const saldoAposMovimento = (movimento) => {
        if(movimento['operacao'] == "Levantamento"){
            valor -= movimento['valor'];
            return valor;
        }
        else{
            valor += movimento['valor'];
            //setSaldo(valor);
            return valor;
        }
    }



    return (
        <div className="HistoricoTransacoes">
            <h1>Historico de Transações</h1>
            {/*<Button onclick={"history.go(-1);"}>Voltar</Button>*/}
            <IconButton onClick={goBack}>
                < ArrowBackIcon />
            </IconButton>
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
                    <TableHead>
                        <TableRow>
                            <TableCell align="right">Data</TableCell>
                            <TableCell align="right">Descrição</TableCell>
                            <TableCell align="right">Operação</TableCell>
                            <TableCell align="right">Saldo após movimento&nbsp;(€)</TableCell>
                            <TableCell align="right">Freebets após movimento&nbsp;(€)</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {/** preencher a tabela */}
                        {transacoes.map((transacao) => (
                            
                            <TableRow
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell align="right">{dateString(transacao.data)}</TableCell>
                                <TableCell align="right">{transacao.operacao}</TableCell>
                                <TableCell align="right">{transacao.operacao ==='Depósito' ? '+' : '-'}{transacao.valor}</TableCell>
                                <TableCell align="right">{saldoAposMovimento(transacao)}</TableCell>
                                <TableCell align="right">?</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

        </div>
    );
}

export default HistoricoTransacoes;