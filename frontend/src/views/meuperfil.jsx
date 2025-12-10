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
        cpf: "",
        dataNasc: "",
        turno: "",
        cargaHoraria: "",
        dataContratacao: ""
    });

    function formatToBR(date) {
        if (!date) return "";
        const [y, m, d] = date.split("-");
        return `${d}/${m}/${y}`;
    }

    function formatToISO(date) {
        if (!date.includes("/")) return date;
        const [d, m, y] = date.split("/");
        return `${y}-${m}-${d}`;
    }

    // CARREGA PERFIL
    useEffect(() => {
        axios.get("http://localhost:8080/api/session", { withCredentials: true })
            .then(res => {
                if (!res.data || !res.data.usuario) {
                    navigate("/login");
                    return;
                }
                const usuario = res.data.usuario;
                const tipoUsuario = res.data.tipo;
                setTipo(tipoUsuario);

                setDados({
                    login: usuario.login || "",
                    senha: "",
                    email: usuario.contatoInfo?.email || "",
                    fone: usuario.contatoInfo?.fone || "",
                    foneContato: usuario.contatoInfo?.foneContato || "",
                    cpf: usuario.cpf || "",
                    dataNasc: usuario.dataNasc ? formatToISO(usuario.dataNasc) : "",
                    turno: usuario.turno || "",
                    cargaHoraria: usuario.cargaHoraria || "",
                    dataContratacao: usuario.dataContratacao ? formatToISO(usuario.dataContratacao) : ""
                });

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

    // SALVAR ALTERAÇÕES
    async function salvar(e) {
        e.preventDefault();

        let payload = {};

        if (tipo === "cliente") {
            payload = {
                login: dados.login,
                senha: dados.senha || undefined,
                cpf: dados.cpf,
                dataNasc: formatToBR(dados.dataNasc),
                contatoInfo: {
                    email: dados.email,
                    fone: dados.fone,
                    foneContato: dados.foneContato
                }
            };
        }

        if (tipo === "vigilante") {
            payload = {
                login: dados.login,
                senha: dados.senha || undefined,
                turno: dados.turno,
                cargaHoraria: dados.cargaHoraria || "08:00:00",
                remuneracao: dados.remuneracao ? Number(dados.remuneracao) : undefined,
                dataContratacao: formatToBR(dados.dataContratacao),
                contatoInfo: {
                    email: dados.email,
                    fone: dados.fone,
                    foneContato: dados.foneContato
                }
            };
        }

        if (tipo === "admin") {
            payload = {
                login: dados.login,
                senha: dados.senha || undefined
            };
        }

        try {
            if (tipo === "cliente") {
                await axios.post("http://localhost:8080/api/clientes/salvar", payload, { withCredentials: true });
            } else if (tipo === "vigilante") {
                await axios.post("http://localhost:8080/api/admin/vigilantes/salvar", payload, { withCredentials: true });
            } else if (tipo === "admin") {
                await axios.post("http://localhost:8080/api/admin/salvar", payload, { withCredentials: true });
            }

            alert("Perfil atualizado!");
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
            <Helmet><title>Meu Perfil</title></Helmet>

            <div className="register-wrapper">
                <div className="register-box" style={{ textAlign: "left" }}>
                    <h2>Meu Perfil</h2>

                    <form onSubmit={salvar}>

                        <label>Login</label>
                        <input name="login" value={dados.login} onChange={atualizar} required />

                        <label>Nova Senha</label>
                        <input type="password" name="senha" placeholder="Nova senha" onChange={atualizar} />

                        {!isAdmin && (
                            <>
                                <label>E-mail</label>
                                <input name="email" value={dados.email} onChange={atualizar} />

                                <label>Telefone</label>
                                <input name="fone" value={dados.fone} onChange={atualizar} />

                                <label>Telefone para Contato</label>
                                <input name="foneContato" value={dados.foneContato} onChange={atualizar} />
                            </>
                        )}

                        {isCliente && (
                            <>
                                <label>CPF</label>
                                <input
                                    name="cpf"
                                    value={dados.cpf}
                                    readOnly
                                    style={{ color: "#333" }}
                                />

                                <label>Data de Nascimento</label>
                                <input
                                    type="date"
                                    name="dataNasc"
                                    value={dados.dataNasc}
                                    onChange={atualizar}
                                />
                            </>
                        )}

                        {isVigilante && (
                            <>
                                <label>Turno</label>
                                <select name="turno" value={dados.turno} onChange={atualizar}>
                                    <option value="">--Selecione--</option>
                                    <option value="D">Diurno</option>
                                    <option value="N">Noturno</option>
                                </select>

                                <label>Data de Contratação</label>
                                <input
                                    type="date"
                                    name="dataContratacao"
                                    value={dados.dataContratacao}
                                    onChange={atualizar}
                                />
                            </>
                        )}

                        <button type="submit">Salvar alterações</button>
                    </form>

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
