import React from 'react';
import './App.css';
import RiskForm from './pages/RiskForm';

/**
 * Main Application Component for the Risk Register Tool.
 * Organized for the Week 4 Demo Prep.
 */
function App() {
  return (
    <div className="App">
      {/* Header with AI Branding for Demo Day */}
      <header className="App-header">
        <div className="container">
          <h1 className="flex items-center gap-2">
            Risk Management System 
            <span className="ai-badge">AI-Enabled</span>
          </h1>
        </div>
      </header>
      
      <main className="container main-content">
        {/* Project Context Banner */}
        <section className="info-banner">
          <p>
            <strong>Status:</strong> Week 4 Presentation Preparation | <strong>Date:</strong> May 4, 2026
          </p>
        </section>

        {/* The core AI-driven functional component */}
        <div className="card-container">
          <RiskForm />
        </div>
      </main>

      <footer className="App-footer">
        <p>© 2026 Bangalore Institute of Technology | MCA Technical Project</p>
      </footer>
    </div>
  );
}

export default App;