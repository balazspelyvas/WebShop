import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

function ProductList() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch('http://localhost:8080/api/products')
      .then(res => res.json())
      .then(data => {
        setProducts(data.content || []); // Page objektum content mezője
        setLoading(false);
      })
      .catch(err => {
        console.error('Hiba a termékek lekérésekor', err);
        setLoading(false);
      });
  }, []);

  if (loading) {
    return <div>Betöltés...</div>;
  }

  return (
    <div>
      <h2>Terméklista</h2>
      {products.map((product) => (
        <div key={product.id}>
          <Link to={`/products/${product.id}`}>
            <h3>{product.name}</h3>
          </Link>
          <p>Ár: {product.price} Ft</p>
        </div>
      ))}
    </div>
  );
}

export default ProductList;
