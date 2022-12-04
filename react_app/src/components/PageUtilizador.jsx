import Login from '././Login';
import ResponsiveAppBar from './Appbar';
import Jogos from './Jogos';
import { useEffect, useState } from 'react';
import Registo from './Registo';
import Pagamento from './Pagamento';
import Historico from './Historico';
import Dialogo from './Dialogo';
import Perfil from './Perfil';
import PublicOffIcon from '@mui/icons-material/PublicOff';
import FavoriteIcon from '@mui/icons-material/Favorite';
import SearchIcon from '@mui/icons-material/Search';
import Favoritos from './Favoritos';


function PageUtilizador(props) {


  const [showJogos, setShowJogos] = useState(true);
  const [showHistorico, setShowHistorico] = useState(false);
  const [showPerfil, setShowPerfil] = useState(false);
  const [isDown, setIsDown] = useState(false);
  const [desportos, setDesportos] = useState([]);
  const [desportoAtivo, setDesportoAtivo] = useState('futebol');
  const [aposta, setAposta] = useState([])
  const [notificacoes, setNotificacoes] = useState(false);
  const [favoritos,setFavoritos] = useState({});



  const handleClickOdd = (odd) => {
    let newApostaArray = aposta.slice();
    for (var i = 0; i < newApostaArray.length; i++) {
      if (newApostaArray[i].idOdd === odd.idOdd) {
        removeOdd(odd.idOdd);
        return;
      }
      if (newApostaArray[i].desJogo === odd.desJogo && newApostaArray[i].tema === odd.tema) {
        newApostaArray[i] = odd;
        setAposta(newApostaArray);
        console.log("ja existe");
        return;
      }
    }
    newApostaArray.push(odd);
    console.log(odd);
    setAposta(newApostaArray);
  }

  const removeOdd = (idOdd) => {
    var newApostaArray = aposta.slice();
    newApostaArray = newApostaArray.filter((aposta) => aposta.idOdd !== idOdd);
    setAposta(newApostaArray);
  }


  useEffect(() => {
    getDesportos();
    getFavoritos();
  }, []);





  const handleHistorico = () => {
    setShowJogos(false);
    setShowPerfil(false);
    setShowHistorico(true);
  }

  const handlePerfil = () => {
    setShowJogos(false);
    setShowPerfil(true);
    setShowHistorico(false);
  }

  const handleJogos = () => {
    setShowJogos(true);
    setShowPerfil(false);
    setShowHistorico(false);
  }


  const loggout = () => {
    props.loggout();
    setShowJogos(true);
    setShowPerfil(false);
    setShowHistorico(false);
    setNotificacoes(false);
  }

  const handleNotifcacoes = () => {
    
  }


  const settingsOptions = {
    'Perfil': handlePerfil,
    'Histórico': handleHistorico,
    'Terminar Sessão': loggout
  }


  const setAlignment = (newAlignment) => {
    return newAlignment;
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

  const getFavoritos = async () => {

    const response = await fetch('http://localhost:8080/favoritos?' + new URLSearchParams({
      userId: props.user
    }));
    const data = await response.json();
    //array to map
    console.log("DATA");
    console.log(data);
    //setFavoritos(data);
  }


  return (
    <div className="App">
      {!isDown ?
        <div>
          <ResponsiveAppBar
            showDesportos={showJogos}
            desportoAtivo={desportoAtivo}
            setDesportoAtivo={setDesportoAtivo}
            pages={desportos}
            isLogin={props.isLogin}
            login={props.login}
            settings={settingsOptions}
          />
          {showPerfil && <Perfil />}
          {showJogos && <Jogos setAlignment={setAlignment} showBoletim={true} aposta={aposta} setAposta={setAposta} desportoAtivo={desportoAtivo} userId={props.user} login={props.isLogin} handleClick={handleClickOdd} />}
          {showHistorico && <Historico nome={props.user} />}
        </div>
        :
        <div>
          <PublicOffIcon sx={{ fontSize: '200px' }} />
          <h1>Sorry, the app is down</h1>
        </div>


      }
    </div>
  );
}

export default PageUtilizador;