import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.jsx'

// Procura a div com id="root" lá no index.html e injeta o componente App nela
createRoot(document.getElementById('root')).render(
    <StrictMode>
        <App />
    </StrictMode>,
)