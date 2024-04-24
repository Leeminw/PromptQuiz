import React from 'react';
import { Route, Router, Routes } from 'react-router-dom';
import Login from './pages/Login';
import Join from './pages/Join';
const App = () => {
  return (
    <Routes>
      <Route path="/login" element={<Login />}></Route>
      <Route path="/home" element={<div>홈화면</div>}></Route>
      <Route path="/join" element={<Join />}></Route>
    </Routes>
  );
};

export default App;
