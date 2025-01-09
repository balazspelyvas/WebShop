import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function Order() {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [paymentMethod, setPaymentMethod] = useState('CREDIT_CARD');
  const [shippingMethod, setShippingMethod] = useState('GLS');
  const [cartItems, setCartItems] = useState([]);

  // Betöltjük a kosár elemeket
  useEffect(() => {
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    setCartItems(cart);
  }, []);

  const handleOrder = async () => {
    if (cartItems.length === 0) {
      alert('Üres kosárból nem lehet rendelni!');
      return;
    }
    try {
      const response = await fetch('http://localhost:8080/api/orders', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          username,
          paymentMethod,
          shippingMethod,
          items: cartItems
        })
      });
      if (response.ok) {
        const data = await response.json();
        alert('Sikeres rendelés, rendelés azonosító: ' + data.id);
        // Kosár törlése
        localStorage.removeItem('cart');
        navigate('/');
      } else {
        alert('Hiba a rendelés létrehozásakor. Lehet, hogy a felhasználó nem létezik, vagy COD tiltva van.');
      }
    } catch (err) {
      console.error(err);
      alert('Szerver vagy hálózati hiba.');
    }
  };

  return (
    <div>
      <h2>Rendelés leadása</h2>
      <div>
        <label>Felhasználónév (akivel regisztráltál): </label>
        <input type="text" value={username} onChange={e => setUsername(e.target.value)} />
      </div>
      <div>
        <label>Fizetési mód: </label>
        <select value={paymentMethod} onChange={e => setPaymentMethod(e.target.value)}>
          <option value="COD">Utánvét</option>
          <option value="BANK_TRANSFER">Banki átutalás</option>
          <option value="CREDIT_CARD">Bankkártya</option>
          <option value="PAYPAL">PayPal</option>
        </select>
      </div>
      <div>
        <label>Szállítási mód: </label>
        <select value={shippingMethod} onChange={e => setShippingMethod(e.target.value)}>
          <option value="GLS">GLS</option>
          <option value="MPL">MPL</option>
          <option value="FOXPOST">Foxpost</option>
          <option value="PACKETA">Packeta</option>
          <option value="SAMEDAY">Sameday</option>
        </select>
      </div>
      <button onClick={handleOrder}>Rendelés leadása</button>
    </div>
  );
}

export default Order;
