
import { useEffect, useState } from 'react';
import Mercados from './Mercados';
import ResponsiveAppBar from '../Appbar';
import { Button, Divider } from '@mui/material';
import CircularProgress from '@mui/material/CircularProgress';
import { Box } from '@mui/system';
import Grid from '@mui/material/Grid';

function PageEspecialista() {


  const [load, setLoad] = useState(false);
  const [jogos, setJogos] = useState([]);
  const [desportos, setDesportos] = useState([]);
  const [desportoAtivo, setDesportoAtivo] = useState('futebol');
  const [login, setLogin] = useState(false);
  const [toAdd, setToAdd] = useState(false);
  const [ativos, setAtivos] = useState(false);
  const [user, setUser] = useState();


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
    //getJogosAdd();\
    getDesportos();
    if (sessionStorage.getItem('user')) {
      setLogin(true);
      setUser(JSON.parse(sessionStorage.getItem('user')));
    }
  }
    , []);

  const handleLogout = () => {
    setLogin(false);
    sessionStorage.removeItem('user');
  }

  const settingsOptions = {
    'Terminar Sessão': handleLogout
  }


  const handleToAdd = () => {
    setToAdd(!toAdd);
    getJogosAdd();
  }


  const removeJogo = (id) => {
    setJogos(jogos.filter((jogo) => jogo.id !== id));
  }


  const addJogo = async (idJogo, mercado) => {
    const response = await fetch('http://localhost:8080/addGame', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        gameId: idJogo,
        keyBookmaker: mercado,
        desporto: desportoAtivo
      })
    });
    alert("Jogo adicionado com sucesso!");
    const data = await response.json();
    removeJogo(idJogo);

  }


  return (
    <div className="App">
      <ResponsiveAppBar desportoAtivo={desportoAtivo} setDesportoAtivo={setDesportoAtivo} pages={desportos} settings={settingsOptions} />
      <Box sx={{ display: 'flex' }}>
        <Grid
          container
          spacing={0}
          alignItems="center"
          justifyContent="center"
          style={{ minHeight: '10vh' }}
        >
          <Grid item xs={12} md={6}>
            <Button fullWidth variant="contained" onClick={handleToAdd} color={toAdd ? "secondary" : "inherit"}>Jogos para adicionar</Button>
          </Grid>
          <Grid item xs={12} md={6}>
            <Button fullWidth variant="contained" onClick={getJogosAdd} color={ativos ? "primary" : "inherit"}>Jogos Ativos</Button>
          </Grid>
          <Divider />
          <Grid item xs={12}>
            {toAdd &&
              !load ? <CircularProgress /> :
              <div>
                {load && jogos.length > 0 && jogos.map((jogo) => (
                  <Box sx={{ border: 1, borderRadius: '10px', m: '5%' }}>
                    <h1>{jogo.titulo}</h1>
                    <Mercados mercados={jogo['mapMercados']} addJogo={addJogo} idJogo={jogo.idJogo} jogos={jogos} />
                  </Box>))
                }
              </div>
            }
          </Grid>

        </Grid>

      </Box>


    </div>
  );
}

export default PageEspecialista;