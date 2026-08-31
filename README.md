# 🎮 Gaming Equipment Shopping System

A comprehensive full-stack e-commerce web application designed for gaming enthusiasts to browse, compare, customize, and purchase high-performance gaming gear (GPUs, mechanical keyboards, gaming mice, headsets, monitors, and pre-built rigs).

---

## 🌟 Key Features

### 🛒 Customer Experience
- **Product Catalog**: Browse gaming peripherals and hardware with category filtering, search, and sorting (price, popularity, rating).
- **Product Details**: Detailed specifications, high-resolution media, stock status, and customer reviews.
- **Shopping Cart & Checkout**: Interactive cart management, order summary, price breakdown, discount coupon application, and multi-step checkout.
- **User Authentication**: Secure user registration, login, profile management, and order history tracking.

### 🛡️ Admin Dashboard
- **Inventory Management**: Add, update, edit, and remove products, specs, categories, and inventory stock levels.
- **Order Processing**: Monitor customer orders, update shipping statuses (Pending, Shipped, Delivered), and manage transactions.
- **User Management**: View registered customer profiles and manage administrative roles.

---

## 🛠️ Tech Stack & Architecture

- **Frontend**: HTML5, CSS3 / Bootstrap, JavaScript / React
- **Backend**: Node.js / Express or Python / Django / Flask REST APIs
- **Database**: MongoDB / PostgreSQL / MySQL for relational product and transaction storage
- **Authentication**: JWT (JSON Web Tokens) or Session-based Auth

---

## 📁 Repository Structure

```
gaming_equipment_shopping_system/
├── backend/
│   ├── controllers/      # Route handlers for products, cart, orders, users
│   ├── models/           # Data schemas (Product, User, Order, Category)
│   ├── routes/           # REST API endpoints
│   ├── middleware/       # Auth verification & error handling
│   └── server.js         # Entry point
├── frontend/
│   ├── public/           # Static assets & icons
│   └── src/
│       ├── components/   # Navbar, Footer, ProductCard, CartItem
│       ├── pages/        # Home, Catalog, ProductDetail, Cart, Checkout, Admin
│       └── services/     # API integration calls
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites
- Node.js (v16+) or Python 3.9+
- Database server (MongoDB / MySQL / PostgreSQL)

### Setup & Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Namra1912/gaming_equipment_shopping_syestem.git
   cd gaming_equipment_shopping_syestem
   ```

2. **Backend Setup**:
   ```bash
   cd backend
   npm install
   # Configure environment variables in .env (PORT, DB_URI, JWT_SECRET)
   npm start
   ```

3. **Frontend Setup**:
   ```bash
   cd ../frontend
   npm install
   npm start
   ```

4. Open `http://localhost:3000` in your browser to launch the application.

---

## 📜 License
This project is licensed under the ISC / MIT License — Created with ❤️ by **[Namra Shah (Namra1912)](https://github.com/Namra1912)**.

