import React, { useState } from 'react';
import { aiService } from '../services/aiService';
import RecommendationList from '../components/RecommendationList';

const RiskForm = () => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [recommendations, setRecommendations] = useState([]);
  const [loading, setLoading] = useState({ desc: false, rec: false });
  const [isFallback, setIsFallback] = useState(false);

  // Task: Generate Description using AI
  const handleGenerateDescription = async () => {
    if (!title) return alert("Please enter a risk title first.");
    
    setLoading({ ...loading, desc: true });
    const data = await aiService.getDescription(title);
    
    setDescription(data.description);
    setIsFallback(data.is_fallback); // Identify if Groq API is offline
    setLoading({ ...loading, desc: false });
  };

  // Task: Fetch 3 Prioritized Recommendations
  const handleGetRecommendations = async () => {
    if (!description) return alert("Generate or enter a description first.");
    
    setLoading({ ...loading, rec: true });
    const data = await aiService.getRecommendations(description);
    
    setRecommendations(data);
    setLoading({ ...loading, rec: false });
  };

  return (
    <div className="max-w-4xl mx-auto p-6 bg-white shadow-lg rounded-lg">
      <h2 className="text-2xl font-bold mb-6 text-gray-800">Create New Risk Record</h2>

      {/* Risk Title Input */}
      <div className="mb-4">
        <label className="block text-sm font-medium text-gray-700">Risk Title</label>
        <div className="flex gap-2 mt-1">
          <input
            type="text"
            className="flex-1 p-2 border rounded shadow-sm"
            placeholder="e.g., Database Connection Timeout"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
          <button
            onClick={handleGenerateDescription}
            disabled={loading.desc}
            className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 disabled:bg-blue-300"
          >
            {loading.desc ? 'AI Thinking...' : 'Generate AI Description'}
          </button>
        </div>
      </div>

      {/* Description Area */}
      <div className="mb-4">
        <label className="block text-sm font-medium text-gray-700">Description</label>
        {isFallback && (
          <span className="text-xs text-orange-600 font-medium">⚠️ Using fallback template</span>
        )}
        <textarea
          rows="4"
          className="w-full mt-1 p-2 border rounded shadow-sm"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          placeholder="AI generated description will appear here..."
        />
      </div>

      {/* Recommendations Section */}
      <div className="mb-6">
        <div className="flex justify-between items-center mb-4">
          <h3 className="text-lg font-semibold text-gray-700">AI Mitigation Strategies</h3>
          <button
            onClick={handleGetRecommendations}
            disabled={loading.rec || !description}
            className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700 disabled:bg-green-300"
          >
            {loading.rec ? 'Analyzing...' : 'AI Recommend'}
          </button>
        </div>

        {loading.rec ? (
          <div className="animate-pulse space-y-4">
            <div className="h-20 bg-gray-200 rounded"></div>
            <div className="h-20 bg-gray-200 rounded"></div>
          </div>
        ) : (
          <RecommendationList recommendations={recommendations} />
        )}
      </div>

      <button className="w-full py-3 bg-gray-800 text-white font-bold rounded hover:bg-black">
        Save Risk Record
      </button>
    </div>
  );
};

export default RiskForm;