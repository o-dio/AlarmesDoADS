import { Helmet } from "react-helmet";
import "../style/css/styleLogin.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

export default function Login() {

    //funcao para utilizar a navegacao entre rotas
    const navigate = useNavigate();

    //Varias que vao ter os estados alterados ao decorrer da funcao
    const [nome, setNome] = useState("");
    const [senha, setSenha] = useState("");
    const [role, setRole] = useState("cliente");

    //Funcao para 
    async function handleLogin(e) {
        //Para o evento de envio do formulario
        e.preventDefault();

        //Tenta enviar os dados para api para ela fazer a validacao
        try {
            // Enviar como form-urlencoded para o Spring receber via @RequestParam
            const formData = new URLSearchParams();
            formData.append("nome", nome);
            formData.append("senha", senha);
            formData.append("role", role);

            await axios.post(
                "http://localhost:8080/api/login",
                formData,
                { 
                    withCredentials: true, 
                    headers: { "Content-Type": "application/x-www-form-urlencoded" } 
                }
            );

            //Se der certo, vai para a pagina do index
            navigate("/index");

        } catch (error) {
            //Caso der errado mostra um erro na tela
            alert("Credenciais inválidas");
        }
    }

    return (
        <>
            <Helmet>
                <title>Login - Acesso</title>
                <meta charSet="UTF-8" />
                <link rel="preconnect" href="https://fonts.googleapis.com" />
                <link
                    href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap"
                    rel="stylesheet"
                />
            </Helmet>

            <div className="login-wrapper">
                <div className="login-box">
                    <h2>Login</h2>
                    <form id="loginFormCliente" onSubmit={handleLogin}>
                        <input 
                            name="nome" 
                            type="text" 
                            placeholder="Usuário" 
                            required
                            value={nome}
                            onChange={(e) => setNome(e.target.value)}
                            autoComplete="off"
                            style={{ textTransform: "none" }}
                        />

                        <input 
                            name="senha" 
                            type="password" 
                            placeholder="Senha" 
                            required 
                            value={senha}
                            onChange={(e) => setSenha(e.target.value)}
                            autoComplete="off"
                            style={{ textTransform: "none" }}
                        />

                        <div className="role-selector">
                            <label>
                                <input 
                                    type="radio"
                                    name="role"
                                    value="cliente"
                                    checked={role === "cliente"}
                                    onChange={() => setRole("cliente")}
                                /> Cliente
                            </label>

                            <label>
                                <input 
                                    type="radio"
                                    name="role"
                                    value="vigilante"
                                    checked={role === "vigilante"}
                                    onChange={() => setRole("vigilante")}
                                /> Vigilante
                            </label>

                            <label>
                                <input 
                                    type="radio"
                                    name="role"
                                    value="admin"
                                    checked={role === "admin"}
                                    onChange={() => setRole("admin")}
                                /> Administrador
                            </label>
                        </div>

                        <button type="submit">Entrar</button>

                        <p className="register-link">
                            Não tem uma conta? <a href="/registro">Registrar-se</a>
                        </p>
                    </form>
                </div>
            </div>
        </>
    );
}
