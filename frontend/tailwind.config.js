/** @type {import('tailwindcss').Config} **/
const plugin = require('tailwindcss/plugin');
module.exports = {
  content: ['./src/**/*.{html,js,jsx,ts,tsx}'],
  theme: {
    extend: {
      colors: {
        mint: '#359DB0',
        lightmint: '#8ecfdb',
        extralightmint: '#d5e7eb',
        customRed: '#e37070',
        customGreen: '#6bc164',
        customBlue: '#4d9be3',
        customYellow: '#de9e23',
      },
      animationDelay: {
        '1s': '1s',
        '1.5s': '1.5s',
        '2s': '2s',
      },
      typography: (theme) => ({
        DEFAULT: {
          css: {
            color: theme('colors.gray.800'),
          },
        },
      }),
      borderWidth: {
        10: '10px',
        14: '14px',
        20: '20px',
        24: '24px',
        28: '28px',
        32: '32px',
      },
      keyframes: {
        logo: {
          from: {
            transform: 'translateY(-5rem)',
          },
          to: {
            transform: 'translateY(0)',
          },
          '0%, 14.9%, 30%, 44.9%, 60%, 74.9%': {
            opacity: '0',
          },
          '15%, 29.9%, 45%, 59.9%, 75%': {
            opacity: '1',
          },
        },
        logininput: {
          '0%': {
            transform: 'translateX(0)',
            opacity: 0,
          },
          '0.1%': {
            transform: 'translateX(-100vw)',
            opacity: 1,
          },
          '50%': {
            transform: 'translateX(-100vw)',
          },
          '80%': {
            transform: 'translateX(0)',
          },
        },
        loginbtn: {
          '0%': {
            transform: 'translateY(0)',
            opacity: 0,
          },
          '0.1%': {
            transform: 'translateY(100vh)',
            opacity: 1,
          },
          '60%': {
            transform: 'translateY(100vh)',
          },
          '90%': {
            transform: 'translateY(0)',
          },
        },
        clickbtn: {
          '0%, 100%': { filter: 'brightness(1)' },
          '50%': { filter: 'brightness(1.25)' },
        },
        clickwhitebtn: {
          '0%, 100%': { filter: 'brightness(1)' },
          '50%': { filter: 'brightness(0.8)' },
        },
        earthquake: {
          '0%, 50%, 100%': {
            transform: 'translateY(1vh)',
          },
          '25%, 75%': {
            transform: 'translateY(-1vh)',
          },
        },
        chatBubble: {
          from: {
            opacity: 1,
          },
          to: {
            opacity: 0,
          },
          '0%': {
            display: 'hidden',
          },
        },
      },
      animation: {
        logo: 'logo 2s ease-in-out',
        logininput: 'logininput 2.5s ease-out',
        loginbtn: 'loginbtn 2.5s ease-out',
        clickbtn: 'clickbtn 0.1s linear infinite',
        clickwhitebtn: 'clickwhitebtn 0.1s linear infinite',
        earthquake: 'earthquake 0.1s linear infinite',
        chatBubble: 'chatBubble 1s ease-out',
      },
    },
  },
  plugins: [
    require('daisyui'),
    require('tailwindcss-animation-delay'),
    require('@tailwindcss/typography'),
    plugin(function ({ addUtilities }) {
      addUtilities({
        '.border-custom-red': {
          borderWidth: '0.3rem',
          borderImageSource: "url('../public/ui/border-red.png')",
          borderImageSlice: '14%',
          borderImageOutset: '0.05rem',
          borderImageRepeat: 'stretch',
        },
        '.border-custom-blue': {
          borderWidth: '0.3rem',
          borderImageSource: "url('../public/ui/border-blue.png')",
          borderImageSlice: '14%',
          borderImageOutset: '0.05rem',
          borderImageRepeat: 'stretch',
        },
        '.border-custom-yellow': {
          borderWidth: '0.3rem',
          borderImageSource: "url('../public/ui/border-yellow.png')",
          borderImageSlice: '14%',
          borderImageOutset: '0.05rem',
          borderImageRepeat: 'stretch',
        },
        '.border-custom-green': {
          borderWidth: '0.3rem',
          borderImageSource: "url('../public/ui/border-green.png')",
          borderImageSlice: '14%',
          borderImageOutset: '0.05rem',
          borderImageRepeat: 'stretch',
        },
        '.border-custom-red-select': {
          borderWidth: '0.3rem',
          borderImageSource: "url('../public/ui/border-red-btn.png')",
          borderImageSlice: '14%',
          borderImageOutset: '0.05rem',
          borderImageRepeat: 'stretch',
        },
        '.border-custom-blue-select': {
          borderWidth: '0.3rem',
          borderImageSource: "url('../public/ui/border-blue-btn.png')",
          borderImageSlice: '14%',
          borderImageOutset: '0.05rem',
          borderImageRepeat: 'stretch',
        },
        '.border-custom-green-select': {
          borderWidth: '0.3rem',
          borderImageSource: "url('../public/ui/border-green-btn.png')",
          borderImageSlice: '14%',
          borderImageOutset: '0.05rem',
          borderImageRepeat: 'stretch',
        },
        '.border-custom-white': {
          borderWidth: '0.3rem',
          borderImageSource: "url('../public/ui/border-white.png')",
          borderImageSlice: '14%',
          borderImageOutset: '0.05rem',
          borderImageRepeat: 'stretch',
        },
        '.border-custom-mint': {
          borderWidth: '0.3rem',
          borderImageSource: "url('../public/ui/border-mint.png')",
          borderImageSlice: '14%',
          borderImageOutset: '0.05rem',
          borderImageRepeat: 'stretch',
        },
        '.border-custom-gray': {
          borderWidth: '0.3rem',
          borderImageSource: "url('../public/ui/border-gray.png')",
          borderImageSlice: '14%',
          borderImageOutset: '0.05rem',
          borderImageRepeat: 'stretch',
        },
        '.btn-mint': {
          borderWidth: '0.3rem',
          borderImageSource: "url('../public/ui/border-mint.png')",
          borderImageSlice: '14%',
          borderImageOutset: '0.25rem',
          borderImageRepeat: 'stretch',
          backgroundColor: '#359DB0',
          color: 'white',
          fontWeight: '800',
        },
        '.btn-mint-border-white': {
          borderWidth: '0.3rem',
          borderImageSource: "url('../public/ui/border-mint-btn.png')",
          borderImageSlice: '14%',
          borderImageOutset: '0.25rem',
          borderImageRepeat: 'stretch',
          backgroundColor: '#359DB0',
          color: 'white',
          fontWeight: '800',
        },
        '.btn-red': {
          borderWidth: '0.3rem',
          borderImageSource: "url('../public/ui/border-red-btn.png')",
          borderImageSlice: '14%',
          borderImageOutset: '0.25rem',
          borderImageRepeat: 'stretch',
          backgroundColor: '#e37070',
          color: 'white',
          fontWeight: '800',
        },
        '.btn-white': {
          borderWidth: '0.3rem',
          borderImageSource: "url('../public/ui/border-white.png')",
          borderImageSlice: '14%',
          borderImageOutset: '0.25rem',
          borderImageRepeat: 'stretch',
          backgroundColor: 'white',
          color: '#359DB0',
          fontWeight: '800',
        },
        '.btn-white-bdmint': {
          borderWidth: '0.3rem',
          borderImageSource: "url('../public/ui/border-white-btn.png')",
          borderImageSlice: '14%',
          borderImageOutset: '0.25rem',
          borderImageRepeat: 'stretch',
          backgroundColor: 'white',
          color: '#359DB0',
          fontWeight: '800',
        },
      });
    }),
  ],
};
