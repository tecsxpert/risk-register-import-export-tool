import React from 'react';

const AiSkeleton = ({ type = 'text' }) => {
  // Skeleton for the 3 Recommendation Cards
  if (type === 'cards') {
    return (
      <div className="animate-pulse space-y-4">
        {[1, 2, 3].map((item) => (
          <div key={item} className="p-4 border-l-4 border-gray-300 bg-gray-50 rounded shadow-sm">
            <div className="flex justify-between items-center mb-2">
              <div className="h-4 bg-gray-300 rounded w-1/4"></div>
              <div className="h-4 bg-gray-300 rounded w-16"></div>
            </div>
            <div className="space-y-2">
              <div className="h-3 bg-gray-200 rounded w-full"></div>
              <div className="h-3 bg-gray-200 rounded w-5/6"></div>
            </div>
          </div>
        ))}
      </div>
    );
  }

  // Default Skeleton for the Description Text Area
  return (
    <div className="animate-pulse space-y-3 p-3 border rounded shadow-sm bg-gray-50">
      <div className="h-3 bg-gray-300 rounded w-3/4"></div>
      <div className="h-3 bg-gray-200 rounded w-full"></div>
      <div className="h-3 bg-gray-200 rounded w-5/6"></div>
      <div className="h-3 bg-gray-200 rounded w-1/2"></div>
    </div>
  );
};

export default AiSkeleton;