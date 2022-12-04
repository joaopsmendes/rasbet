
import { useEffect, useState } from 'react';
import Mercados from './Mercados';
import ResponsiveAppBar from '../Appbar';
import { Button, Container, Divider } from '@mui/material';
import CircularProgress from '@mui/material/CircularProgress';
import { Box } from '@mui/system';
import Grid from '@mui/material/Grid';
import Jogos from '../Jogos';

import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import DialogActions from '@mui/material/DialogActions';
import DialogContentText from '@mui/material/DialogContentText';
import AlteracaoOdd from './AlteracaoOdd';

import { TextField } from '@mui/material';

function PageEspecialista(props) {


  const [load, setLoad] = useState(false);
  const [jogos, setJogos] = useState([]);
  const [desportos, setDesportos] = useState([]);
  const [desportoAtivo, setDesportoAtivo] = useState('futebol');
  const [toAdd, setToAdd] = useState(false);
  const [ativos, setAtivos] = useState(true);
  const [open, setOpen] = useState(false);
  const [jogoAlterado, setJogoAlterado] = useState();


  const getJogosAdd = async () => {
    const response = await fetch('http://localhost:8080/showGamesToAdd', {
      method: 'GET',
    });
    const data = await response.json();
    //const data =response.json();
    console.log("RESULT TO ADD");
    console.log(data);
    setLoad(true);
    setJogos(data['futebol']);
  }

  const getDesportos = async () => {
    const response = await fetch('http://localhost:8080/desportos', {
      method: 'GET',
    });
    const data = await response.json();
    //array to map

    var result = Object.keys(data).map((key) => data[key].modalidade);
    console.log("DATA");
    console.log(result);
    setDesportos(result);
  }


  useEffect(() => {
    //getJogosAdd();
    getDesportos();
  }
    , []);


  const settingsOptions = {
    'Terminar Sessão': props.loggout
  }


  const handleToAdd = () => {
    setToAdd(!toAdd);
    setAtivos(false);
    if (!toAdd) {
      getJogosAdd();
    }
  }


  const removeJogo = (id) => {
    console.log(jogos);
    setJogos(jogos.filter((jogo) => jogo.idJogo !== id));
    console.log(jogos);
  }

  const handleJogosAtivos = () => {
    setAtivos(!ativos);
    setToAdd(false);
  }

  const update = () => {
    window.location.reload();
  }



  const addJogo = async (idJogo, mercado) => {
    const jogo = jogos.find((jogo) => jogo.idJogo === idJogo);
    jogo['escolhido'] = mercado;
    console.log(jogo);
    const response = await fetch('http://localhost:8080/adicionarJogo', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(jogo)
    });
    alert("Jogo adicionado com sucesso!");
    removeJogo(idJogo);
  }

  const handleClick = (odd) => {
    console.log("CLICKED");
    console.log(odd);
    setJogoAlterado(odd);
    setOpen(true);
  }


  const setAlignment = (alignment) => {
    return null;
  }




  return (
    <div className="App">
      <ResponsiveAppBar
        showDesportos={true}
        desportoAtivo={desportoAtivo}
        setDesportoAtivo={setDesportoAtivo}
        pages={desportos}
        settings={settingsOptions}
        isLogin={props.isLogin} />

      {open && <AlteracaoOdd jogo={jogoAlterado} setMaster={setOpen} update={update} />}



      <Box sx={{ display: 'flex' }}>
        <Grid
          container
          spacing={0}
          alignItems="center"
          justifyContent="center"
          style={{ minHeight: '10vh' }}
        >
          <Grid item xs={12} md={6}>
            <Button fullWidth variant="contained" onClick={handleJogosAtivos} color={ativos ? "secondary" : "inherit"}>Jogos Ativos</Button>
          </Grid>
          <Grid item xs={12} md={6}>
            <Button fullWidth variant="contained" onClick={handleToAdd} color={toAdd ? "secondary" : "inherit"}>Jogos para adicionar</Button>
          </Grid>
          <Divider />
          <Grid item xs={12}>
            {ativos && <Jogos setAlignment={setAlignment} showBoletim={false} desportoAtivo={desportoAtivo} userId={props.user} login={props.isLogin} handleClick={handleClick} />}
            {toAdd && !load && <CircularProgress />}
            {toAdd && load && jogos.length === 0 && <h1>Não existem jogos para adicionar</h1>}
            {toAdd && load && jogos.length > 0 && jogos.map((jogo) => (
              <Box sx={{ border: 1, borderRadius: '10px', m: '5%' }}>
                <h1>{jogo.titulo}</h1>
                <Mercados mercados={jogo['mapMercados']} addJogo={addJogo} idJogo={jogo.idJogo} jogos={jogos} />
              </Box>))
            }
          </Grid>

        </Grid>

      </Box>


    </div >
  );
}

export default PageEspecialista;