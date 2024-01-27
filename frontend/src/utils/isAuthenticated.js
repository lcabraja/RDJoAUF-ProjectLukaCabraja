// import axios from 'axios';

// const isAuthenticated = async () => {
//   const token = localStorage.getItem('token');
//   if (!token) return false;

//   try {
//     const response = await axios.get('http://localhost:8080/api/v1/auth/valid', {
//       headers: {
//         Authorization: `Bearer ${token}`
//       }
//     })
//     // if 4xx reutrn false
//     if (response.status / 100 === 4) return false;
//     return true;
//   } catch (error) {
//     return false;
//   }
// };

const isAuthenticated = () => {
  return !!localStorage.getItem('token');
}

export default isAuthenticated;
