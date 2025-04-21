/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: 'rgb(var(--primary))',
          50: 'rgb(var(--primary) / 0.05)',
          100: 'rgb(var(--primary) / 0.1)',
          200: 'rgb(var(--primary) / 0.2)',
          300: 'rgb(var(--primary) / 0.3)',
          400: 'rgb(var(--primary) / 0.4)',
          500: 'rgb(var(--primary) / 0.5)',
          600: 'rgb(var(--primary) / 0.6)',
          700: 'rgb(var(--primary) / 0.7)',
          800: 'rgb(var(--primary) / 0.8)',
          900: 'rgb(var(--primary) / 0.9)',
          950: 'rgb(var(--primary) / 0.95)'
        },
        secondary: {
          DEFAULT: 'rgb(var(--secondary))',
          50: 'rgb(var(--secondary) / 0.05)',
          100: 'rgb(var(--secondary) / 0.1)',
          200: 'rgb(var(--secondary) / 0.2)',
          300: 'rgb(var(--secondary) / 0.3)',
          400: 'rgb(var(--secondary) / 0.4)',
          500: 'rgb(var(--secondary) / 0.5)',
          600: 'rgb(var(--secondary) / 0.6)',
          700: 'rgb(var(--secondary) / 0.7)',
          800: 'rgb(var(--secondary) / 0.8)',
          900: 'rgb(var(--secondary) / 0.9)',
          950: 'rgb(var(--secondary) / 0.95)'
        },
        accent: {
          DEFAULT: 'rgb(var(--accent))',
          50: 'rgb(var(--accent) / 0.05)',
          100: 'rgb(var(--accent) / 0.1)',
          200: 'rgb(var(--accent) / 0.2)',
          300: 'rgb(var(--accent) / 0.3)',
          400: 'rgb(var(--accent) / 0.4)',
          500: 'rgb(var(--accent) / 0.5)',
          600: 'rgb(var(--accent) / 0.6)',
          700: 'rgb(var(--accent) / 0.7)',
          800: 'rgb(var(--accent) / 0.8)',
          900: 'rgb(var(--accent) / 0.9)',
          950: 'rgb(var(--accent) / 0.95)'
        },
        success: {
          DEFAULT: 'rgb(var(--success))',
          50: 'rgb(var(--success) / 0.05)',
          100: 'rgb(var(--success) / 0.1)',
          200: 'rgb(var(--success) / 0.2)',
          300: 'rgb(var(--success) / 0.3)',
          400: 'rgb(var(--success) / 0.4)',
          500: 'rgb(var(--success) / 0.5)',
          600: 'rgb(var(--success) / 0.6)',
          700: 'rgb(var(--success) / 0.7)',
          800: 'rgb(var(--success) / 0.8)',
          900: 'rgb(var(--success) / 0.9)',
          950: 'rgb(var(--success) / 0.95)'
        },
        warning: {
          DEFAULT: 'rgb(var(--warning))',
          50: 'rgb(var(--warning) / 0.05)',
          100: 'rgb(var(--warning) / 0.1)',
          200: 'rgb(var(--warning) / 0.2)',
          300: 'rgb(var(--warning) / 0.3)',
          400: 'rgb(var(--warning) / 0.4)',
          500: 'rgb(var(--warning) / 0.5)',
          600: 'rgb(var(--warning) / 0.6)',
          700: 'rgb(var(--warning) / 0.7)',
          800: 'rgb(var(--warning) / 0.8)',
          900: 'rgb(var(--warning) / 0.9)',
          950: 'rgb(var(--warning) / 0.95)'
        },
        error: {
          DEFAULT: 'rgb(var(--error))',
          50: 'rgb(var(--error) / 0.05)',
          100: 'rgb(var(--error) / 0.1)',
          200: 'rgb(var(--error) / 0.2)',
          300: 'rgb(var(--error) / 0.3)',
          400: 'rgb(var(--error) / 0.4)',
          500: 'rgb(var(--error) / 0.5)',
          600: 'rgb(var(--error) / 0.6)',
          700: 'rgb(var(--error) / 0.7)',
          800: 'rgb(var(--error) / 0.8)',
          900: 'rgb(var(--error) / 0.9)',
          950: 'rgb(var(--error) / 0.95)'
        },
        background: 'rgb(var(--background))',
        foreground: 'rgb(var(--foreground))',
        card: 'rgb(var(--card))',
        'card-foreground': 'rgb(var(--card-foreground))',
        border: 'rgb(var(--border))',
        input: 'rgb(var(--input))',
        ring: 'rgb(var(--ring))'
      },
      borderRadius: {
        lg: 'var(--radius)',
        md: 'calc(var(--radius) - 2px)',
        sm: 'calc(var(--radius) - 4px)',
      },
      animation: {
        'fade-in': 'fadeIn 0.3s ease-out forwards',
        'slide-in': 'slideInUp 0.4s ease-out forwards',
      },
      keyframes: {
        fadeIn: {
          from: { opacity: '0' },
          to: { opacity: '1' },
        },
        slideInUp: {
          from: { transform: 'translateY(10px)', opacity: '0' },
          to: { transform: 'translateY(0)', opacity: '1' },
        },
      },
    },
  },
  plugins: [],
};