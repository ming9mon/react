import { FlatCompat } from "@eslint/eslintrc";

const compat = new FlatCompat({
  baseDirectory: import.meta.url && new URL(".", import.meta.url).pathname
})

const eslintConfig = [
  ...compat.extends(
      "next/core-web-vitals",
      "next/typescript",
      "plugin:prettier/recommended",
      "plugin:tailwindcss/recommended" // Tailwind CSS 규칙 (ESLint Tailwind 플러그인)
  ),
];

export default eslintConfig;
