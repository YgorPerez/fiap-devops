/** @type {import("prettier").Config} */
const config = {
  trailingComma: "es5",
  tabWidth: 2,
  useTabs: false,
  semi: true,
  singleQuote: true,
  experimentalTernaries: true,
  bracketSameLine: true,
  printWidth: 80,
  jsxSingleQuote: true,
  arrowParens: "avoid",
  plugins: ["prettier-plugin-tailwindcss"],
};

export default config;
