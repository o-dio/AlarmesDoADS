import { Routes, Route, Navigate, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

import Login from "./views/login";
import Index from "./views/index";
import Registro from "./views/registro";
import Rondas from "./views/rondas";
import Monitoramento from "./views/monitoramento";
import Ocorrencia from "./views/ocorrencia";

function App() {
  const [userType, setUserType] = useState(null);
  const [loading, setLoading] = useState(true);

  const location = useLocation();

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/session", { withCredentials: true })
      .then((res) => setUserType(res.data))
      .catch(() => setUserType(null))
      .finally(() => setLoading(false));
  }, [location.pathname]); 

  if (loading) return <p>Carregando...</p>;

  return (
    <Routes>
      <Route
        path="/"
        element={userType ? <Navigate to="/index" /> : <Login />}
      />
      <Route
        path="/login"
        element={userType ? <Navigate to="/index" /> : <Login />}
      />
      <Route
        path="/index"
        element={userType ? <Index userType={userType} /> : <Navigate to="/" />}
      />
      <Route
        path="/registro"
        element={userType ? <Navigate to="/index"/> : <Registro />}
      />
      <Route
        path="/rondas"
        element={userType ? <Rondas /> : <Navigate to="/" />}
      />
      <Route 
        path="/monitoramento"
        element={userType ? <Monitoramento /> : <Navigate to="/" />}
      />
      <Route
        path="/ocorrencia"
        element={userType ? <Ocorrencia /> : <Navigate to="/" />}
      />
    </Routes>
  );
}

export default App;
