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

import { createTheme } from '@mui/material/styles';




function App() {
  

  const [login,setLogin] = useState(false);
  const [signUp,setSignUp] = useState(false);
  const [user,setUser] = useState();
  const [desportos,setDesportos] = useState([]);


  useEffect(()=>{
    getDesportos();
  },[]);



  const handleSignUp = () => {
    setSignUp(true);
  }

  const handleLogin = (user) => {
    setLogin(true);
    //localStorage.setItem("user",user);
    setUser(user);
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
      <ResponsiveAppBar pages={desportos} login={login} changeState={handleLogin}/>
      <div> 
        {login ? <p>Bem vindo, {user}</p> : <Dialogo  form={<Login changeState={handleLogin}/>} title="Login"/>}
        {signUp && <Dialogo form={<Registo/>} title="Registo"/>}
        <Jogos/>
        
        <HistoricoApostas nome={user}/>
      </div>
    </div>
  );
}

export default App;
