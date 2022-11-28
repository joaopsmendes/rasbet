import React,{useState,useEffect} from "react";
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


function HistoricoTransacoes(props){
    {/* const goBack = () => {
    window.history.back();
    }*/}

    const [date, setDate] = useState();
    const [descricao, setDescricao] = useState();
    const [operacao, setOperacao] = useState();
    const [saldo, setSaldo] = useState();
    const [levantamento, setLevantamento] = useState();
    const [deposito, setDeposito] = useState();


    const getHistoricoTransacao=async(email)=>{

        const response = await fetch('http://localhost:8080/historicoTransacoes?' + new URLSearchParams({
            userId: email})
            , {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }            
      });
      const data = await response.json();
      console.log(data);
      setLevantamento(data['levantamento']);
      setDeposito(data['deposito']);
      
    }


    useEffect(()=>{
        const user = JSON.parse(sessionStorage.getItem('user'));
        getHistoricoTransacao(user);
    },[])

    const rows = []

    const goBack = () => {
        //history.back();
        return false;
    }


    return(
        <div className="HistoricoTransacoes">
            <h1>Historico de Transações</h1>
            {/*<Button onclick={"history.go(-1);"}>Voltar</Button>*/}
            <IconButton onClick={goBack}>
              < ArrowBackIcon/>
            </IconButton>
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
                    <TableHead>
                        <TableRow>
                        <TableCell align="right">Data</TableCell>
                        <TableCell align="right">Descrição</TableCell>
                        <TableCell align="right">Operação</TableCell>
                        <TableCell align="right">Saldo após movimento&nbsp;(€)</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {/** preencher a tabela */}
                        {rows.map((row) => (
                            <TableRow
                                key={row.name}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                            <TableCell component="th" scope="row">
                            {row.name}
                            </TableCell>
                            <TableCell align="right">{row.data}</TableCell>
                            <TableCell align="right">{row.descricao}</TableCell>
                            <TableCell align="right">{row.operacao}</TableCell>
                            <TableCell align="right">{row.saldo}</TableCell>
                            </TableRow>
                    ))}
                    </TableBody>
                </Table>
            </TableContainer>

        </div>
    );
}

export default HistoricoTransacoes;