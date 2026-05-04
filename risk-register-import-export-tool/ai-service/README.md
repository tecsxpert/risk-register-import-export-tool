# AI Risk Microservice (ai-service)

# Overview
This microservice provides automated risk analysis and reporting capabilities for the Risk Register Tool. It utilizes the LLaMA-3.3-70b-versatile model via the Groq API to deliver high-performance, low-latency AI completions.

# Core Features
Automated Risk Description: Generates 2-3 sentence technical descriptions based on risk titles.

Mitigation Recommendations: Provides exactly 3 prioritized actions for any given risk.

Executive Report Generation: Synthesizes title, description, and recommendations into a formatted audit report.

Resilience Engineering: Built-in fallback mechanism to handle API outages without crashing the main application.

# Tech Stack
Language: Python 3.11
Framework: FlaskAI 
Engine: Groq SDK (LLaMA-3.3-70b)
Security: Flask-Limiter ($30~req/min$), Python-Dotenv
Deployment: Docker & Docker Compose

# API Reference
1. POST /ai/describe
Generates a professional risk description.

Payload: {"title": "string"}

Response: {"description": "string", "generated_at": "timestamp", "is_fallback": boolean}

2. POST /ai/recommend
Provides exactly 3 mitigation strategies.

Payload: {"description": "string"}

Response: [{"action_type": "string", "description": "string", "priority": "string"}]

3. POST /ai/generate-report
Synthesizes all risk data into a single executive summary.

Payload: {"title": "string", "description": "string", "recommendations": "string"}

Response: {"report_text": "string"}

# Setup & Installation
Local Setup
Environment Variables: Create a .env file in the root directory:

Plaintext
GROQ_API_KEY=your_actual_api_key_here
Install Dependencies:

Bash
pip install -r requirements.txt
Run Service:

Bash
python app.py
Docker Deployment
The service is containerized for consistent deployment:

Bash
docker build -t ai-service .
docker run -p 5000:5000 --env-file .env ai-service

# Security & Reliability
Timeout: All upstream calls are governed by a 10-second timeout in the Java AiServiceClient.

Rate Limiting: Protected by a 30 requests-per-minute threshold to prevent API abuse.

Error Handling: If the Groq API returns an error, the service returns a is_fallback: true flag rather than a 500 Internal Server Error.

