import React from 'react';
import { Route, Router, Routes } from 'react-router-dom';
import Login from './pages/Login';
const App = () => {
  return (
    <Routes>
      <Route path='/login' element={<Login/>}></Route>
      <Route path='/home' element={<div>홈화면</div>}> </Route>
    </Routes>
  );
};

export default App;
