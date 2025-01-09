const API_BASE_URL = 'http://localhost:8080/api';

export async function getProducts(page = 0, size = 10) {
  const response = await fetch(`${API_BASE_URL}/products?page=${page}&size=${size}`);
  if (!response.ok) {
    throw new Error('Hiba a termékek lekérése során');
  }
  return await response.json();
}

export async function getProductById(id) {
  const response = await fetch(`${API_BASE_URL}/products/${id}`);
  if (!response.ok) {
    throw new Error('Hiba a termék lekérése során');
  }
  return await response.json();
}

// További segédfüggvények: termék létrehozás, frissítés, törlés, regisztráció, login stb.
