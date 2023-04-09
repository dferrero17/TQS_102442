import React, { useEffect, useState } from 'react';
import './App.css';
import Stats from './Stats';

const CityInfo = () => {
    const [input, setInput] = useState('');
    const [city, setCity] = useState('');
    const [coordinates, setCoordinates] = useState({ lat: 0, lon: 0 });
    const [airQuality, setAirQuality] = useState({ co: null, no: null, no2: null });
    const [isLoading, setIsLoading] = useState(false);
    const [refresh, setRefresh] = useState(false);


    const fetchCoordinates = async () => {
        try {
            setIsLoading(true);
            const response = await fetch(`http://localhost:8080/api/city/${encodeURIComponent(input)}?location=${encodeURIComponent(input)}`);
            const data = await response.json();
            setCoordinates(data);
            setIsLoading(false);
        } catch (error) {
            console.error('Error fetching coordinates:', error);
            setIsLoading(false);
        }
    };

    useEffect(() => {
        const fetchAirQuality = async () => {
            try {
                if (coordinates.lat !== 0 && coordinates.lon !== 0) {
                    setIsLoading(true);
                    const response = await fetch(`http://localhost:8080/api/airquality/now?lat=${coordinates.lat}&lon=${coordinates.lon}`);
                    const data = await response.json();
                    setCity(input);
                    setAirQuality({ co: data.co, no: data.no, no2: data.no2 });
                    setIsLoading(false);
                }
            } catch (error) {
                console.error('Error fetching air quality data:', error);
                setIsLoading(false);
            }
        };

        fetchAirQuality();
    }, [coordinates]);

    const handleStatsRefresh = () => {
        setRefresh((prevRefresh) => !prevRefresh);
    };


    const handleInputChange = (e) => {
        setInput(e.target.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setCity(''); // Clear the city name
        setCoordinates({ lat: 0, lon: 0 }); // Reset the coordinates
        fetchCoordinates();
    };

    return (
        <div>
            <div className='text-9xl mb-10'>Air Quality Information</div>
            <form onSubmit={handleSubmit}>
                <label htmlFor="cityInput">City: </label>
                <input type="text" id="cityInput" value={input} onChange={handleInputChange} placeholder="Choose a city..." className='input input-bordered input-primary w-full max-w-xs' />
                <button type="submit" className='btn btn-active btn-primary mx-5'>Submit</button>
            </form>
            {isLoading ? (
                <div className="loader">Loading...</div>
            ) : (
                city && (
                    <div className="stats shadow">
                        <div className="stat">
                            <div className="stat-figure text-primary">
                                {/* Add an appropriate icon for CO */}
                            </div>
                            <div className="stat-title">CO</div>
                            <div className="stat-value text-primary">{airQuality.co}</div>
                            {/* Add a description if needed */}
                            <div className="stat-desc"></div>
                        </div>
                        <div className="stat">
                            <div className="stat-figure text-secondary">
                                {/* Add an appropriate icon for NO */}
                            </div>
                            <div className="stat-title">NO</div>
                            <div className="stat-value text-secondary">{airQuality.no}</div>
                            {/* Add a description if needed */}
                            <div className="stat-desc"></div>
                        </div>
                        <div className="stat">
                            <div className="stat-figure text-secondary">
                                {/* Add an appropriate icon for NO2 */}
                            </div>
                            <div className="stat-title">NO2</div>
                            <div className="stat-value">{airQuality.no2}</div>
                            {/* Add a description if needed */}
                            <div className="stat-desc text-secondary"></div>
                        </div>
                        <div className="stat">
                            <div className="stat-figure text-secondary">
                                {/* Add an appropriate icon for the remaining values */}
                            </div>
                            <div className="stat-title">Remaining values</div>
                            <div className="stat-value text-secondary">{/* Add remaining values here */}</div>
                            {/* Add a description if needed */}
                            <div className="stat-desc"></div>
                        </div>
                    </div>

                )
            )}
            <div className="text-5xl my-10"><b>Stats</b></div>
            <div className='flex justify-center'>
                <Stats refresh={refresh} /> {/* Include the Stats component and pass the refresh prop */}
                <button className='btn btn-active btn-secondary mx-5 my-auto' onClick={handleStatsRefresh}>Refresh Stats</button>
            </div>
        </div>

    );

};

export default CityInfo;
