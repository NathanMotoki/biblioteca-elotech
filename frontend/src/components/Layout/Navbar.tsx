import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => {
  return (
    <nav className="bg-indigo-600 text-gray-100 sticky top-0 w-full z-50 border-b border-indigo-500 shadow-lg">
      <div className="px-8 h-16 flex items-center justify-between w-full">
        <div className="text-xl font-semibold text-white">Biblioteca</div>
        <ul className="flex items-center space-x-8">
          <li>
            <Link 
              to="/" 
              className="hover:text-white transition-colors duration-200 font-medium py-2"
            >
              Empréstimos
            </Link>
          </li>
          <li>
            <Link 
              to="/livros" 
              className="hover:text-white transition-colors duration-200 font-medium py-2"
            >
              Livros
            </Link>
          </li>
          <li>
            <Link 
              to="/usuarios" 
              className="hover:text-white transition-colors duration-200 font-medium py-2"
            >
              Usuários
            </Link>
          </li>
        </ul>
      </div>
    </nav>
  );
};

export { Navbar };
