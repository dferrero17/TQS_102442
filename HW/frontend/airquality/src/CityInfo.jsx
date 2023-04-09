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
                <input type="text" id="cityInput" value={input} onChange={handleInputChange} />
                <button type="submit">Submit</button>
            </form>
            {isLoading ? (
                <div className="loader">Loading...</div>
            ) : (
                city && (
                    <div>
                        <div className='text-5xl my-10'><b>City:</b> {city}</div>
                        <p>CO: {airQuality.co}</p>
                        <p>NO: {airQuality.no}</p>
                        <p>NO2: {airQuality.no2}</p>
                    </div>
                )
            )}
            <div className="text-5xl my-10">Stats</div>
            <button onClick={handleStatsRefresh}>Refresh Stats</button>
            <Stats refresh={refresh} /> {/* Include the Stats component and pass the refresh prop */}
        </div>

    );

};

export default CityInfo;
