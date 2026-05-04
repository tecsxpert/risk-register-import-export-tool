import logging
from flask import Flask, jsonify
from routes.risk_routes import risk_bp
from flask_limiter import Limiter
from flask_limiter.util import get_remote_address

# --- Step 1: Logging Configuration ---
# Essential for auditing AI calls and debugging during Demo Day
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = Flask(__name__)

# --- Step 2: Security Rate Limiting ---
# Requirement: Prevent API abuse with a 30 req/min limit
limiter = Limiter(
    key_func=get_remote_address,
    app=app,
    default_limits=["30 per minute"],
    storage_uri="memory://" 
)

# --- Step 3: Blueprint Registration ---
# Maps your /describe, /recommend, and /generate-report routes
app.register_blueprint(risk_bp, url_prefix='/ai')

# --- Step 4: Health Check Endpoint ---
# Requirement: Allows Java backend to verify AI service status
@app.route('/health', methods=['GET'])
def health_check():
    logger.info("Health check accessed by monitor")
    return jsonify({
        "status": "healthy",
        "model": "llama-3.3-70b-specdec", # Final model choice for performance
        "port": 5000
    }), 200

# --- Step 5: Error Handling ---
# Ensures a clean JSON error if a user hits the rate limit
@app.errorhandler(429)
def ratelimit_handler(e):
    return jsonify({
        "error": "Rate limit exceeded", 
        "details": "Please wait a moment before trying again."
    }), 429

if __name__ == '__main__':
    # host='0.0.0.0' is mandatory for Docker networking access
    app.run(host='0.0.0.0', port=5000, debug=False)