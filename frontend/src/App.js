import React from 'react';
import { Routes, Route, Link } from 'react-router-dom';
import Register from './components/Auth/Register';
import Login from './components/Auth/Login';
import ProductList from './components/Products/ProductList';
import ProductDetail from './components/Products/ProductDetail';
import Cart from './components/Cart/Cart';
import Order from './components/Orders/Order';
import './App.css';

function App() {
  return (
    <div className="app-container">
      <nav>
        <Link to="/">Termékek</Link> |{" "}
        <Link to="/cart">Kosár</Link> |{" "}
        <Link to="/login">Bejelentkezés</Link> |{" "}
        <Link to="/register">Regisztráció</Link>
      </nav>

      <Routes>
        <Route path="/" element={<ProductList />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/products/:id" element={<ProductDetail />} />
        <Route path="/order" element={<Order />} />
      </Routes>
    </div>
  );
}

export default App;
