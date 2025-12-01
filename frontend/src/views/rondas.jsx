import { useEffect, useState } from "react";
import { Helmet } from "react-helmet";
import * as LucideIcons from "lucide-react";
import axios from "axios";
import "../style/css/styleRondas.css";
import Menu from "../components/Menu";

export default function Rondas() {
    //Estados vindos da API
    const [emRonda, setEmRonda] = useState(false);
    const [rondas, setRondas] = useState([]);
    const [rotasDisponiveis, setRotasDisponiveis] = useState([]);
    const [enderecoRondaAtual, setEnderecoRondaAtual] = useState(null);
    
    //Buscar dados da api
    async function carregarRondas() {
        try {
            const resp = await axios.get("http://localhost:8080/api/rondas", {
                withCredentials: true
            });

            setRondas(resp.data.rondas || []);
            setEmRonda(resp.data.emRonda || false);
            setRotasDisponiveis(resp.data.rotasDisponiveis || []);
            setEnderecoRondaAtual(resp.data.enderecoRondaAtual || null);

        } catch (err) {
            console.error("Erro ao carregar rondas:", err);
            alert("Erro ao carregar rondas.");
        }
    }

    useEffect(() => {
        carregarRondas();
    }, []);

    //Check-in
    async function handleCheckin(e) {
        e.preventDefault();
        const idRota = new FormData(e.target).get("idRota");

        try {
            await axios.post(
                `http://localhost:8080/api/rondas/checkin?idRota=${idRota}`,
                {},
                { withCredentials: true }
            );

            await carregarRondas();

        } catch (err) {
            console.error("Erro no check-in:", err);
            alert("Erro ao iniciar a ronda.");
        }
    }

    //Check-out
    async function handleCheckout(e) {
        e.preventDefault();
        const idRota = new FormData(e.target).get("idRota");

        try {
            await axios.post(
                `http://localhost:8080/api/rondas/checkout?idRota=${idRota}`,
                {},
                { withCredentials: true }
            );

            await carregarRondas();

        } catch (err) {
            console.error("Erro no checkout:", err);
            alert("Erro ao finalizar a ronda.");
        }
    }

    return (
        <>
            <Helmet>
                <meta charSet="UTF-8" />
                <title>Escala e Rondas - Vigilante</title>
                <link rel="stylesheet" href="/style/css/styleRondas.css" />
                <link rel="stylesheet" href="/style/css/style.css" />
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
            </Helmet>

            <Menu></Menu>

            {/* CONTEUDO */}
            <div className="rondas-page">
                <div className="rondas-container">
                    <h1>Rondas do Vigilante</h1>

                    {/* ---------------- CHECK-OUT ---------------- */}
                    {emRonda && (
                        <div className="ronda-card">
                            <h3>Ronda em andamento</h3>

                            <form onSubmit={handleCheckout}>
                                <label>Finalizar Ronda Atual:</label>

                                <select name="idRota" required>
                                    {rondas
                                        .filter(r => r.status === "Em andamento")
                                        .map(r => (
                                            <option value={r.idRota} key={r.idRota}>
                                                {r.enderecoCompleto}
                                            </option>
                                        ))}
                                </select>

                                <button type="submit">Finalizar Ronda</button>
                            </form>
                        </div>
                    )}

                    {/* ---------------- CHECK-IN ---------------- */}
                    {!emRonda && (
                        <div className="ronda-card">
                            <h3>Iniciar nova ronda</h3>

                            <form onSubmit={handleCheckin}>
                                <label>Selecionar Rota:</label>

                                <select name="idRota" required>
                                    {rotasDisponiveis.map(r => (
                                        <option key={r.id} value={r.id}>
                                            {r.nome} – {r.bairro}
                                        </option>
                                    ))}
                                </select>

                                <button type="submit" className="checkin-btn">
                                    <LucideIcons.LogIn size={18} /> Iniciar Ronda
                                </button>
                            </form>
                        </div>
                    )}

                    <hr />

                    {/* ---------------- HISTORICO ---------------- */}
                    <h2>Histórico de Rondas</h2>

                    <table className="historico">
                        <thead>
                            <tr>
                                <th>Data Início</th>
                                <th>Data Fim</th>
                                <th>Local</th>
                                <th>Bairro</th>
                                <th>Descrição</th>
                                <th>Status</th>
                            </tr>
                        </thead>

                        <tbody>
                            {rondas.map((r, index) => (
                                <tr key={index}>
                                    <td>{r.dataIni}</td>
                                    <td>{r.dataFim || "---"}</td>
                                    <td>{r.local}</td>
                                    <td>{r.bairro}</td>
                                    <td>{r.descricao}</td>
                                    <td>{r.status}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>

                    {/* ---------------- MAPA ---------------- */}
                    {enderecoRondaAtual && (
                        <div className="ronda-mapa">
                            <h2>
                                <LucideIcons.Route size={20} /> Rota Atual
                            </h2>

                            <iframe
                                width="100%"
                                height="300"
                                style={{ border: 0, borderRadius: "0.5rem" }}
                                src={`https://www.google.com/maps?q=${encodeURIComponent(
                                    enderecoRondaAtual
                                )}&output=embed`}
                                loading="lazy"
                                allowFullScreen
                            ></iframe>
                        </div>
                    )}
                </div>
            </div>
        </>
    );
}
