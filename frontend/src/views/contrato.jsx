import { useEffect, useState } from "react";
import axios from "axios";
import Menu from "../components/Menu";
import "../style/css/styleContrato.css";

export default function Contrato() {
    const [contratos, setContratos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [modalAberto, setModalAberto] = useState(false);
    const [contratoSelecionado, setContratoSelecionado] = useState(null);

    const handleVisualizarContrato = (contrato) => {
        setContratoSelecionado(contrato);
        setModalAberto(true);
    };

    const handleBaixarContrato = (contratoId) => {
        console.log("Baixar contrato ID:", contratoId);
        alert(`Download do Contrato ${contratoId} iniciado.`);
    };

    useEffect(() => {
        axios.get("http://localhost:8080/api/session", { withCredentials: true })
            .then(res => {
                const idCliente = res.data.usuario?.id;
                console.log(`id do cliente  ${idCliente}`)
                if (idCliente !== undefined && idCliente !== null) {
                    setLoading(true);
                    axios.get(`http://localhost:8080/api/contrato/cliente/id/${idCliente}`, { withCredentials: true })
                        .then(res => setContratos(res.data))
                        .catch(() => setContratos([]))
                        .finally(() => setLoading(false));
                } else {
                    setContratos([]);
                    setLoading(false);
                }
            })
            .catch(() => {
                setContratos([]);
                setLoading(false);
            });
    }, []);

    if (loading) return <h2>Carregando contratos...</h2>;

    return (
        <>
            <Menu />
            <div className="contrato-container">
                <h1>Meus Contratos</h1>

                {contratos.length === 0 && (
                    <p>Você ainda não possui contratos cadastrados.</p>
                )}
                {contratos.length > 0 && (
                    <table className="contrato-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Valor</th>
                                <th>Data Início</th>
                                <th>Data Fim</th>
                                <th>Status</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            {contratos.map(c => (
                                <tr key={c.id}>
                                    <td>{c.id}</td>
                                    <td>R$ {c.valor?.toFixed(2) ?? "N/A"}</td>
                                    <td>{c.dataInicio?.substring(0, 10)}</td>
                                    <td>{c.dataFim?.substring(0, 10)}</td>
                                    <td>{c.status ?? "N/A"}</td>
                                    <td className="contrato-actions-cell">
                                        <button
                                            className="action-button view-button"
                                            onClick={() => handleVisualizarContrato(c)}
                                        >
                                            Visualizar
                                        </button>
                                        <button
                                            className="action-button download-button"
                                            onClick={() => handleBaixarContrato(c.id)}
                                        >
                                            Baixar
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                )}

                {/* Modal */}
              {modalAberto && contratoSelecionado && (
                <div className={`modal-backdrop ${modalAberto ? "show" : ""}`}>
                    <div className="modal-content">
                        <button className="modal-close" onClick={() => setModalAberto(false)}>×</button>
                        <h2>Contrato #{contratoSelecionado.id}</h2>
                        <div className="contract-preview-box">
                            <div className="preview-header">Visualização do Contrato</div>
                            <div className="lorem-content">
                                {contratoSelecionado.documento || "Aqui o conteúdo do documento..."}
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button
                                className="download-button"
                                onClick={() => handleBaixarContrato(contratoSelecionado.id)}
                            >
                                Baixar Contrato
                            </button>
                        </div>
                    </div>
                </div>
            )}


            </div>
        </>
    );
}
