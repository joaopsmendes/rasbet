import Login from '././Login';
import ResponsiveAppBar from './Appbar';
import Jogos from './Jogos';
import { useEffect, useState } from 'react';
import Historico from './Historico';
import Perfil from './Perfil';
import PublicOffIcon from '@mui/icons-material/PublicOff';
import Notificacoes from './Notificacoes';



function PageUtilizador(props) {

  const maxApostas =20;

  const [showJogos, setShowJogos] = useState(true);
  const [showHistorico, setShowHistorico] = useState(false);
  const [showPerfil, setShowPerfil] = useState(false);
  const [isDown, setIsDown] = useState(false);
  const [desportos, setDesportos] = useState([]);
  const [desportoAtivo, setDesportoAtivo] = useState('futebol');
  const [aposta, setAposta] = useState([])
  const [notificacoes, setNotificacoes] = useState(false);
  const [favoritos, setFavoritos] = useState({});
  const [jogosAseguir,setJogosAseguir] = useState([]);



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
    if(newApostaArray.length>= maxApostas){
      alert("Aposta maxima de "+maxApostas+" odds");
      return;
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


  useEffect(() => {
    getFavoritos();
  }, [desportoAtivo, props.isLogin]);


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
    if(aposta.length >= maxApostas) return null;
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
    const user = JSON.parse(sessionStorage.getItem('user'));
    if (!user) return;
    const response = await fetch('http://localhost:8080/favoritos?' + new URLSearchParams({
      userId: user
    }), {
      method: 'GET'
    }
    );
    const data = await response.json();
    //array to map
    let obj = []
    data.forEach((favorito) => {
      if (favorito.desporto.modalidade === desportoAtivo) {
        obj.push(String(favorito.nome));
      }
    });
    setFavoritos(obj);
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
            setFavoritos={setFavoritos} 
            favoritos={favoritos} 
            showFavoritos={true}
          />
          {showPerfil && <Perfil />}
          {showJogos && <Jogos showFavoritos={props.isLogin} setFavoritos={setFavoritos} favoritos={favoritos} setAlignment={setAlignment} showBoletim={true} aposta={aposta} setAposta={setAposta} desportoAtivo={desportoAtivo} userId={props.user} login={props.isLogin} handleClick={handleClickOdd} />}
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