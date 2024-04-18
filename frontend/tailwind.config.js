/** @type {import('tailwindcss').Config} **/
module.exports = {
  content: ['./src/**/*.{html,js,jsx,ts,tsx}'],
  theme: {
    extend: {
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
  plugins: [],
};
