import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

import './tools/coockies';
import { setCookie } from './tools/coockies';

ReactDOM.render(  
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);

