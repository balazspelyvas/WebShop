import React, { useEffect, useState } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';

function ProductDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [product, setProduct] = useState(null);

  useEffect(() => {
    fetch(`http://localhost:8080/api/products/${id}`)
      .then(res => res.json())
      .then(data => setProduct(data))
      .catch(err => console.error('Hiba:', err));
  }, [id]);

  const handleAddToCart = () => {
    // Példa: localStorage-ben tároljuk a kosarat,
    // valós alkalmazásban lehet Redux/Context, esetleg session-based
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    cart.push({ productId: product.id, quantity: 1, unitPrice: product.price });
    localStorage.setItem('cart', JSON.stringify(cart));

    alert(`${product.name} hozzáadva a kosárhoz!`);
    navigate('/cart');
  };

  if (!product) {
    return <div>Betöltés...</div>;
  }

  return (
    <div>
      <h2>{product.name}</h2>
      <p>Ár: {product.price} Ft</p>
      <p>{product.description}</p>
      <button onClick={handleAddToCart}>Kosárba</button>
      <br />
      <Link to="/">Vissza a termékekhez</Link>
    </div>
  );
}

export default ProductDetail;
