import './App.css';
import Login from './components/Login';
import ResponsiveAppBar from './components/Appbar';
import HistoricoApostas from './components/HistoricoApostas';
import Jogos from './components/Jogos';
import { useEffect,useState} from 'react';
import Registo from './components/Registo';
function App() {

  const [login,setLogin] = useState(false);
  const [showLogin,setShowLogin] = useState(false);
  const [user,setUser] = useState();


  useEffect(()=>{
  },[]);


  const handleShowLogin = () => {
    setShowLogin(true);
  }

  const handleLogin = (user) => {
    setLogin(true);
    setUser(user);
    setShowLogin(false);
  }
  


  return (
    <div className="App">
      {login ? <p>Bem vindo, {user}</p>: <button onClick={handleShowLogin}>Login</button>}
      {
      showLogin ? 
      <Login changeState={handleLogin} /> 
      :  
      <div>
        <Jogos/>
        <HistoricoApostas nome={user}/>
      </div>
    }
    </div>
  );
}

export default App;
