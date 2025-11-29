import { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

import "../style/css/style.css";
import securityHeroImg from "../assets/images/security-hero.jpg";
import aboutImg from "../assets/images/about-img.png";
import { Helmet } from "react-helmet";

export default function Index() {
    const [tipo, setTipo] = useState(null);
    const [usuario, setUsuario] = useState("Usuário");
    const [menuAtivo, setMenuAtivo] = useState(false);
    const [dropdownAtivo, setDropdownAtivo] = useState(false);

    const perfilRef = useRef(null);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get("http://localhost:8080/api/session", { withCredentials: true })
            .then(res => {
                console.log(res);
                setTipo(res.data.tipo);
                setUsuario(res.data.usuario?.login || "Usuário");
            })
            .catch(() => setTipo("NAO_LOGADO"));
    }, []);

    function toggleMenu() { setMenuAtivo(!menuAtivo); }
    function toggleDropdown() { setDropdownAtivo(!dropdownAtivo); }

    //FAZER LOGOUT
    async function handleLogout() {
        try {
            await axios.get("http://localhost:8080/api/logout", {
                withCredentials: true
            });

            navigate("/");
        } catch (err) {
            console.error("Erro ao deslogar:", err);
        }
    }

    //FECHA O DROPDOWN AO CLICAR FORA
    useEffect(() => {
        function handleClickOutside(event) {
            if (perfilRef.current && !perfilRef.current.contains(event.target)) {
                setDropdownAtivo(false);
            }
        }

        document.addEventListener("mousedown", handleClickOutside);
        return () => document.removeEventListener("mousedown", handleClickOutside);
    }, []);

    return (
        <>
            <Helmet>
                <title>Index</title>
                <meta charset="UTF-8"/>
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>
            </Helmet>

            <header className="header">
                <section>
                    <a href="#" className="logo">
                        {<span style={{ color: '#d6a85c', fontWeight: 'bold', fontSize: '1.5rem' }}>SEGURANÇA</span>}
                    </a>

                    <nav className={`navbar ${menuAtivo ? "active" : ""}`}>
                        <a href="#">Inicio</a>
                        <a href="#about">Sobre</a>
                        <a href="#contato">Contato</a>

                        {tipo === "vigilante" && <a href="/ocorrencia">Ocorrência</a>}
                        {tipo === "vigilante" && <a href="/rondas">Rondas</a>}
                        {tipo === "vigilante" && <a href="/monitoramento">Monitoramento</a>}
                        {tipo === "admin" && <a href="/dashboard">Admin</a>}
                    </nav>

                    <div className="menu-toggle" onClick={toggleMenu}>
                        <i className="fas fa-bars"></i>
                    </div>

                    <div className="perfil" onClick={toggleDropdown} ref={perfilRef}>
                        <i className="fas fa-user-circle"></i>
                        <span id="user-name">{usuario}</span>

                        <div className={`dropdown-menu ${dropdownAtivo ? "show" : ""}`}>
                            <a href="#">Meu Perfil</a>
                            <a href="#">Configurações</a>
                            <button onClick={handleLogout} className="logout-btn">Sair</button>
                        </div>
                    </div>
                </section>
            </header>

            <section className="hero">
                <div className="hero-img">
                    <img src={securityHeroImg} alt="Segurança" />
                </div>

                <div className="hero-content">
                    <div className="hero-text">
                        <h1>Protegendo o que importa</h1>
                        <p>Com tecnologia e confiança, garantimos sua segurança 24 horas por dia.</p>
                    </div>
                </div>
            </section>

            <main>
                <section id="about" className="about section-wrapper">
                    <div className="about-content">
                        <img src={aboutImg} alt="Sobre Nós" />
                        <div>
                            <h2>Sobre Nós</h2>
                            <p>
                                Somos uma empresa dedicada à segurança. Atuamos com tecnologia de ponta, vigilância especializada e
                                soluções customizadas para garantir a tranquilidade de nossos clientes 24 horas por dia.
                            </p>
                        </div>
                    </div>
                </section>

                <section id="contato" className="contato section-wrapper">
                    <h2>Entre em Contato</h2>
                    <form method="POST" action="http://localhost:8080/enviarEmail"> 
                        <input type="text" name="nome" placeholder="Seu nome" required />
                        <input type="email" name="email" placeholder="Seu e-mail" required />
                        <textarea name="mensagem" placeholder="Mensagem" required></textarea>
                        <button type="submit">Enviar</button>
                    </form>
                </section>
            </main>

            <footer>
                <p>&copy; 2025 Segurança</p>
            </footer>
        </>
    );
}
