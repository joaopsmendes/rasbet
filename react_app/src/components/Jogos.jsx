import React,{useState,useEffect} from "react";
import Jogo from "./Jogo";
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Paper from '@mui/material/Paper';
import Pagamento from "./Pagamento";





function Jogos(props) {
    //passar os desportos no props
    //passar desporto ativo no props
    
    const[jogos,setJogos]=useState({})
    const[aposta,setAposta]=useState([])
    const[montante,setMontante]=useState(1)
    const[isSimples,setIsSimples]=useState(true)
    const[pagamento,setPagamento]=useState(false)




    const getJogos=async()=>{
        const response = await fetch('http://localhost:8080/jogos?' + new URLSearchParams({
                desporto: "futebol"})
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

    useEffect(()=>{    
        
        getJogos(); 
        //fetch('http://localhost:8080/api/jogos')
        //.then(response=>response.json())
        //.then(data=>setJogos(data))

    },[])


    const addOdd= (newAposta) => {
        console.log("NEW APOSTA");
        var newApostaArray = aposta.slice();
        for (var i = 0; i < newApostaArray.length; i++) {
            if (newApostaArray[i].idJogo === newAposta.idJogo && newApostaArray[i].tema === newAposta.tema) {
                console.log("Já existe");
                newApostaArray[i] = newAposta;
                setAposta(newApostaArray);
                return;
            }
        }
        newApostaArray.push(newAposta);
        setAposta(newApostaArray);
    }

    const removeOdd= (idOdd) => {
        console.log("REMOVE APOSTA");
        console.log(idOdd);
        var newApostaArray = aposta.slice();
        newApostaArray = newApostaArray.filter((aposta) => aposta.idOdd !== idOdd);
        setAposta(newApostaArray);
    }



    const getTypesApostas = () => {

        return <h1>{(isSimples  ? "Simples" : "Múltipla")}</h1>;
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
        return(
        <TextField margin="normal" required fullWidth name="Montante" label="Montante" id="Montante" 
            type="number"
            defaultValue={1}
            onChange={(e)=>setMontante(e.target.value)}
            error={montante <= 0 }
            helperText={montante <= 0 ? 'Valor inválido' : ' '}
            InputProps={{ inputProps: { min: 0.01 } }} />
        );
    }

    const doAposta = async() => {

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
            {pagamento && <Pagamento valor={montante} setPagamento={setPagamento} pagamento={pagamento} submit={doAposta}/>}
                <Box sx={{ flexGrow: 1 }}>
                    <Grid container spacing={2}>
                        <Grid item xs>
                          {jogos.length  > 0 && jogos.map((jogo)=>(<Jogo removeOdd={removeOdd} addOdd={addOdd} key={jogo.idJogo} jogo={jogo}/>))}
                        </Grid>
                        {aposta.length > 0 ?
                            <Grid item xs={2}>
                                <div>{getTypesApostas()}</div>
                                <div>{aposta.length > 0 && aposta.map((aposta)=>(<p>{aposta.nome}: {aposta.opcao}<br/><br/> Cota : {aposta.valor} </p>))}</div>
                                <Grid container spacing={2}>
                                    <Grid item > 
                                        <div><p>Cota Total: {getCotaTotal()}<br/>Total de Ganhos:{getMontanteTotal()}</p></div>
                                    </Grid>  
                                    <Grid item xs={3}>
                                        {montanteField()}
                                    </Grid>
                                </Grid>
                                <Button disabled={montante <= 0} onClick={handlePagamento} variant="contained">
                                    Apostar
                                </Button>
                            </Grid>
                        : 
                        <Grid item xs={2}>
                            <h1>No Odds</h1>
                        </Grid>
                        }
                    </Grid>
                </Box>

        </div>
    );
  }

export default Jogos;
