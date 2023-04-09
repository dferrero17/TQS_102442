import React, { useEffect, useState } from 'react';

const Stats = ({ refresh }) => {
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
        <div className="stats shadow">
            <div className="stat">
                <div className="stat-figure text-primary">
                    {/* Add an appropriate icon here */}
                </div>
                <div className="stat-title">Cache Misses</div>
                <div className="stat-value text-primary">{stats.cacheMisses}</div>
                {/* Add a description if needed */}
                <div className="stat-desc"></div>
            </div>
            <div className="stat">
                <div className="stat-figure text-secondary">
                    {/* Add an appropriate icon here */}
                </div>
                <div className="stat-title">Cache Hits</div>
                <div className="stat-value text-secondary">{stats.cacheHits}</div>
                {/* Add a description if needed */}
                <div className="stat-desc"></div>
            </div>

            <div className="stat">
                <div className="stat-figure text-secondary">
                    {/* Add an appropriate icon here */}
                </div>
                <div className="stat-title">API Fails</div>
                <div className="stat-value">{stats.apiFails}</div>
                {/* Add a description if needed */}
                <div className="stat-desc text-secondary"></div>
            </div>

            <div className="stat">
                <div className="stat-figure text-secondary">
                    {/* Add an appropriate icon here */}
                </div>
                <div className="stat-title">API Hits</div>
                <div className="stat-value text-secondary">{stats.apiHits}</div>
                {/* Add a description if needed */}
                <div className="stat-desc"></div>
            </div>
        </div>
    );
};

export default Stats;
