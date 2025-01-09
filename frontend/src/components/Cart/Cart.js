import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

function Cart() {
  const [cartItems, setCartItems] = useState([]);

  useEffect(() => {
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    setCartItems(cart);
  }, []);

  const removeFromCart = (index) => {
    let newCart = [...cartItems];
    newCart.splice(index, 1);
    setCartItems(newCart);
    localStorage.setItem('cart', JSON.stringify(newCart));
  };

  const clearCart = () => {
    setCartItems([]);
    localStorage.removeItem('cart');
  };

  const totalPrice = cartItems.reduce((acc, item) => acc + (item.quantity * item.unitPrice), 0);

  return (
    <div>
      <h2>Kosár</h2>
      {cartItems.length === 0 ? (
        <p>A kosár üres.</p>
      ) : (
        <div>
          {cartItems.map((item, index) => (
            <div key={index}>
              <p>TermékID: {item.productId}, Mennyiség: {item.quantity}, Egységár: {item.unitPrice} Ft</p>
              <button onClick={() => removeFromCart(index)}>Törlés</button>
            </div>
          ))}
          <h3>Végösszeg: {totalPrice} Ft</h3>
          <Link to="/order">Rendelés leadása</Link>
        </div>
      )}
      <button onClick={clearCart}>Kosár ürítése</button>
    </div>
  );
}

export default Cart;
