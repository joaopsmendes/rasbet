import './App.css';
import Login from './components/Login';
import ResponsiveAppBar from './components/Appbar';
function App() {
  return (
    <div className="App">
      <ResponsiveAppBar/>
      <Login/>
    </div>
  );
}

export default App;
