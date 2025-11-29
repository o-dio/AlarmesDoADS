import { Helmet } from "react-helmet";
import "../style/css/styleRegistro.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

export default function Registro() {
    const navigate = useNavigate();

    const [dados, setDados] = useState({
        login: "",
        senha: "",
        email: "",
        fone: "",
        foneContato: "",
        role: "cliente",
        cpf: "",
        dataNasc: "",
        turno: "",
        dataContratacao: "",
    });

    function atualizar(e) {
        const { name, value } = e.target;
        setDados({ ...dados, [name]: value });
    }

    async function registrar(e) {
        e.preventDefault();
        try {
            await axios.post("http://localhost:8080/api/registrar", dados);
            alert("Registrado com sucesso!");
            navigate("/login");
        } catch (error) {
            console.error(error);
            alert("Erro ao registrar.");
        }
    }

    const isCliente = dados.role === "cliente";

    return (
        <>
            <Helmet>
                <meta charSet="UTF-8" />
                <title>Registrar-se</title>
                <style>{`
                    .hidden { display: none; }
                    select {
                        width: 100%;
                        padding: 1rem;
                        margin-bottom: 1rem;
                        border-radius: 0.5rem;
                        background-color: var(--input-bg);
                        color: var(--light-color);
                        border: 1px solid var(--border-color);
                        font-size: 1rem;
                    }
                    option { color: white; }
                `}</style>
            </Helmet>

            <div className="register-wrapper">
                <div className="register-box">
                    <h2>Cadastro</h2>
                    <form onSubmit={registrar}>
                        {/* Campos comuns */}
                        <input name="login" placeholder="Nome de usuário" required onChange={atualizar} />
                        <input type="password" name="senha" placeholder="Senha" required onChange={atualizar} />
                        <input type="email" name="email" placeholder="E-mail" required onChange={atualizar} />
                        <input type="tel" name="fone" placeholder="Telefone" required onChange={atualizar} />
                        <input type="tel" name="foneContato" placeholder="Telefone de Contato" onChange={atualizar} />

                        {/* Role */}
                        <div className="role-selector">
                            <label>
                                <input
                                    type="radio"
                                    name="role"
                                    value="cliente"
                                    checked={isCliente}
                                    onChange={atualizar}
                                />
                                Cliente
                            </label>
                            <label>
                                <input
                                    type="radio"
                                    name="role"
                                    value="vigilante"
                                    checked={!isCliente}
                                    onChange={atualizar}
                                />
                                Vigilante
                            </label>
                        </div>

                        {/* Campos específicos de Cliente */}
                        <div className={isCliente ? "" : "hidden"}>
                            <input
                                name="cpf"
                                placeholder="CPF"
                                maxLength="11"
                                required={isCliente}
                                onChange={atualizar}
                            />
                            <input
                                type="date"
                                name="dataNasc"
                                placeholder="Data de nascimento"
                                required={isCliente}
                                onChange={atualizar}
                            />
                        </div>

                        {/* Campos específicos de Vigilante */}
                        <div className={!isCliente ? "" : "hidden"}>
                            <label>Turno</label>
                            <select
                                name="turno"
                                required={!isCliente}
                                onChange={atualizar}
                                value={dados.turno}
                            >
                                <option value="">--Selecione--</option>
                                <option value="D">🌞 Diurno</option>
                                <option value="N">🌙 Noturno</option>
                            </select>

                            <label>Data de Contratação</label>
                            <input
                                type="date"
                                name="dataContratacao"
                                required={!isCliente}
                                onChange={atualizar}
                                value={dados.dataContratacao}
                            />
                        </div>

                        <button type="submit">Registrar</button>
                        <p className="login-link">
                            Já tem conta? <a href="/login">Faça login</a>
                        </p>
                    </form>
                </div>
            </div>
        </>
    );
}
