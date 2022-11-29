import Login from '././Login';
import ResponsiveAppBar from './Appbar';
import Jogos from './Jogos';
import { useEffect, useState } from 'react';
import Registo from './Registo';
import AlteracaoOdd from './AlteracaoOdd'
import Pagamento from './Pagamento';
import Historico from './Historico';
import Dialogo from './Dialogo';
import Perfil from './Perfil';
import PublicOffIcon from '@mui/icons-material/PublicOff';

function PageUtilizador() {


  const [login, setLogin] = useState(false);
  const [signUp, setSignUp] = useState(false);
  const [user, setUser] = useState();
  const [desportos, setDesportos] = useState([]);
  const [showJogos, setShowJogos] = useState(true);
  const [showHistorico, setShowHistorico] = useState(false);
  const [showPerfil, setShowPerfil] = useState(false);
  const [isDown, setIsDown] = useState(false);



  useEffect(() => {
    getDesportos();
    if (sessionStorage.getItem('user')) {
      setLogin(true);
      setUser(JSON.parse(sessionStorage.getItem('user')));
    }
  }, [login]);



  const handleSignUp = () => {
    setSignUp(true);
  }

  const handleLogin = (user) => {
    setLogin(true);
    sessionStorage.setItem('user', JSON.stringify(user));
    console.log("SESSION STORAGE");
    setUser(user);
  }

  const handleLogout = () => {
    setLogin(false);
    sessionStorage.removeItem('user');
    setUser(null);
    setShowPerfil(false);
    setShowHistorico(false);
    setShowJogos(true);
  }

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
  return (
    <div className="App">
      {!isDown ?
        <div>
          <ResponsiveAppBar pages={desportos} isLogin={login} login={handleLogin} historico={handleHistorico} perfil={handlePerfil} logout={handleLogout} />
          {showPerfil && <Perfil />}
          {login && <Dialogo form={<Login login={handleLogin} />} title="Login" />}
          {signUp && <Dialogo form={<Registo />} title="Registo" />}
          {showJogos && <Jogos userId={user} />}
          {showHistorico && <Historico nome={user} />}
        </div>
        :
        <div>
        <PublicOffIcon sx={{ fontSize:'200px'}}/>
        <h1>Sorry, the app is down</h1>
        </div>


}
    </div>
  );
}

export default PageUtilizador;