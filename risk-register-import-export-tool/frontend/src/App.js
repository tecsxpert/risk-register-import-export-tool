import React from 'react';
import './App.css';
import RiskForm from './pages/RiskForm';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <div className="container">
          <h1>Risk Register <span className="ai-badge">AI-Powered</span></h1>
        </div>
      </header>
      
      <main className="container main-content">
        <section className="info-banner">
          <p>
            <strong>Project Status:</strong> Week 4 - Demo Prep [May 4, 2026]
          </p>
        </section>

        {/* Integration of the AI-powered Form */}
        <RiskForm />
      </main>

      <footer className="App-footer">
        <p>© 2026 Risk Management Tool | AI Microservice Stable</p>
      </footer>
    </div>
  );
}

export default App;