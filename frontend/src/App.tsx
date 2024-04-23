import React from 'react';
import TestScreen from './pages/test/test';
import { Route, Router, Routes } from 'react-router-dom';
import Login from './pages/Login';
const App = () => {
  return (
    <Routes>
      <Route path='/login' element={<Login/>}></Route>
    </Routes>
  );
};

export default App;
