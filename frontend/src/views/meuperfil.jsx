import { useEffect, useState } from "react";
import { Helmet } from "react-helmet";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../style/css/styleRegistro.css";

export default function MeuPerfil() {

    const navigate = useNavigate();
    const [tipo, setTipo] = useState(null);
    const [loading, setLoading] = useState(true);

    const [dados, setDados] = useState({
        login: "",
        senha: "",
        email: "",
        fone: "",
        foneContato: "",
        role: "",
        cpf: "",
        dataNasc: "",
        turno: "",
        dataContratacao: "",
    });

    function formatDateToBR(dateStr) {
        if (!dateStr) return "";
        const [y, m, d] = dateStr.split("-");
        return `${d}/${m}/${y}`;
    }

    useEffect(() => {
        axios.get("http://localhost:8080/api/session", { withCredentials: true })
            .then(res => {

                if (!res.data || !res.data.usuario) {
                    navigate("/login");
                    return;
                }

                const usuario = res.data.usuario;
                const tipoUsuario = res.data.tipo;

                let dadosFix = { ...usuario };

                if (usuario.dataNasc) {
                    const [d, m, y] = usuario.dataNasc.split("/");
                    dadosFix.dataNasc = `${y}-${m}-${d}`;
                }

                if (usuario.dataContratacao) {
                    const [d, m, y] = usuario.dataContratacao.split("/");
                    dadosFix.dataContratacao = `${y}-${m}-${d}`;
                }

                setTipo(tipoUsuario);
                setDados(dadosFix);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                navigate("/login");
            });
    }, []);

    function atualizar(e) {
        const { name, value } = e.target;
        setDados(prev => ({ ...prev, [name]: value }));
    }

    async function salvar(e) {
        e.preventDefault();

        const payload = { ...dados };

        if (tipo === "cliente") payload.dataNasc = formatDateToBR(dados.dataNasc);
        if (tipo === "vigilante") payload.dataContratacao = formatDateToBR(dados.dataContratacao);

        try {
            if (tipo === "cliente") {
                await axios.post("http://localhost:8080/api/clientes/salvar", payload, { withCredentials: true });
            } else if (tipo === "vigilante") {
                await axios.post("http://localhost:8080/api/admin/vigilantes/salvar", payload, { withCredentials: true });
            } else if (tipo === "admin") {
                await axios.post("http://localhost:8080/api/admin/salvar", payload, { withCredentials: true });
            }

            alert("Perfil atualizado com sucesso!");
            navigate("/");

        } catch (error) {
            console.error(error);
            alert("Erro ao salvar!");
        }
    }

    if (loading) return <h2>Carregando...</h2>;

    const isCliente = tipo === "cliente";
    const isVigilante = tipo === "vigilante";
    const isAdmin = tipo === "admin";

    return (
        <>
            <Helmet>
                <title>Meu Perfil</title>
            </Helmet>

            <div className="register-wrapper">
                <div className="register-box">
                    <h2>Meu Perfil</h2>

                    <form onSubmit={salvar}>

                        {/* Campos comuns */}
                        <input
                            name="login"
                            placeholder="Login"
                            required
                            value={dados.login || ""}
                            onChange={atualizar}
                        />

                        <input
                            type="password"
                            name="senha"
                            placeholder="Nova senha"
                            onChange={atualizar}
                        />

                        {!isAdmin && (
                            <>
                                <input
                                    name="email"
                                    placeholder="E-mail"
                                    value={dados.email || ""}
                                    onChange={atualizar}
                                />

                                <input
                                    name="fone"
                                    placeholder="Telefone"
                                    value={dados.fone || ""}
                                    onChange={atualizar}
                                />

                                <input
                                    name="foneContato"
                                    placeholder="Telefone de Contato"
                                    value={dados.foneContato || ""}
                                    onChange={atualizar}
                                />
                            </>
                        )}

                        {/* CLIENTE */}
                        {isCliente && (
                            <>
                                <input
                                    name="cpf"
                                    placeholder="CPF"
                                    value={dados.cpf || ""}
                                    onChange={atualizar}
                                />

                                <input
                                    type="date"
                                    name="dataNasc"
                                    value={dados.dataNasc || ""}
                                    onChange={atualizar}
                                />
                            </>
                        )}

                        {/* VIGILANTE */}
                        {isVigilante && (
                            <>
                                <label>Turno</label>
                                <select
                                    name="turno"
                                    value={dados.turno || ""}
                                    onChange={atualizar}
                                >
                                    <option value="">--Selecione--</option>
                                    <option value="D">🌞 Diurno</option>
                                    <option value="N">🌙 Noturno</option>
                                </select>

                                <label>Data de Contratação</label>
                                <input
                                    type="date"
                                    name="dataContratacao"
                                    value={dados.dataContratacao || ""}
                                    onChange={atualizar}
                                />
                            </>
                        )}

                        <button type="submit">Salvar alterações</button>
                    </form>

                    {/* 🔹 Botão de voltar embaixo do formulário */}
                    <button
                        type="button"
                        onClick={() => navigate("/")}
                        className="btn-voltar"
                        style={{
                            marginTop: "15px",
                            background: "#777",
                            color: "white",
                            padding: "8px 12px",
                            borderRadius: "5px",
                            border: "none",
                            cursor: "pointer",
                            width: "100%"
                        }}
                    >
                        ⬅ Voltar
                    </button>

                </div>
            </div>
        </>
    );
}
