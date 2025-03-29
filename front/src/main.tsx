import './index.css'
import App from './App.tsx'
import {BrowserRouter} from "react-router-dom";
import ReactDOM from 'react-dom/client';
import React from 'react';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

root.render(
    <React.StrictMode>
        <BrowserRouter>
            <App />
        </BrowserRouter>
    </React.StrictMode>
);