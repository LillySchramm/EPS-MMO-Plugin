import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import AdminPage from './sites/admin/admin';

function App() {
  return (
    <Router>
      <Route path='/admin' exact component={AdminPage} />      
    </Router>
  );
}

export default App;
