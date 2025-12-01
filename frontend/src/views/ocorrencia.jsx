import { useEffect, useState } from "react";
import axios from "axios";
import "../style/css/styleOcorrencia.css";
import Menu from "../components/Menu";

function Ocorrencia() {
  const [produtos, setProdutos] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/ocorrencias/produtos-monitorados", {
        withCredentials: true,
      })
      .then((res) => {
        setProdutos(res.data)
        console.log(res.data);
       })
      .catch((err) => console.error("Erro ao buscar produtos:", err))
      .finally(() => setLoading(false));
  }, []);

  

  const registrarOcorrencia = (e, idProduto) => {
    e.preventDefault();

    const formData = new FormData(e.target);
    const duracao = formData.get("duracao");

    axios
      .post(
        "http://localhost:8080/api/ocorrencias/cadastrar",
        { idProduto, duracao },
        { withCredentials: true }
      )
      .then(() => {
        alert("Ocorrência registrada com sucesso!");
        e.target.reset();
      })
      .catch((err) => {
        console.error("Erro ao registrar ocorrência:", err);
        alert("Erro ao registrar ocorrência.");
      });
  };

  if (loading) return <p>Carregando...</p>;

  return (
    <>
      <Menu />

      <div className="ocorrencia-page">
          <div className="container-ocorrencia">
            <h2>
              <i className="fas fa-bell"></i> Registro de Ocorrência
            </h2>
            <table>
              <thead>
                <tr>
                  <th>ID Produto</th>
                  <th>Cliente</th>
                  <th>Telefone</th>
                  <th>Endereço</th>
                  <th>Tempo de Ocorrência</th>
                </tr>
              </thead>
              <tbody>
                {produtos.map((p) => (
                  <tr key={p.idProduto}>
                    <td>{p.idProduto}</td>
                    <td>{p.nomeCliente}</td>
                    <td>{p.telefoneCliente}</td>
                    <td>{p.enderecoCompleto}</td>
                    <td>
                      <form onSubmit={(e) => registrarOcorrencia(e, p.idProduto)}>
                        <input
                          type="text"
                          name="duracao"
                          placeholder="HH:mm:ss"
                          required
                        />
                        <button type="submit">Registrar</button>
                      </form>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
      </div>
    </>
  );
}

export default Ocorrencia;
