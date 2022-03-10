import { useState, useCallback } from 'react';

const useHttp = () => {

    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    const sendRequest = useCallback(async (config) => {
        setIsLoading(true);
        setError(null);
        try {
            const response = await fetch(
                config.url, {
                method: config.method ? config.method : 'GET',
                headers: config.headers ? config.headers : {},
                body:  config.body ? JSON.stringify(config.body) : null
            }
            );

            if (!response.ok) {
                throw new Error('Request failed!');
            }

            const data = await response.json();
            console.log(data)
            setIsLoading(false);
            return data;
        } catch (err) {
            setError(err.message || 'Something went wrong!');
            setIsLoading(false);
        }
    }, []);

    return {
        isLoading,
        error,
        sendRequest
    }
}

export default useHttp;