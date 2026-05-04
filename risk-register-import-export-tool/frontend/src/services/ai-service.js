const AI_BASE_URL = process.env.REACT_APP_AI_SERVICE_URL || 'http://localhost:5000/ai';

export const aiService = {
  /**
   * Fetches a 2-3 sentence risk description based on the title.
   */
  async getDescription(title) {
    try {
      const response = await fetch(`${AI_BASE_URL}/describe`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ title }),
      });

      if (response.status === 429) throw new Error("Rate limit exceeded. Please wait.");
      return await response.json();
    } catch (error) {
      console.error("AI Service Error:", error);
      return { description: "Technical description unavailable.", is_fallback: true };
    }
  },

  /**
   * Fetches 3 prioritized mitigation strategies.
   */
  async getRecommendations(description) {
    try {
      const response = await fetch(`${AI_BASE_URL}/recommend`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ description }),
      });
      return await response.json();
    } catch (error) {
      return [{ action_type: "General", description: "Follow standard mitigation protocols.", priority: "Medium" }];
    }
  }
};