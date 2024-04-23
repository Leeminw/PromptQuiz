/** @type {import('tailwindcss').Config} **/
const plugin = require('tailwindcss/plugin');
module.exports = {
  content: ['./src/**/*.{html,js,jsx,ts,tsx}'],
  theme: {
    extend: {
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
    },
  },
  plugins: [
    require('daisyui'),
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
      });
    }),
  ],
};
