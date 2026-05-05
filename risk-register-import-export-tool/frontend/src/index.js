import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css'; 
import App from './App';

// This connects your React code to the <div id="root"> in index.html
const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
