import './App.css';
import Login from './components/Login';
import ResponsiveAppBar from './components/Appbar';
import HistoricoApostas from './components/HistoricoApostas';
import Jogos from './components/Jogos';
import { useEffect,useState} from 'react';
import Registo from './components/Registo';
import Button from '@mui/material/Button';
import Desportos from './components/Desportos';
import Dialogo from './components/Dialogo';
import AlteracaoOdd from './components/AlteracaoOdd'

import { createTheme } from '@mui/material/styles';
import Pagamento from './components/Pagamento';
import AlterarInformacaoUser from './components/AlterarInformacaoUser';



function App() {
  

  const [login,setLogin] = useState(false);
  const [signUp,setSignUp] = useState(false);
  const [user,setUser] = useState();
  const [desportos,setDesportos] = useState([]);
  const [showJogos,setShowJogos] = useState(true);
  const [showHistorico,setShowHistorico] = useState(false);
  const [showPerfil,setShowPerfil] = useState(false);



  useEffect(()=>{
    getDesportos();
    if (sessionStorage.getItem('user')){
      setLogin(true);
      setUser(JSON.parse(sessionStorage.getItem('user')));
    }
  },[]);



  const handleSignUp = () => {
    setSignUp(true);
  }

  const handleLogin = (user) => {
    setLogin(true);
    sessionStorage.setItem('user',JSON.stringify(user));
    setUser(user);
  }

  const handleLogout = () => {
    setLogin(false);
    sessionStorage.removeItem('user');
    setUser(null);
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


  const getDesportos=async()=>{
    const response = await fetch('http://localhost:8080/desportos',{
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
      <ResponsiveAppBar pages={desportos} isLogin={login} login={handleLogin} historico={handleHistorico} perfil={handlePerfil} logout={handleLogout}/>
      <div> 
        {showPerfil && <AlterarInformacaoUser/>}
        {login ? <p>Bem vindo, {user}</p> : <Dialogo  form={<Login changeState={handleLogin}/>} title="Login"/>}
        {signUp && <Dialogo form={<Registo/>} title="Registo"/>}
        {showJogos && <Jogos userId={user}/>}
        {showHistorico && <HistoricoApostas nome={user}/>}
        <Pagamento/>
        <AlteracaoOdd/>
      </div>
    </div>
  );
}

export default App;
