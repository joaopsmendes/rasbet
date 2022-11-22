import './App.css';
import Login from './components/Login';
import ResponsiveAppBar from './components/Appbar';
import HistoricoApostas from './components/HistoricoApostas';
import Jogos from './components/Jogos';
import { useEffect } from 'react';
function App() {

  //const [login,setLogin] = useState(false);



  return (
    <div className="App">
      <Jogos/>
      {/*<ResponsiveAppBar/>*
      <Login/>
      <HistoricoApostas/>*/}
    </div>
  );
}

export default App;
