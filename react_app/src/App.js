import './App.css';
import Login from './components/Login';
import ResponsiveAppBar from './components/Appbar';
import HistoricoApostas from './components/HistoricoApostas';
import Jogos from './components/Jogos';
import { useEffect,useState} from 'react';
import Registo from './components/Registo';
import Button from '@mui/material/Button';
import Desportos from './components/Desportos';

function App() {

  const [login,setLogin] = useState(false);
  const [showLogin,setShowLogin] = useState(false);
  const [user,setUser] = useState();

  const[desportos,setDesportos] = useState([]);


  useEffect(()=>{
    getDesportos();
  },[]);


  const handleShowLogin = () => {
    setShowLogin(true);
  }

  const handleLogin = (user) => {
    setLogin(true);
    setUser(user);
    setShowLogin(false);
  }
  

  const getDesportos=async()=>{
    const response = await fetch('http://localhost:8080/desportos',{
        method: 'GET',
    });
    
    const data = await response.json();
    setDesportos(data);

}


  const loginButton = () => {
    return(
        <Button
        onClick={handleShowLogin}
        variant="contained"
        color={"primary"}
        >
         Loggin
       </Button>
       );
  }



  return (
    <div className="App">
      <Desportos/>
      <Registo/>
      {
      showLogin ? 
      <Login changeState={handleLogin} /> 
      :  
      <div>
        {login ? <p>Bem vindo, {user}</p>: loginButton()}
        <Jogos/>
        <HistoricoApostas nome={user}/>
      </div>
    }
    </div>
  );
}

export default App;
