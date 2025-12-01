import { useEffect, useState } from "react";
import axios from "axios";
import Menu from "../components/Menu";
import "../style/css/stylePainel.css";

export default function Monitoramento() {
  const [enderecos, setEnderecos] = useState([]);
  const [idEnderecoSelecionado, setIdEnderecoSelecionado] = useState("");
  const [enderecoSelecionado, setEnderecoSelecionado] = useState(null);
  const [vigilante, setVigilante] = useState(null);
  const [gravacoes, setGravacoes] = useState([]);
  const [alarmeAtivo, setAlarmeAtivo] = useState(true);
  const [camerasAoVivo, setCamerasAoVivo] = useState(false);

  // vídeos simulando cameras ao vivo
  const camerasSimuladas = [
    {
      url: "https://www.youtube.com/embed/eqO0cBHeDxE?autoplay=1&mute=1&start=0"
    },
    {
      url: "https://www.youtube.com/embed/6dp-bvQ7RWo?autoplay=1&mute=1&start=0"
    },
    {
      url: "https://www.youtube.com/embed/h1wly909BYw?si=NuVvP45SIlesMKyz"
    },
    {
      url: "https://www.youtube.com/embed/57w2gYXjRic?si=Kw2JOYN89VL5BKzs"
    },
    {
      url: "https://www.youtube.com/embed/9SLt3AT0rXk?si=CRHBaUi6xQ2lH_DY"
    },
    {
      url: "https://www.youtube.com/embed/fUsJZTHeZn4?si=02jBte5-sXt8pWqK"  
    }
  ];

  //buscar e setar enderecos
  useEffect(() => {
    axios
      .get("http://localhost:8080/api/monitoramento", { withCredentials: true })
      .then((res) => {
        if (res.data.error) return;

        setEnderecos(res.data.enderecos);
        setVigilante(res.data.vigilante);
        setGravacoes(res.data.gravacoes);
        setEnderecoSelecionado(res.data.enderecoSelecionado);

        if (res.data.enderecoSelecionado) {
          setIdEnderecoSelecionado(res.data.enderecoSelecionado.id);
        }
      });
  }, []);

  //trocar enderecos
  const handleSelect = (e) => {
    const id = e.target.value;
    setIdEnderecoSelecionado(id);

    axios
      .get(`http://localhost:8080/api/monitoramento?idEndereco=${id}`, {
        withCredentials: true,
      })
      .then((res) => {
        setEnderecoSelecionado(res.data.enderecoSelecionado);
        setGravacoes(res.data.gravacoes);
      });
  };

  //Alterar alarme
  const toggleAlarme = () => {
    setAlarmeAtivo((prev) => !prev);
  };

  //Abrir/fechar overlay de cameras ao vivo
  const toggleCamerasAoVivo = () => {
    setCamerasAoVivo((prev) => !prev);
  };

  //Url do mapa
  const mapaUrl =
    enderecoSelecionado &&
    `https://maps.google.com/maps?q=${encodeURIComponent(
      `${enderecoSelecionado.rua} ${enderecoSelecionado.numero} ${enderecoSelecionado.bairro} ${enderecoSelecionado.cidade} ${enderecoSelecionado.estado}`
    )}&output=embed`;

  return (
    <>
      <Menu />

      <div className={`container-monitoramento ${camerasAoVivo ? "blurred" : ""}`}>
        <h2>Painel de Monitoramento</h2>

        {/* SELECT DE ENDERECOS */}
        <div className="select-endereco-container">
          <label htmlFor="select-local">Selecione um endereço monitorado:</label>
          <select id="select-local" value={idEnderecoSelecionado} onChange={handleSelect}>
            <option value="">-- Selecione --</option>
            {enderecos.map((end) => (
              <option key={end.id} value={end.id}>
                {`${end.rua}, ${end.numero} - ${end.bairro}, ${end.cidade} - ${end.estado}`}
              </option>
            ))}
          </select>
        </div>

        {/* PAINEL PRINCIPAL */}
        {enderecos.length > 0 && (
          <div className="painel-dinamico">
            <div className="status-box">
              <div className="status-item">
                <span>Status do Alarme:</span>
                <span
                  className={`alarme-status ${alarmeAtivo ? "ativo" : "inativo"}`}
                >
                  {alarmeAtivo ? "Ativado" : "Desativado"}
                </span>
              </div>

              <div className="status-item">
                <span>Vigilante em campo:</span>
                <span>{vigilante?.login ?? "Carregando..."}</span>
              </div>
            </div>

            {/* GRAVACOES */}
            {/*<div className="camera-grid">
              {gravacoes.length === 0 && <p>Nenhuma gravação disponível.</p>}

              {gravacoes.map((g) => (
                <div key={g.id} className="video-card">
                  <iframe
                    src={g.url + "?autoplay=1&mute=1&start=0"}
                    width="100%"
                    height="250"
                    frameBorder="0"
                    allow="autoplay; fullscreen; picture-in-picture"
                    allowFullScreen
                  ></iframe>
                </div>
              ))}
            </div>*/}

            {/* MAPA */}
            <div className="mapa-section">
              <h3>Mapa do local</h3>
              {enderecoSelecionado && (
                <iframe
                  src={mapaUrl}
                  width="100%"
                  height="300"
                  style={{ borderRadius: "10px" }}
                  loading="lazy"
                ></iframe>
              )}
            </div>

            {/* BOTOES */}
            <div className="atalhos-section">
              <button className="btn-acoes alarme-toggle" onClick={toggleAlarme}>
                {alarmeAtivo ? "🔴 Desativar Alarme" : "🟢 Ativar Alarme"}
              </button>

              <button className="btn-acoes">📞 Acionar Suporte</button>
              <button className="btn-acoes" onClick={toggleCamerasAoVivo}>
                {camerasAoVivo ? "✖ Fechar Câmeras" : "🎥 Ver Câmeras ao Vivo"}
              </button>
            </div>
          </div>
        )}
      </div>

      {/* OVERLAY DE CAMERAS AO VIVO */}
        {camerasAoVivo && (
        <div className="overlay-cameras" onClick={toggleCamerasAoVivo}>
            <div className="cameras-container" onClick={(e) => e.stopPropagation()}>
            <div className="camera-grid">
                {camerasSimuladas.map((cam, idx) => {
                // Garantir autoplay, mute e start=0
                const autoplayUrl = cam.url.includes("?")
                    ? `${cam.url}&autoplay=1&mute=1&start=0`
                    : `${cam.url}?autoplay=1&mute=1&start=0`;
                return (
                    <div key={idx} className="video-card">
                    <iframe
                        src={autoplayUrl}
                        width="100%"
                        height="250"
                        frameBorder="0"
                        allow="autoplay; fullscreen; picture-in-picture"
                        allowFullScreen
                    ></iframe>
                    </div>
                );
                })}
            </div>
            </div>
        </div>
        )}
    </>
  );
}
