/** @type {import('tailwindcss').Config} **/
const plugin = require('tailwindcss/plugin');
module.exports = {
  content: ['./src/**/*.{html,js,jsx,ts,tsx}'],
  theme: {
    extend: {
      colors: {
        mint: '#359DB0',
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
      },
      animation: {
        logo: 'logo 2s ease-in-out',
        logininput: 'logininput 2.5s ease-out',
        loginbtn: 'loginbtn 2.5s ease-out',
        clickbtn: 'clickbtn 0.1s linear infinite',
        clickwhitebtn: 'clickwhitebtn 0.1s linear infinite',
      },
    },
  },
  plugins: [
    require('daisyui'),
    require('tailwindcss-animation-delay'),
    require('@tailwindcss/typography'),
    plugin(function ({ addUtilities }) {
      addUtilities({
        '.border-custom-skyblue': {
          border: '24px solid transparent',
          borderImageSource: "url('../public/ui/border-skyblue.png')",
          borderImageSlice: '14%',
          borderImageRepeat: 'stretch',
        },
        '.border-custom-normal': {
          border: '24px solid transparent',
          borderImageSource: "url('../public/ui/border-skyblue.png')",
          borderImageSlice: '14%',
          borderImageRepeat: 'stretch',
        },
        '.border-custom-red': {
          border: '24px solid transparent',
          borderImageSource: "url('../public/ui/border-skyblue.png')",
          borderImageSlice: '14%',
          borderImageRepeat: 'stretch',
        },
        '.border-custom-yellow': {
          border: '24px solid transparent',
          borderImageSource: "url('../public/ui/border-skyblue.png')",
          borderImageSlice: '14%',
          borderImageRepeat: 'stretch',
        },
        '.border-custom-white': {
          borderWidth:'0.3rem',
          borderImageSource: "url('../public/ui/border-white.png')",
          borderImageSlice: '14%',
          borderImageOutset:'0.25rem',
          borderImageRepeat: 'stretch',
        },
        '.border-custom-mint': {
          borderWidth:'0.3rem',
          borderImageSource: "url('../public/ui/border-mint.png')",
          borderImageSlice: '14%',
          borderImageOutset:'0.05rem',
          borderImageRepeat: 'stretch',
        },
        '.btn-mint': {
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
          backgroundColor: '#f87171',
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
