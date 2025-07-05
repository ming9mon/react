import {Config} from "tailwindcss";
import tailwindcss from 'tailwindcss'
import autoprefixer from 'autoprefixer'

export default {
  // 순서대로 실행될 PostCSS 플러그인 목록
  plugins: [
    tailwindcss(),    // Tailwind 지시문(@tailwind …) 처리
    autoprefixer(),   // 벤더 접두사(-webkit- 등) 자동 추가
  ],
}