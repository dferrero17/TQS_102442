import React, { useEffect, useState } from 'react';

export function Stats({ refresh }) {
  const [stats, setStats] = useState({});

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const response = await fetch('http://localhost:8080/stats');
        const data = await response.json();
        setStats(data);
      } catch (error) {
        console.error('Error fetching stats:', error);
      }
    };

    fetchStats();
  }, [refresh]);

  return (
    <div>
      <h2>Stats</h2>
      <p>Cache Misses: {stats.cacheMisses}</p>
      <p>Cache Hits: {stats.cacheHits}</p>
      <p>API Fails: {stats.apiFails}</p>
      <p>API Hits: {stats.apiHits}</p>
    </div>
  );
};

export default Stats;
