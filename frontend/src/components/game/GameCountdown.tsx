import React, { useEffect, useState } from 'react';
interface CustomCSSProperties extends React.CSSProperties {
  '--value'?: number;
  '--size'?: string;
  '--thickness'?: string;
}
const GameCountdown = ({ sec }: { sec: number }) => {
  const [val, setVal] = useState<number>(100);
  useEffect(() => {
    if (val > 0) {
      const timer = setInterval(() => {
        setVal((prev) => prev - 1);
      }, 8);
      return () => clearInterval(timer);
    }
  }, [val]);

  useEffect(() => {
    setVal(103);
  }, [sec]);
  const progreesProperties: CustomCSSProperties = {
    '--value': val,
    '--size': '10rem',
    '--thickness': '0.5rem',
  };
  return (
    <div className="w-full h-full absolute flex items-center justify-center">
      <div
        className="radial-progress bg-lightmint text-mint border-4 border-white transition-all"
        style={progreesProperties}
        role="progressbar"
      >
        <p className="text-6xl font-extrabold text-white animate-countdown">{sec}</p>
      </div>
    </div>
  );
};

export default GameCountdown;
