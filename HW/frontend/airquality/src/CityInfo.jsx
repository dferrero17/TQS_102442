import React, { useEffect, useState } from 'react';
import './App.css';
import Stats from './Stats';

const CityInfo = () => {
    const [input, setInput] = useState('');
    const [city, setCity] = useState('');
    const [coordinates, setCoordinates] = useState({ lat: 0, lon: 0 });
    const [airQuality, setAirQuality] = useState({
        co: null,
        no: null,
        no2: null,
        o3: null,
        so2: null,
        pm2_5: null,
        pm10: null,
        nh3: null,
    });
    const airQualityKeys = ['co', 'no', 'no2', 'o3', 'so2', 'pm2_5', 'pm10', 'nh3'];

    const [isLoading, setIsLoading] = useState(false);
    const [refresh, setRefresh] = useState(false);
    const [historicalData, setHistoricalData] = useState([]);
    const [useHistory, setUseHistory] = useState(false);

    const toggleUseHistory = () => {
        setUseHistory((prevState) => !prevState);
    };

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
                    setAirQuality({
                        co: data.co,
                        no: data.no,
                        no2: data.no2,
                        o3: data.o3,
                        so2: data.so2,
                        pm2_5: data.pm2_5,
                        pm10: data.pm10,
                        nh3: data.nh3,
                    });

                    setIsLoading(false);
                }
            } catch (error) {
                console.error('Error fetching air quality data:', error);
                setIsLoading(false);
            }
        };

        fetchAirQuality();
    }, [coordinates]);
    useEffect(() => {
        const fetchAirQuality = async () => {
            try {
                if (coordinates.lat !== 0 && coordinates.lon !== 0) {
                    setIsLoading(true);
                    const endpoint = useHistory
                        ? `http://localhost:8080/api/airquality/history?lat=${coordinates.lat}&lon=${coordinates.lon}`
                        : `http://localhost:8080/api/airquality/now?lat=${coordinates.lat}&lon=${coordinates.lon}`;
                    const response = await fetch(endpoint);
                    const data = await response.json();
                    setCity(input);
                    if (useHistory) {
                        setHistoricalData(data.list.slice(0, 7));
                    } else {
                        setAirQuality({
                            co: data.co,
                            no: data.no,
                            no2: data.no2,
                            o3: data.o3,
                            so2: data.so2,
                            pm2_5: data.pm2_5,
                            pm10: data.pm10,
                            nh3: data.nh3,
                        });
                    }
                    setIsLoading(false);
                }
            } catch (error) {
                console.error('Error fetching air quality data:', error);
                setIsLoading(false);
            }
        };

        fetchAirQuality();
    }, [coordinates, useHistory]);

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
            <div className='my-10 text-9xl'>Air Quality Information</div>
            <form onSubmit={handleSubmit}>
                <input type="text" id="cityInput" value={input} onChange={handleInputChange} placeholder="Choose a city..." className='input input-bordered input-primary w-[60rem]' />
                <button type="submit" className='mx-5 btn btn-active btn-primary'>Submit</button>
            </form>
            <div className="relative flex items-center justify-center mt-10 text-5xl text-center">
                {city ? (
                    <b>
                        {city}<sup className="absolute ml-1 text-xs align-top text-primary">(Lat: {coordinates.lat.toFixed(2)}, Long: {coordinates.lon.toFixed(2)})</sup>
                    </b>
                ) : (
                    <div>City</div>
                )}
            </div>

            <div className="toggle-container">
                <label className="cursor-pointer label"></label>
                <div className="my-1 text-xl label-text">See Past 7 Days</div>
                <input
                    type="checkbox"
                    className="mb-1 toggle"
                    checked={useHistory}
                    onChange={toggleUseHistory}
                />
            </div>
            {isLoading ? (
                <div className="loader">Loading...</div>
            ) : (
                city && (
                    useHistory ? (
                        historicalData.map((item, index) => (
                            <div className="shadow stats" key={index}>
                                <div className="stat">
                                    <div className="stat-title text-primary">Days ago</div>
                                    <div className="stat-value text-primary-focus">{index + 1}</div>
                                </div>
                                {airQualityKeys.map((key) => {
                                    const value = item[key];
                                    return (
                                        <div className="stat" key={key}>
                                            <div className="stat-figure text-secondary">
                                            </div>
                                            <div className="w-20 stat-title">{key.toUpperCase()}</div>
                                            <div className="w-20 stat-value">{value}</div>
                                            <div className="w-20 stat-desc">µg/m³</div>
                                        </div>
                                    );
                                })}
                            </div>
                        ))
                    ) : (
                        <div className="shadow stats airq">
                            {Object.entries(airQuality).map(([key, value]) => (
                                <div className="stat" key={key}>
                                    <div className="stat-figure text-secondary">
                                        {/* Add an appropriate icon here */}
                                    </div>
                                    <div className="w-20 stat-title">{key.toUpperCase()}</div>
                                    <div className="w-20 stat-value">{value}</div>
                                    <div className="w-20 stat-desc">µg/m³</div>
                                </div>
                            ))}

                        </div>
                    )
                )
            )}
            <div className="my-10 text-5xl"><b>Stats</b></div>
            <div className='flex justify-center'>
                <Stats refresh={refresh} /> {/* Include the Stats component and pass the refresh prop */}
                <button className='mx-5 my-auto btn btn-active btn-secondary' onClick={handleStatsRefresh}>Refresh Stats</button>
            </div>
        </div>
    );

};

export default CityInfo;
