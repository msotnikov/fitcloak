import './theme.scss'
import { createRoot } from 'react-dom/client'
import { PasswordStrength } from './PasswordStrength.jsx'

const el = document.getElementById('password-strength')
if (el) {
  createRoot(el).render(<PasswordStrength />)
}
