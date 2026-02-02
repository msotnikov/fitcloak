import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  root: '.',
  plugins: [react()],
  build: {
    outDir: 'login/resources/css',
    emptyOutDir: false,
    rollupOptions: {
      input: 'src/main.jsx',
      output: {
        assetFileNames: 'demo.[ext]'
      }
    }
  }
})
