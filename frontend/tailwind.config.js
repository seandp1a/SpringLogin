const mode = process.env.TAILWIND_MODE ? 'jit' : 'aot';
module.exports = {
  mode:'jit',
  purge: {
    enabled: true,
    content: ["./src/**/*.html", "./src/**/*.scss"],
  },
  darkMode: 'class', // or 'media' or 'class' or false
  theme: {
    extend: {},
    screen:{
      'tablet': '640px',
      // => @media (min-width: 640px) { ... }

      'laptop': '1024px',
      // => @media (min-width: 1024px) { ... }

      'desktop': '1280px',
      // => @media (min-width: 1280px) { ... }
    }
  },
  variants: {
    extend: {
      // ringWidth:['active'],
    },
    borderStyle: ['responsive','hover'],
  },
  plugins: [
    require('@tailwindcss/forms'),
  ],
};
// npx tailwindcss init --full 可取得完整預設配置檔
// 詳見 https://unpkg.com/browse/tailwindcss@2.2.16/stubs/defaultConfig.stub.js
