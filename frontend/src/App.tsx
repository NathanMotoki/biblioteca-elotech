import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import EmprestimosPage from './pages/EmprestimosPage';
import LivrosPage from './pages/LivrosPage';
import UsuariosPage from './pages/UsuariosPage';
import { Navbar } from './components/Layout/Navbar';


const App = () => {
  return (
    <Router>
      <Navbar />
      <main className="w-full">
        <Routes>
          <Route path="/" element={<EmprestimosPage />} />
          <Route path="/livros" element={<LivrosPage />} />
          <Route path="/usuarios" element={<UsuariosPage />} />
        </Routes>
      </main>
    </Router>
  );
};

export default App;