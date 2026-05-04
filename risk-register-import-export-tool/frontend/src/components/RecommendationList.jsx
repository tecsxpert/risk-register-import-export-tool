import React from 'react';

const RecommendationList = ({ recommendations }) => {
  const getPriorityColor = (priority) => {
    switch (priority.toLowerCase()) {
      case 'high': return 'border-red-500 bg-red-50';
      case 'medium': return 'border-yellow-500 bg-yellow-50';
      default: return 'border-green-500 bg-green-50';
    }
  };

  return (
    <div className="grid gap-4">
      {recommendations.map((rec, index) => (
        <div key={index} className={`p-4 border-l-4 rounded shadow-sm ${getPriorityColor(rec.priority)}`}>
          <div className="flex justify-between items-center mb-2">
            <span className="font-bold text-sm uppercase">{rec.action_type}</span>
            <span className="text-xs px-2 py-1 bg-white rounded border">{rec.priority}</span>
          </div>
          <p className="text-gray-700 text-sm">{rec.description}</p>
        </div>
      ))}
    </div>
  );
};

export default RecommendationList;