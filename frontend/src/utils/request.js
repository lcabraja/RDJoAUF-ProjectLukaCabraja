import isAuthenticated from "./isAuthenticated";

const request = async (url, method, body, callback) => {
    const r = 'http://localhost:8080/api/v1';
    try {
        const token = localStorage.getItem('token'); // Replace with your actual token key
        const response = await axios.get(url[0] == "/" ? r + url : r + "/" + url, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });

        callback && callback(response.data);
        return response.data;
    } catch (error) {
        !isAuthenticated() && localStorage.removeItem('token');
        console.error('Error fetching portfolios:', error);
    }
};