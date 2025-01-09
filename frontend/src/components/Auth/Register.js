import React, { useState } from 'react';

function Register() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState('');
  const [billingAddress, setBillingAddress] = useState('');
  const [shippingAddress, setShippingAddress] = useState('');

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          username,
          password,
          email,
          billingAddress,
          shippingAddress
        })
      });
      if (response.ok) {
        alert('Sikeres regisztráció!');
        // További lépések: pl. átirányítás a login oldalra
      } else {
        alert('Hiba történt a regisztráció során.');
      }
    } catch (err) {
      console.error(err);
      alert('Hálózati hiba vagy szerver oldali hiba történt.');
    }
  };

  return (
    <div>
      <h2>Regisztráció</h2>
      <form onSubmit={handleRegister}>
        <div>
          <label>Felhasználónév:</label>
          <input 
            type="text" 
            value={username} 
            onChange={e => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Jelszó:</label>
          <input 
            type="password" 
            value={password}
            onChange={e => setPassword(e.target.value)}
            required
          />
        </div>
        <div>
          <label>E-mail:</label>
          <input 
            type="email" 
            value={email}
            onChange={e => setEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Számlázási cím:</label>
          <input 
            type="text" 
            value={billingAddress}
            onChange={e => setBillingAddress(e.target.value)}
          />
        </div>
        <div>
          <label>Szállítási cím:</label>
          <input 
            type="text" 
            value={shippingAddress}
            onChange={e => setShippingAddress(e.target.value)}
          />
        </div>
        <button type="submit">Regisztráció</button>
      </form>
    </div>
  );
}

export default Register;
