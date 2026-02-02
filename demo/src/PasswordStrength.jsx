import { useState, useEffect } from 'react'

function calcStrength(password) {
  if (!password) return { score: 0, label: '', color: '' }

  let score = 0
  if (password.length >= 8) score++
  if (password.length >= 12) score++
  if (/[0-9]/.test(password)) score++
  if (/[a-z]/.test(password) && /[A-Z]/.test(password)) score++
  if (/[^a-zA-Z0-9]/.test(password)) score++

  if (score <= 2) return { score, label: 'Weak', color: '#e74c3c' }
  if (score <= 3) return { score, label: 'Medium', color: '#f39c12' }
  return { score, label: 'Strong', color: '#27ae60' }
}

export function PasswordStrength() {
  const [strength, setStrength] = useState({ score: 0, label: '', color: '' })

  useEffect(() => {
    const input = document.getElementById('password')
    if (!input) return

    const handler = () => setStrength(calcStrength(input.value))
    input.addEventListener('input', handler)
    return () => input.removeEventListener('input', handler)
  }, [])

  if (!strength.label) return null

  return (
    <div className="password-strength">
      <div className="password-strength-bar">
        <div
          className="password-strength-fill"
          style={{
            width: `${(strength.score / 5) * 100}%`,
            backgroundColor: strength.color,
          }}
        />
      </div>
      <span className="password-strength-label" style={{ color: strength.color }}>
        {strength.label}
      </span>
    </div>
  )
}
