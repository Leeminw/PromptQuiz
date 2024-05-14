import React, { useState } from 'react';

const CustomButton = ({
  className,
  children,
  onClick,
  btnCurrentActivate,
  ...props
}: ButtonProps) => {
  const [activateBtn, setActivateBtn] = useState<boolean>(false);
  const handleClick = () => {
    if (!btnCurrentActivate) {
      onClick();
      setActivateBtn(true);
      setTimeout(() => {
        setActivateBtn(false);
      }, 500);
    }
  };
  return (
    <button
      className={`${className} ${activateBtn ? 'animate-clickbtn scale-105' : ''}
      flex items-center justify-center hover:brightness-110 hover:scale-105 transition select-none
      `}
      onClick={handleClick}
      {...props}
    >
      {children}
    </button>
  );
};

export default CustomButton;
