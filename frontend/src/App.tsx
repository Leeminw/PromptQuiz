import React from 'react';

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ChannelSelect from './pages/ChannelSelect';
const App = () => {
  return (
    <div
      style={{
        backgroundImage: 'url(/ui/bg.jpg)',
        width: '100vw',
        height: '100vh',
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
      }}
    >
      <Router>
        <Routes>
          <Route path="/channel-select" element={<ChannelSelect />}></Route>
        </Routes>
      </Router>
    </div>
  );
};

export default App;
